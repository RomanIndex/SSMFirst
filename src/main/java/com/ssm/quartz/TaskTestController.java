package com.ssm.quartz;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.ssm.base.util.DateUtil;

/**
 * 定时任务Controller
 */
@Controller
public class TaskTestController{
	
	private int taskCount = 0;
	
	private int taskTest = 0;
	
	@Scheduled(cron = "0/6 * * * * ?")
	public void executeMessageTask() {
		System.out.println(DateUtil.formatDate(new Date())+ " @TaskTestController定时任务，6秒间隔--sdasdasd次数："+ taskCount++);
	}

	public TaskTestController() {
		System.out.println(DateUtil.formatDate(new Date()) + " Controller初始化次数："+ taskTest);
	}

}
