package com.example.demo.service;

import com.example.demo.entity.BorrowRecord;
import com.example.demo.entity.Fine;
import com.example.demo.entity.User;
import com.example.demo.mapper.BorrowRecordMapper;
import com.example.demo.mapper.FineMapper;
import com.example.demo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final BorrowRecordMapper borrowRecordMapper;
    private final FineMapper fineMapper;
    private final UserMapper userMapper;

    /**
     * 每天凌晨1点执行逾期检查
     */
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void checkOverdue() {
        log.info("开始执行逾期检查任务");

        // 查询所有逾期未还的记录
        List<BorrowRecord> overdueRecords = borrowRecordMapper.selectOverdueRecords();

        for (BorrowRecord record : overdueRecords) {
            long overdueDays = ChronoUnit.DAYS.between(record.getDueTime(), LocalDateTime.now());

            // 标记为逾期
            if (record.getStatus() != 2) {
                borrowRecordMapper.markAsOverdue(record.getId());
            }

            // 更新逾期天数
            borrowRecordMapper.updateOverdueDays(record.getId(), (int) overdueDays);

            // 计算罚款
            BigDecimal fineAmount = new BigDecimal("0.2").multiply(new BigDecimal(overdueDays));

            // 更新或插入罚款记录
            Fine fine = fineMapper.selectByBorrowId(record.getId());
            if (fine == null) {
                fine = new Fine();
                fine.setUserId(record.getUserId());
                fine.setBorrowRecordId(record.getId());
                fine.setAmount(fineAmount);
                fine.setPaidAmount(BigDecimal.ZERO);
                fine.setStatus(0);
                fineMapper.insert(fine);
            } else {
                fine.setAmount(fineAmount);
                fineMapper.updateById(fine);
            }

            // 逾期30天拉黑
            if (overdueDays >= 30) {
                User user = userMapper.selectById(record.getUserId());
                if (user != null && user.getStatus() != 2) {
                    userMapper.addToBlacklist(record.getUserId());
                    log.info("用户{}已被拉黑", user.getUsername());
                }
            }
        }

        log.info("逾期检查任务完成，共处理{}条记录", overdueRecords.size());
    }
}
