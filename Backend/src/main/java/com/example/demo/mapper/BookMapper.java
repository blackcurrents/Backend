package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface BookMapper extends BaseMapper<Book> {
    /**
     * 查询所有图书（带剩余数量）
     */
    @Select("SELECT b.*, " +
            "(b.total_count - IFNULL((" +
            "    SELECT COUNT(*) FROM borrow_record br " +
            "    WHERE br.book_id = b.id AND br.status IN (0, 2)" +
            "), 0)) AS remain_count " +
            "FROM book b " +
            "WHERE b.deleted = 0 " +
            "ORDER BY b.id DESC")
    List<Book> selectAllWithRemain();

    /**
     * 查询可借图书（剩余数量 > 0）
     */
    @Select("SELECT b.*, " +
            "(b.total_count - IFNULL((" +
            "    SELECT COUNT(*) FROM borrow_record br " +
            "    WHERE br.book_id = b.id AND br.status IN (0, 2)" +
            "), 0)) AS remain_count " +
            "FROM book b " +
            "WHERE b.deleted = 0 " +
            "HAVING remain_count > 0")
    List<Book> selectAvailableBooks();

    /**
     * 根据ID查询图书（带剩余数量）
     */
    @Select("SELECT b.*, " +
            "(b.total_count - IFNULL((" +
            "    SELECT COUNT(*) FROM borrow_record br " +
            "    WHERE br.book_id = b.id AND br.status IN (0, 2)" +
            "), 0)) AS remain_count " +
            "FROM book b " +
            "WHERE b.id = #{id} AND b.deleted = 0")
    Book selectByIdWithRemain(@Param("id") Long id);

    /**
     * 根据分类查询图书
     */
    @Select("SELECT b.*, " +
            "(b.total_count - IFNULL((" +
            "    SELECT COUNT(*) FROM borrow_record br " +
            "    WHERE br.book_id = b.id AND br.status IN (0, 2)" +
            "), 0)) AS remain_count " +
            "FROM book b " +
            "WHERE b.category = #{category} AND b.deleted = 0")
    List<Book> selectByCategory(@Param("category") String category);

    /**
     * 搜索图书（按书名或作者）
     */
    @Select("SELECT b.*, " +
            "(b.total_count - IFNULL((" +
            "    SELECT COUNT(*) FROM borrow_record br " +
            "    WHERE br.book_id = b.id AND br.status IN (0, 2)" +
            "), 0)) AS remain_count " +
            "FROM book b " +
            "WHERE (b.title LIKE CONCAT('%', #{keyword}, '%') " +
            "   OR b.author LIKE CONCAT('%', #{keyword}, '%')) " +
            "AND b.deleted = 0")
    List<Book> searchBooks(@Param("keyword") String keyword);

    /**
     * 分页查询图书（带剩余数量）
     */
    @Select("SELECT b.*, " +
            "(b.total_count - IFNULL((" +
            "    SELECT COUNT(*) FROM borrow_record br " +
            "    WHERE br.book_id = b.id AND br.status IN (0, 2)" +
            "), 0)) AS remain_count " +
            "FROM book b " +
            "WHERE b.deleted = 0 " +
            "ORDER BY b.id DESC")
    IPage<Book> selectPageWithRemain(Page<Book> page);

    /**
     * 减少图书库存（借书时调用）
     * 注意：这里只是示例，实际建议用计算方式，不维护冗余字段
     */
    @Update("UPDATE book SET total_count = total_count - 1 WHERE id = #{id} AND total_count > 0")
    int decreaseTotalCount(@Param("id") Long id);

    /**
     * 增加图书库存（还书时调用）
     */
    @Update("UPDATE book SET total_count = total_count + 1 WHERE id = #{id}")
    int increaseTotalCount(@Param("id") Long id);
}
