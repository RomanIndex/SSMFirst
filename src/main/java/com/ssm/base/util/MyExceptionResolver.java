package com.ssm.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.base.entity.McExceptionHistory;
import com.ssm.base.service.MyExceptionService;
import com.ssm.base.view.Config;

@Component
public class MyExceptionResolver implements HandlerExceptionResolver{
	
	public static Logger logger = Logger.getLogger(MyExceptionResolver.class.getName());
	
	@Autowired MyExceptionService exceptionService;
	
	//全局未处理异常捕获  保存记录
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		
		String requestURI = request.getRequestURI();
		
		McExceptionHistory exceptionHistory = new McExceptionHistory();
		exceptionHistory.setMemberId(CookieHelper.getValueByName(Config.MEMBER_ID));
		exceptionHistory.setRequestUrl(requestURI);
		exceptionHistory.setExceptionName(ex.getClass().getName());
		exceptionHistory.setExceptionInfo(ex.getMessage());
		logger.error("统一异常处理方法：" + ex.getMessage());
		
		try {
			//保存异常记录
			exceptionService.addExceptionHistory(exceptionHistory);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("保存异常出错" + e.getMessage());
		}
		
        ModelAndView mv = new ModelAndView("admin/error");
        return mv;
	}

}
