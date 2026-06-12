package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {
    /**
     * 查询图书当前等待预约人数
     */
    @Select("SELECT COUNT(*) FROM reservation " +
            "WHERE book_id = #{bookId} AND status = 0")
    int countWaitingByBook(@Param("bookId") Long bookId);

    /**
     * 查询用户是否已预约某本书
     */
    @Select("SELECT COUNT(*) FROM reservation " +
            "WHERE user_id = #{userId} AND book_id = #{bookId} AND status = 0")
    int checkUserReservation(@Param("userId") Long userId, @Param("bookId") Long bookId);

    /**
     * 获取下一个等待预约的用户
     */
    @Select("SELECT * FROM reservation " +
            "WHERE book_id = #{bookId} AND status = 0 " +
            "ORDER BY queue_number ASC LIMIT 1")
    Reservation getNextWaitingReservation(@Param("bookId") Long bookId);

    /**
     * 获取用户的所有预约
     */
    @Select("SELECT r.*, b.title, b.author " +
            "FROM reservation r " +
            "LEFT JOIN book b ON r.book_id = b.id " +
            "WHERE r.user_id = #{userId} AND r.status IN (0, 1) " +
            "ORDER BY r.reserve_time ASC")
    List<Reservation> selectUserReservations(@Param("userId") Long userId);

    /**
     * 更新预约状态为已通知
     */
    @Update("UPDATE reservation SET status = 1, update_time = NOW() " +
            "WHERE id = #{id}")
    int updateToNotified(@Param("id") Long id);

    /**
     * 更新预约状态为已取书
     */
    @Update("UPDATE reservation SET status = 2, update_time = NOW() " +
            "WHERE id = #{id}")
    int updateToCompleted(@Param("id") Long id);

    /**
     * 取消预约
     */
    @Update("UPDATE reservation SET status = 3, update_time = NOW() " +
            "WHERE id = #{id}")
    int cancelReservation(@Param("id") Long id);

    /**
     * 将过期的预约标记为过期
     */
    @Update("UPDATE reservation SET status = 4, update_time = NOW() " +
            "WHERE status = 0 AND expire_time < NOW()")
    int markExpiredReservations();


    // ReservationMapper.java 中添加

    /**
     * 查询所有等待中的预约
     */
    @Select("SELECT r.*, u.username, u.real_name, u.phone, b.title, b.author " +
            "FROM reservation r " +
            "LEFT JOIN user u ON r.user_id = u.id " +
            "LEFT JOIN book b ON r.book_id = b.id " +
            "WHERE r.status = 0 " +
            "ORDER BY r.reserve_time ASC")
    List<Reservation> selectAllWaitingReservations();

    /**
     * 根据图书ID查询预约队列
     */
    @Select("SELECT r.*, u.username, u.real_name, u.phone " +
            "FROM reservation r " +
            "LEFT JOIN user u ON r.user_id = u.id " +
            "WHERE r.book_id = #{bookId} AND r.status = 0 " +
            "ORDER BY r.queue_number ASC")
    List<Reservation> selectReservationsByBook(@Param("bookId") Long bookId);
}
