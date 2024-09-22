package com.formerlunchbox.sharding.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.formerlunchbox.sharding.jdbc.entities.Order;
import com.formerlunchbox.sharding.jdbc.entities.vo.OrderVO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
  // 其他自定义方法
  List<OrderVO> getOrderAmount();
}
