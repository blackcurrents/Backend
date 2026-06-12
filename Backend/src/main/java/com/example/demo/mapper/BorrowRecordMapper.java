package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.BorrowRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface BorrowRecordMapper extends BaseMapper<BorrowRecord> {
    /**
     * 查询用户当前借阅数量（未归还的）
     */
    @Select("SELECT COUNT(*) FROM borrow_record " +
            "WHERE user_id = #{userId} AND status IN (0, 2)")
    int countBorrowingByUser(@Param("userId") Long userId);

    /**
     * 查询用户逾期数量
     */
    @Select("SELECT COUNT(*) FROM borrow_record " +
            "WHERE user_id = #{userId} AND status = 2")
    int countOverdueByUser(@Param("userId") Long userId);

    /**
     * 查询用户是否已借阅某本书（未归还）
     */
    @Select("SELECT COUNT(*) FROM borrow_record " +
            "WHERE user_id = #{userId} AND book_id = #{bookId} AND status IN (0, 2)")
    int checkUserBorrowed(@Param("userId") Long userId, @Param("bookId") Long bookId);

    /**
     * 查询所有逾期未还的记录
     */
    @Select("SELECT * FROM borrow_record WHERE status = 2")
    List<BorrowRecord> selectOverdueRecords();

    /**
     * 查询用户当前借阅列表（带图书信息）
     */
    @Select("SELECT br.*, b.title, b.author, b.isbn " +
            "FROM borrow_record br " +
            "LEFT JOIN book b ON br.book_id = b.id " +
            "WHERE br.user_id = #{userId} AND br.status IN (0, 2) " +
            "ORDER BY br.borrow_time DESC")
    List<Map<String, Object>> selectUserBorrowingList(@Param("userId") Long userId);

    /**
     * 查询用户借阅历史
     */
    @Select("SELECT br.*, b.title, b.author, b.isbn " +
            "FROM borrow_record br " +
            "LEFT JOIN book b ON br.book_id = b.id " +
            "WHERE br.user_id = #{userId} " +
            "ORDER BY br.borrow_time DESC " +
            "LIMIT #{offset}, #{limit}")
    List<Map<String, Object>> selectUserHistory(@Param("userId") Long userId,
                                                @Param("offset") int offset,
                                                @Param("limit") int limit);

    /**
     * 更新逾期天数
     */
    @Update("UPDATE borrow_record SET overdue_days = #{overdueDays} " +
            "WHERE id = #{id}")
    int updateOverdueDays(@Param("id") Long id, @Param("overdueDays") Integer overdueDays);

    /**
     * 将借阅记录标记为逾期
     */
    @Update("UPDATE borrow_record SET status = 2 WHERE id = #{id}")
    int markAsOverdue(@Param("id") Long id);

    /**
     * 归还图书
     */
    @Update("UPDATE borrow_record SET status = 1, return_time = NOW() " +
            "WHERE id = #{id}")
    int returnBook(@Param("id") Long id);

    /**
     * 热门图书统计（借阅次数最多的）
     */
    @Select("SELECT b.id, b.title, b.author, COUNT(*) as borrow_times " +
            "FROM borrow_record br " +
            "LEFT JOIN book b ON br.book_id = b.id " +
            "GROUP BY br.book_id " +
            "ORDER BY borrow_times DESC " +
            "LIMIT #{limit}")
    List<Map<String, Object>> selectHotBooks(@Param("limit") int limit);

    /**
     * 按图书分类统计借阅次数
     */
    @Select("SELECT b.category, COUNT(*) AS borrow_count " +
            "FROM borrow_record br " +
            "LEFT JOIN book b ON br.book_id = b.id " +
            "WHERE b.category IS NOT NULL AND b.category != '' " +
            "GROUP BY b.category " +
            "ORDER BY borrow_count DESC")
    List<Map<String, Object>> selectBorrowCountByCategory();

    /**
     * 查询所有借阅记录（管理员用）
     */
    @Select("SELECT br.*, u.username, u.real_name, b.title, b.author " +
            "FROM borrow_record br " +
            "LEFT JOIN user u ON br.user_id = u.id " +
            "LEFT JOIN book b ON br.book_id = b.id " +
            "ORDER BY br.borrow_time DESC")
    List<Map<String, Object>> selectAllBorrowRecords();
}
