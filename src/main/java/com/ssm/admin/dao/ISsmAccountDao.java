package com.ssm.admin.dao;

import com.ssm.common.multiDataSource.wwOracleMapper;
import org.springframework.stereotype.Repository;

import com.ssm.admin.entity.SsmAccount;

import tk.mybatis.mapper.common.Mapper;
@Repository
public interface ISsmAccountDao extends Mapper<SsmAccount>, wwOracleMapper{

}
