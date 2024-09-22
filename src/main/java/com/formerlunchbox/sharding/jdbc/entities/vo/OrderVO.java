package com.formerlunchbox.sharding.jdbc.entities.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderVO {
  private String orderNo;
  private BigDecimal amount;
}
