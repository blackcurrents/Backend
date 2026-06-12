package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("fine")
public class Fine {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long borrowRecordId;

    private BigDecimal amount;

    private BigDecimal paidAmount;

    /**
     * 状态：0-未支付 1-已支付
     */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private LocalDateTime payTime;
}
