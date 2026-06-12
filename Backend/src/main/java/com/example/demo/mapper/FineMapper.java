package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Fine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface FineMapper extends BaseMapper<Fine> {

    /**
     * 查询所有未支付罚款（管理员用）
     */
    @Select("SELECT f.* FROM fine f WHERE f.status = 0 ORDER BY f.create_time DESC")
    List<Fine> selectAllUnpaidFines();

    /**
     * 查询罚款统计（管理员用）
     */
    @Select("SELECT " +
            "COUNT(*) as total_count, " +
            "COALESCE(SUM(amount), 0) as total_amount, " +
            "COALESCE(SUM(CASE WHEN status = 0 THEN amount ELSE 0 END), 0) as unpaid_amount, " +
            "COALESCE(SUM(CASE WHEN status = 1 THEN amount ELSE 0 END), 0) as paid_amount " +
            "FROM fine")
    Map<String, Object> selectFineStatistics();

    /**
     * 查询用户未支付罚款总额
     */
    @Select("SELECT COALESCE(SUM(amount - COALESCE(paid_amount, 0)), 0) FROM fine " +
            "WHERE user_id = #{userId} AND status = 0")
    BigDecimal sumUnpaidByUser(@Param("userId") Long userId);

    /**
     * 根据借阅记录ID查询罚款
     */
    @Select("SELECT * FROM fine WHERE borrow_record_id = #{borrowRecordId}")
    Fine selectByBorrowId(@Param("borrowRecordId") Long borrowRecordId);

    /**
     * 查询用户所有未支付罚款
     */
    @Select("SELECT f.* FROM fine f " +
            "WHERE f.user_id = #{userId} AND f.status = 0")
    List<Fine> selectUserUnpaidFines(@Param("userId") Long userId);

    /**
     * 支付罚款
     */
    @Update("UPDATE fine SET paid_amount = amount, status = 1, pay_time = NOW() " +
            "WHERE id = #{id}")
    int payFine(@Param("id") Long id);

    /**
     * 部分支付罚款
     */
    @Update("UPDATE fine SET paid_amount = #{paidAmount} " +
            "WHERE id = #{id}")
    int partialPayFine(@Param("id") Long id, @Param("paidAmount") BigDecimal paidAmount);

    /**
     * 支付宝回调：更新罚款为已支付
     */
    @Update("UPDATE fine SET paid_amount = #{paidAmount}, status = 1, pay_time = NOW() WHERE id = #{id}")
    int updatePaid(@Param("id") Long id, @Param("paidAmount") BigDecimal paidAmount);
}