package com.ssm.admin.dao;

import org.springframework.stereotype.Repository;

import com.ssm.admin.entity.SsmAccount;

import tk.mybatis.mapper.common.Mapper;
@Repository
public interface ISsmAccountDao extends Mapper<SsmAccount>{

}
