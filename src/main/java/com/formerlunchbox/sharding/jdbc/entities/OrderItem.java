package com.formerlunchbox.sharding.jdbc.entities;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("t_order_item")
@Data
public class OrderItem {
  @TableId(type = IdType.AUTO)
  private Long id;
  private String orderNo;
  private Long userId;
  private BigDecimal price;
  private Integer count;
}
