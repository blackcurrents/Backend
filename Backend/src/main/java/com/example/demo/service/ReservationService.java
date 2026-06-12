package com.example.demo.service;

import com.example.demo.entity.Reservation;
import com.example.demo.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationMapper reservationMapper;

    /**
     * 查询用户的所有预约
     */
    public List<Reservation> getUserReservations(Long userId) {
        return reservationMapper.selectUserReservations(userId);
    }

    /**
     * 取消预约
     */
    @Transactional
    public boolean cancelReservation(Long reservationId) {
        return reservationMapper.cancelReservation(reservationId) > 0;
    }

    /**
     * 检查并处理过期预约
     */
    @Transactional
    public int processExpiredReservations() {
        return reservationMapper.markExpiredReservations();
    }

    /**
     * 图书到馆后通知预约用户
     */
    @Transactional
    public void notifyReservationUsers(Long bookId) {
        Reservation next = reservationMapper.getNextWaitingReservation(bookId);
        if (next != null) {
            reservationMapper.updateToNotified(next.getId());
            // TODO: 发送通知（短信/邮件）
        }
    }

    // ReservationService.java 中添加

    /**
     * 查询所有等待中的预约（管理员用）
     */
    public List<Reservation> getAllWaitingReservations() {
        return reservationMapper.selectAllWaitingReservations();
    }

    /**
     * 根据图书ID查询预约队列（管理员用）
     */
    public List<Reservation> getReservationsByBook(Long bookId) {
        return reservationMapper.selectReservationsByBook(bookId);
    }

    /**
     * 通知预约用户取书
     */
    @Transactional
    public void notifyUser(Long reservationId) {
        Reservation reservation = reservationMapper.selectById(reservationId);
        if (reservation == null) {
            throw new RuntimeException("预约记录不存在");
        }
        if (reservation.getStatus() != 0) {
            throw new RuntimeException("预约状态异常");
        }
        reservationMapper.updateToNotified(reservationId);
        // TODO: 发送通知（短信/邮件）
    }
}
