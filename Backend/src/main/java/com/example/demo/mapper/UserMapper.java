package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM user WHERE username = #{username} AND deleted = 0")
    User selectByUsername(@Param("username") String username);

    /**
     * 查询所有正常用户（未冻结、未拉黑）
     */
    @Select("SELECT * FROM user WHERE status = 0 AND deleted = 0")
    List<User> selectNormalUsers();

    /**
     * 查询所有黑名单用户
     */
    @Select("SELECT * FROM user WHERE status = 2 AND deleted = 0")
    List<User> selectBlacklistedUsers();

    /**
     * 将用户加入黑名单
     */
    @Update("UPDATE user SET status = 2, update_time = NOW() WHERE id = #{id}")
    int addToBlacklist(@Param("id") Long id);

    /**
     * 将用户移出黑名单
     */
    @Update("UPDATE user SET status = 0, update_time = NOW() WHERE id = #{id}")
    int removeFromBlacklist(@Param("id") Long id);

    /**
     * 冻结用户账号
     */
    @Update("UPDATE user SET status = 1, update_time = NOW() WHERE id = #{id}")
    int freezeUser(@Param("id") Long id);

    /**
     * 解冻用户账号
     */
    @Update("UPDATE user SET status = 0, update_time = NOW() WHERE id = #{id}")
    int unfreezeUser(@Param("id") Long id);
}
