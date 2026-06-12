package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Book {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String isbn;

    private String title;

    private String author;

    private String publisher;

    private String category;

    private Integer totalCount;

    /**
     * 状态：0-正常 1-损坏 2-丢失 3-剔旧
     */
    private Integer status;

    // 删除 coverUrl
    // 删除 location

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;

    // 非数据库字段，用于查询时返回剩余数量
    @TableField(exist = false)
    private Integer remainCount;
}
