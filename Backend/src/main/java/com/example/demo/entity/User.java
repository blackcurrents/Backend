package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String realName;

    private String email;

    private String phone;

    /**
     * 角色：0-普通用户 1-管理员
     */
    private Integer role;

    /**
     * 状态：0-正常 1-冻结 2-黑名单
     */
    private Integer status;

    private Integer maxBorrowCount;

    private Integer borrowDays;

    private LocalDateTime expireTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
