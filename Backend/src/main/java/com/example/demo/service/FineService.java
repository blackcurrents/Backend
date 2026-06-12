package com.example.demo.service;

import com.example.demo.entity.Fine;
import com.example.demo.mapper.FineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class FineService {
    private final FineMapper fineMapper;

    /**
     * 查询用户未支付罚款总额
     */
    public BigDecimal getUnpaidFineByUser(Long userId) {
        return fineMapper.sumUnpaidByUser(userId);
    }

    /**
     * 查询用户所有未支付罚款
     */
    public List<Fine> getUserUnpaidFines(Long userId) {
        return fineMapper.selectUserUnpaidFines(userId);
    }


    /**
     * 支付单笔罚款（返回更新后的罚款记录）
     */
    @Transactional
    public Fine payFine(Long fineId) {
        Fine fine = fineMapper.selectById(fineId);
        if (fine == null) {
            throw new RuntimeException("罚款记录不存在");
        }
        if (fine.getStatus() == 1) {
            throw new RuntimeException("该罚款已支付");
        }
        fineMapper.payFine(fineId);
        fine.setStatus(1);
        fine.setPaidAmount(fine.getAmount());
        return fine;
    }

    /**
     * 支付用户所有罚款（返回总金额）
     */
    @Transactional
    public BigDecimal payAllFines(Long userId) {
        List<Fine> fines = fineMapper.selectUserUnpaidFines(userId);
        if (fines.isEmpty()) {
            throw new RuntimeException("没有未支付的罚款");
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Fine fine : fines) {
            fineMapper.payFine(fine.getId());
            total = total.add(fine.getAmount());
        }
        return total;
    }

    /**
     * 支付宝回调：标记罚款为已支付
     */
    @Transactional
    public void markFinePaidByAlipay(Long fineId, String tradeNo, String totalAmount) {
        Fine fine = fineMapper.selectById(fineId);
        if (fine == null || fine.getStatus() == 1) {
            return;
        }
        fineMapper.updatePaid(fineId, new BigDecimal(totalAmount));
    }

    /**
     * 根据ID查询罚款
     */
    public Fine getById(Long fineId) {
        return fineMapper.selectById(fineId);
    }

    /**
     * 查询所有未支付罚款（管理员用）
     */
    public List<Fine> getAllUnpaidFines() {
        return fineMapper.selectAllUnpaidFines();
    }

    /**
     * 查询罚款统计（管理员用）
     */
    public Map<String, Object> getFineStatistics() {
        return fineMapper.selectFineStatistics();
    }
}
