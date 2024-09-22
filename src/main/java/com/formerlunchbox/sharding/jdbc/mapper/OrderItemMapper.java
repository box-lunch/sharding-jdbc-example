package com.formerlunchbox.sharding.jdbc.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.formerlunchbox.sharding.jdbc.entities.OrderItem;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
  
}
