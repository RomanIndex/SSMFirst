package com.ssm.quartz;

import java.util.Date;

import com.ssm.common.util.DateFormatUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 * 定时任务Controller
 */
@Controller
public class TaskTestController{
	
	private int taskCount = 0;
	
	private int taskTest = 0;
	
	@Scheduled(cron = "0/6 * * * * ?")
	public void executeMessageTask() {
		System.out.println(DateFormatUtil.formatDate(new Date())+ " @TaskTestController定时任务，6秒间隔--sdasdasd次数："+ taskCount++);
	}

	public TaskTestController() {
		System.out.println(DateFormatUtil.formatDate(new Date()) + " Controller初始化次数："+ taskTest);
	}

}
