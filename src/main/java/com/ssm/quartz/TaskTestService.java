package com.ssm.quartz;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssm.base.util.DateUtil;

/**
 * 定时任务Controller
 */
@Service
public class TaskTestService{
	
	private int taskCount = 0;
	
	private int taskTest = 0;
	
	@Scheduled(cron = "0/6 * * * * ?")
	public void executeMessageTask() {
		System.out.println(DateUtil.formatDate(new Date())+ " @TaskTestService定时任务，6秒间隔--sdasdasd次数："+ taskCount++);
	}

	public TaskTestService() {
		System.out.println(DateUtil.formatDate(new Date()) + " Service初始化次数："+ taskTest);
	}

}
