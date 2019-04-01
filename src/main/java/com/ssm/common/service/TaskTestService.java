package com.ssm.common.service;

import java.util.Date;

import com.ssm.common.util.DateFormatUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时任务Service
 */
@Service
public class TaskTestService{
	
	private int taskCount = 0;

	@Scheduled(cron = "0/6 * * * * ?")
	public void executeMessageTask() {
		System.out.println(DateFormatUtil.formatDate(new Date())+ "common.service的测试定时任务【6秒间隔】次数："+ taskCount++);
	}

	public TaskTestService() {
		System.out.println(DateFormatUtil.formatDate(new Date()) + " common.service的测试定时任务初始化");
	}

}
