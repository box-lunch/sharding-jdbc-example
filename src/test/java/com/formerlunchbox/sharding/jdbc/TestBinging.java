package com.formerlunchbox.sharding.jdbc;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.formerlunchbox.sharding.jdbc.entities.Order;
import com.formerlunchbox.sharding.jdbc.entities.OrderItem;
import com.formerlunchbox.sharding.jdbc.entities.vo.OrderVO;
import com.formerlunchbox.sharding.jdbc.mapper.OrderItemMapper;
import com.formerlunchbox.sharding.jdbc.mapper.OrderMapper;

@SpringBootTest
class TestBinging {

  @Autowired
  private OrderMapper orderMapper;
  @Autowired
  private OrderItemMapper orderItemMapper;

  @Test
  void testSelect() {
    List<OrderVO> orderAmountList = orderMapper.getOrderAmount();
    orderAmountList.forEach(System.out::println);
    Assertions.assertNotNull(orderAmountList);
  }

  @Test
  void testInsertItem() {

    orderMapper.delete(null);
    orderItemMapper.delete(null);
    for (long i = 1; i < 3; i++) {
      Order order = new Order();
      order.setOrderNo("test" + i);
      order.setUserId(1L);
      orderMapper.insert(order);

      for (long j = 1; j < 3; j++) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderNo("test" + i);
        orderItem.setUserId(1L);
        orderItem.setPrice(new BigDecimal(10));
        orderItem.setCount(2);
        orderItemMapper.insert(orderItem);
      }
    }

    for (long i = 5; i < 7; i++) {

      Order order = new Order();
      order.setOrderNo("test" + i);
      order.setUserId(2L);
      orderMapper.insert(order);

      for (long j = 1; j < 3; j++) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderNo("test" + i);
        orderItem.setUserId(2L);
        orderItem.setPrice(new BigDecimal(1));
        orderItem.setCount(3);
        orderItemMapper.insert(orderItem);
      }
    }
    Assertions.assertNotNull(orderMapper.selectList(null));
  }

  @Test
  void testDelAll() {
    orderMapper.delete(null);
    orderItemMapper.delete(null);
    Assertions.assertTrue(orderMapper.selectList(null).isEmpty());
  }

}
