package com.ssm.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssm.base.entity.CustomException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.base.entity.McExceptionHistory;
import com.ssm.base.service.CustomExceptionService;
import com.ssm.base.view.Config;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CustomExceptionResolver implements HandlerExceptionResolver{
	
	public static Logger logger = Logger.getLogger(CustomExceptionResolver.class.getName());
	
	@Autowired CustomExceptionService exceptionService;
	
	//全局未处理异常捕获  保存记录
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		String message;

		if(exception instanceof CustomException){
			//是预期异常
			CustomException ex = (CustomException) exception;

			//获取我们自定义的异常信息
			message = ex.getMessage();

		} else {
			//运行时异常   友好提示....
			message = "网络电缆有问题,请稍后再试..........";

			//异常信息打印到日志

//            exception.getMessage();

			StringWriter s = new StringWriter();
			PrintWriter printWriter = new PrintWriter(s);
			exception.printStackTrace(printWriter);
			s.toString();

			logger.error(s.toString());
			//发送异常信息,省略
		}


		McExceptionHistory exceptionHistory = new McExceptionHistory();
		exceptionHistory.setMemberId(CookieHelper.getValueByName(Config.MEMBER_ID));
		exceptionHistory.setRequestUrl(request.getRequestURI());
		exceptionHistory.setExceptionName(exception.getClass().getName());
		exceptionHistory.setExceptionInfo(exception.getMessage());
		logger.error("统一异常处理方法：" + exception.getMessage());
		
		try {
			//保存异常记录
			exceptionService.addExceptionHistory(exceptionHistory);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("保存异常出错" + e.getMessage());
		}

		System.out.println("---------CustomExceptionResolver-----------》》》异常："+ exception);
		
        ModelAndView mv = new ModelAndView("/admin/examples/error.ftl");
        return mv;
	}

}
