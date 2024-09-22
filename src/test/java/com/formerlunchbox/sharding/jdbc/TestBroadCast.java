package com.formerlunchbox.sharding.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formerlunchbox.sharding.jdbc.entities.Dict;
import com.formerlunchbox.sharding.jdbc.mapper.DictMapper;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class TestBroadCast {

  @Autowired
  private DictMapper dictMapper;

  @Test
  public void testInsert() {
    Dict dict = new Dict();
    dict.setDict("dict" + new Random().nextInt(100));
    dictMapper.insert(dict);
    QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
    queryWrapper.last("LIMIT 1");

    dict = dictMapper.selectOne(queryWrapper);
    if (dict != null) {
      System.out.println(dict.getDict());
    } else {
      System.out.println("No record found");
    }
    Assertions.assertNotNull(dict);
  }

  @Test
  public void testSelect() {
    List<Dict> dicts = dictMapper.selectList(null);
    dicts.forEach(u -> u.toString());

  }

}
