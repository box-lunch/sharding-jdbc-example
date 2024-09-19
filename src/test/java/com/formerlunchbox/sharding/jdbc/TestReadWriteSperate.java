package com.formerlunchbox.sharding.jdbc;

import com.formerlunchbox.sharding.jdbc.entities.User;
import com.formerlunchbox.sharding.jdbc.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class TestReadWriteSperate {
  @Autowired
  UserMapper userMapper;

  @Transactional // 用主库 juint默认回滚
  @Test
  void contextLoads() {
    User user = new User();
    user.setName(toString());
    userMapper.insert(user);
    user = userMapper.selectById(user.getId());
    Assertions.assertNotNull(user.getId());
  }


  @Test
  void testSelect() {
    User user = null;
    for (int i = 0; i < 10; i++) {
      user = userMapper.selectById(1);
      System.out.println(user.getName());
    }
  }
}
