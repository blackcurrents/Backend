package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final UserMapper userMapper;
    private final BookMapper bookMapper;
    private final BorrowRecordMapper borrowRecordMapper;
    private final FineMapper fineMapper;
    private final ReservationMapper reservationMapper;

    // 普通用户借书
    @Transactional
    public void borrowBook(Long userId, Long bookId) {
        // 1. 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 检查用户状态
        if (user.getStatus() == 2) {
            throw new RuntimeException("您已被拉黑，无法借书");
        }
        if (user.getStatus() == 1) {
            throw new RuntimeException("账号已被冻结");
        }

        // 3. 检查欠费
        BigDecimal unpaidFine = fineMapper.sumUnpaidByUser(userId);
        if (unpaidFine != null && unpaidFine.compareTo(BigDecimal.TEN) > 0) {
            throw new RuntimeException("欠费" + unpaidFine + "元，请先缴清");
        }

        // 4. 查询图书
        Book book = bookMapper.selectByIdWithRemain(bookId);
        if (book == null) {
            throw new RuntimeException("图书不存在");
        }

        // 5. 检查剩余数量
        Integer remainCount = book.getRemainCount();
        if (remainCount == null || remainCount <= 0) {
            reserveBook(userId, bookId);
            throw new RuntimeException("图书已借完，已为您自动预约");
        }

        // 6. 检查借书数量（普通用户受限制）
        int currentCount = borrowRecordMapper.countBorrowingByUser(userId);
        if (currentCount >= user.getMaxBorrowCount()) {
            throw new RuntimeException("已达最大借书数量" + user.getMaxBorrowCount() + "本");
        }

        // 7. 检查是否重复借阅
        int alreadyBorrowed = borrowRecordMapper.checkUserBorrowed(userId, bookId);
        if (alreadyBorrowed > 0) {
            throw new RuntimeException("您已借过此书，尚未归还");
        }

        // 8. 创建借阅记录
        createBorrowRecord(userId, bookId, user.getBorrowDays());
    }

    // 管理员借书（不受数量限制）
    @Transactional
    public void adminBorrowBook(Long userId, Long bookId) {
        // 1. 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 检查用户状态（跳过欠费和数量限制）
        if (user.getStatus() == 2) {
            throw new RuntimeException("用户已被拉黑，无法借书");
        }

        // 3. 查询图书
        Book book = bookMapper.selectByIdWithRemain(bookId);
        if (book == null) {
            throw new RuntimeException("图书不存在");
        }

        // 4. 检查剩余数量
        Integer remainCount = book.getRemainCount();
        if (remainCount == null || remainCount <= 0) {
            throw new RuntimeException("图书已借完，无法借出");
        }

        // 5. 检查是否重复借阅
        int alreadyBorrowed = borrowRecordMapper.checkUserBorrowed(userId, bookId);
        if (alreadyBorrowed > 0) {
            throw new RuntimeException("该用户已借过此书，尚未归还");
        }

        // 6. 创建借阅记录（管理员借书，天数按用户配置）
        createBorrowRecord(userId, bookId, user.getBorrowDays());
    }

    // 创建借阅记录
    private void createBorrowRecord(Long userId, Long bookId, Integer borrowDays) {
        BorrowRecord record = new BorrowRecord();
        record.setUserId(userId);
        record.setBookId(bookId);
        record.setBorrowTime(LocalDateTime.now());
        record.setDueTime(LocalDateTime.now().plusDays(borrowDays));
        record.setStatus(0);
        record.setRenewalCount(0);
        borrowRecordMapper.insert(record);
    }

    // 预约图书
    @Transactional
    public void reserveBook(Long userId, Long bookId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        Book book = bookMapper.selectById(bookId);
        if (book == null) {
            throw new RuntimeException("图书不存在");
        }

        int alreadyReserved = reservationMapper.checkUserReservation(userId, bookId);
        if (alreadyReserved > 0) {
            throw new RuntimeException("您已预约过此书");
        }

        int alreadyBorrowed = borrowRecordMapper.checkUserBorrowed(userId, bookId);
        if (alreadyBorrowed > 0) {
            throw new RuntimeException("您已借阅此书，无需预约");
        }

        int queueCount = reservationMapper.countWaitingByBook(bookId);

        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setBookId(bookId);
        reservation.setReserveTime(LocalDateTime.now());
        reservation.setExpireTime(LocalDateTime.now().plusDays(3));
        reservation.setStatus(0);
        reservation.setQueueNumber(queueCount + 1);
        reservationMapper.insert(reservation);
    }

    // 还书
    @Transactional
    public BigDecimal returnBook(Long borrowId) {
        BorrowRecord record = borrowRecordMapper.selectById(borrowId);
        if (record == null) {
            throw new RuntimeException("借阅记录不存在");
        }

        if (record.getStatus() == 1) {
            throw new RuntimeException("图书已归还");
        }

        LocalDateTime now = LocalDateTime.now();
        record.setReturnTime(now);

        long overdueDays = 0;
        BigDecimal fineAmount = BigDecimal.ZERO;

        if (now.isAfter(record.getDueTime())) {
            overdueDays = ChronoUnit.DAYS.between(record.getDueTime(), now);
            if (overdueDays > 0) {
                fineAmount = new BigDecimal("0.2").multiply(new BigDecimal(overdueDays));

                Fine fine = new Fine();
                fine.setUserId(record.getUserId());
                fine.setBorrowRecordId(borrowId);
                fine.setAmount(fineAmount);
                fine.setPaidAmount(BigDecimal.ZERO);
                fine.setStatus(0);
                fineMapper.insert(fine);
            }
        }

        record.setStatus(1);
        borrowRecordMapper.updateById(record);

        return fineAmount;
    }

    // 续借
    @Transactional
    public void renewBook(Long borrowId) {
        BorrowRecord record = borrowRecordMapper.selectById(borrowId);
        if (record == null) {
            throw new RuntimeException("借阅记录不存在");
        }

        if (record.getStatus() != 0) {
            throw new RuntimeException("图书状态异常，无法续借");
        }

        if (record.getRenewalCount() >= 2) {
            throw new RuntimeException("已达最大续借次数");
        }

        if (LocalDateTime.now().isAfter(record.getDueTime())) {
            throw new RuntimeException("已逾期无法续借，请先归还");
        }

        int reservationCount = reservationMapper.countWaitingByBook(record.getBookId());
        if (reservationCount > 0) {
            throw new RuntimeException("该书已被预约，无法续借");
        }

        record.setDueTime(record.getDueTime().plusDays(15));
        record.setRenewalCount(record.getRenewalCount() + 1);
        borrowRecordMapper.updateById(record);
    }

    // 查询用户当前借阅列表
    public List<Map<String, Object>> getUserBorrowingList(Long userId) {
        return borrowRecordMapper.selectUserBorrowingList(userId);
    }

    // 查询用户借阅历史
    public List<Map<String, Object>> getUserHistory(Long userId, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return borrowRecordMapper.selectUserHistory(userId, offset, pageSize);
    }

    // 查询所有借阅记录（管理员用）
    public List<Map<String, Object>> getAllBorrowRecords() {
        return borrowRecordMapper.selectAllBorrowRecords();
    }

    // 按分类统计借阅次数
    public List<Map<String, Object>> getBorrowCountByCategory() {
        return borrowRecordMapper.selectBorrowCountByCategory();
    }
}