package com.ssm.base.dao;

import java.util.List;

import com.ssm.common.multiDataSource.wwOracleMapper;
import org.springframework.stereotype.Repository;

import com.ssm.admin.entity.SsmMenu;

@Repository
public interface ISsmMenuDao extends wwOracleMapper {

	List<SsmMenu> getMenuList();
	
}
