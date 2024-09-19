package com.formerlunchbox.sharding.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.formerlunchbox.sharding.jdbc.entities.Grade;
import com.formerlunchbox.sharding.jdbc.entities.User;
import com.formerlunchbox.sharding.jdbc.mapper.GradeMapper;
import com.formerlunchbox.sharding.jdbc.mapper.UserMapper;

@SpringBootTest
public class TestVerticalCut {

  @Autowired
  private UserMapper userMapper;
  @Autowired
  private GradeMapper orderMapper;
  

  @Test
  void testInsert() {
    User user = new User();
    user.setName("testVer1");
    userMapper.insert(user);
    Grade order = new Grade();
    order.setOrderName("testVer1");
    orderMapper.insert(order);
    Assertions.assertNotNull(user.getId());
    Assertions.assertNotNull(order.getOrderId());
  }

  @Test
  void testSelect() {
    List<User> users = userMapper.selectList(null);
    List<Grade> orders = orderMapper.selectList(null);
    users.forEach(u->u.toString());
    orders.forEach(o->o.toString());
    Assertions.assertNotNull(users);
    Assertions.assertNotNull(orders);
  }
  
}
