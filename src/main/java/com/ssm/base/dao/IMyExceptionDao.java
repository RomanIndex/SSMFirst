package com.ssm.base.dao;

import org.springframework.stereotype.Repository;

import com.ssm.base.entity.McExceptionHistory;

@Repository
public interface IMyExceptionDao {

	int addExceptionHistory(McExceptionHistory exceptionHistory);
	
}
