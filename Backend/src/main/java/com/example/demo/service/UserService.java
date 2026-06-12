package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    /**
     * 用户登录
     */
    public User login(String username, String password) {
        // 根据用户名查询用户
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return null;
        }
        // 验证密码（MD5加密）
        String encryptPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!encryptPassword.equals(user.getPassword())) {
            return null;
        }
        // 检查用户状态
        if (user.getStatus() == 1) {
            throw new RuntimeException("账号已被冻结");
        }
        if (user.getStatus() == 2) {
            throw new RuntimeException("账号已被拉黑");
        }
        return user;
    }

    /**
     * 用户注册
     */
    public boolean register(User user) {
        // 检查用户名是否存在
        User existUser = userMapper.selectByUsername(user.getUsername());
        if (existUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        // 密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(encryptPassword);
        // 设置默认值
        user.setRole(0);
        user.setStatus(0);
        user.setMaxBorrowCount(5);
        user.setBorrowDays(30);
        return userMapper.insert(user) > 0;
    }

    /**
     * 根据ID查询用户
     */
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    /**
     * 根据用户名查询用户
     */
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 查询所有用户
     */
    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }

    /**
     * 查询所有正常用户
     */
    public List<User> getNormalUsers() {
        return userMapper.selectNormalUsers();
    }

    /**
     * 更新用户信息
     */
    public boolean updateUser(User user) {
        return userMapper.updateById(user) > 0;
    }

    /**
     * 删除用户（逻辑删除）
     */
    public boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    /**
     * 将用户加入黑名单
     */
    public boolean addToBlacklist(Long id) {
        return userMapper.addToBlacklist(id) > 0;
    }

    /**
     * 将用户移出黑名单
     */
    public boolean removeFromBlacklist(Long id) {
        return userMapper.removeFromBlacklist(id) > 0;
    }

    /**
     * 冻结用户
     */
    public boolean freezeUser(Long id) {
        return userMapper.freezeUser(id) > 0;
    }

    /**
     * 解冻用户
     */
    public boolean unfreezeUser(Long id) {
        return userMapper.unfreezeUser(id) > 0;
    }
}
