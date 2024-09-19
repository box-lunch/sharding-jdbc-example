
package com.formerlunchbox.sharding.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.formerlunchbox.sharding.jdbc.entities.Grade;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GradeMapper extends BaseMapper<Grade> {
  // 其他自定义方法
}