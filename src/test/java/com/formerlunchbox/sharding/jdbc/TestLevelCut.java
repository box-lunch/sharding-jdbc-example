package com.formerlunchbox.sharding.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.formerlunchbox.sharding.jdbc.entities.Order;
import com.formerlunchbox.sharding.jdbc.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class TestLevelCut {

  @Autowired
  private OrderMapper orderMapper;

  @Test
  public void testInsert() {
    for (int i = 0; i < 10; i++) {
      Order order = new Order();
      order.setOrderNo("orderNo" + i);
      order.setUserId((long) i);
      order.setAmount(new BigDecimal(i * 100));

      orderMapper.insert(order);
    }

    List<Order> orders = orderMapper.selectList(null);
    orders.forEach(u->u.toString());

  }
}
