package com.formerlunchbox.sharding.jdbc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.formerlunchbox.sharding.jdbc.entities.Dict;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {
  
}
