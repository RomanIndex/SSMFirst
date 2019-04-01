package com.ssm.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.base.dao.IMyExceptionDao;
import com.ssm.base.entity.McExceptionHistory;

@Service
public class CustomExceptionService {
	
	@Autowired IMyExceptionDao exceptionDao;
	
	//保存异常至数据库
	public void addExceptionHistory(McExceptionHistory exceptionHistory) {
		exceptionDao.addExceptionHistory(exceptionHistory);
	}
}
