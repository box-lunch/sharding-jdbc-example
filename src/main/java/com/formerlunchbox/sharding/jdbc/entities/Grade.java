package com.formerlunchbox.sharding.jdbc.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("t_grade")
@Data
public class Grade {

  @TableId(type = IdType.AUTO)
  public Long orderId;
  public String orderName;
  
}
