package com.ssm.admin.dao;

import com.ssm.admin.entity.Ssm;
import com.ssm.common.multiDataSource.wwMysqlMapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface SsmDao extends Mapper<Ssm>, wwMysqlMapper {
}
