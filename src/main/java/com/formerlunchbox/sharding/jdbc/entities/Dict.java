package com.formerlunchbox.sharding.jdbc.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("t_dict")
@Data
public class Dict {
  
  // @TableId(type = IdType.AUTO) //如果使用自增策略则会遇到返回id值过多的问题
  @TableId(type = IdType.ASSIGN_ID)//使用mybatis-plus的雪花算法
  public Long id;
  public String dict;

}
