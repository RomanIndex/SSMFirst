package com.ssm.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssm.admin.entity.SsmMenu;

@Repository
public interface ISsmMenuDao{

	List<SsmMenu> getMenuList();
	
}
