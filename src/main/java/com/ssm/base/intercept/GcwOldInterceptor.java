package com.ssm.base.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.base.view.Config;

@Component
public class GcwOldInterceptor implements HandlerInterceptor{
	
	public static String JOINT_CARE_DOMAIN = "http://agjwdev.winwayworld.com/";

	/**
	 * 广场舞原 拦截器 设置
	 * 仅做参考，无太大价值。符合实际需求的才是最合理的
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		// TODO Auto-generated method stub
		String requestUrl = request.getRequestURI();
		String query = request.getQueryString();
		System.out.println("--------拦截器执行拦截-----------");
		System.out.println("----请求地址 ："+ requestUrl);
		System.out.println("---地址栏参数："+ query);
		System.out.println("该请求session id = "+ request.getSession().getId());
		String openId = (String) request.getSession().getAttribute(Config.SESSION_OPEN_ID_KEY);
		System.out.println("该请求session openid = "+ openId);
		
		if(requestUrl.contains("/index")) {
			System.out.println("-----拦截到首页地址-----");
			
			if(StringUtils.isNotBlank(query)) {
				//url 地址栏参数不为空（模拟接口重定向过来的，带有openId 和 subscribe参数），放行
				initSession(request);
				return true;
			}
			
			System.out.println("query = null，session的openId是否为null");
			if(StringUtils.isBlank(openId)){
				//domain = JOINT_CARE_DOMAIN;//
				System.out.println("--------重定向ing------------");
				response.sendRedirect("/wx/imitate/auto");//重定向到 模拟 微信授权 接口
				//response.sendRedirect("http://agjwdev.winwayworld.com/gcw/common/wxRoute/index");
			}else{
				return true;
			}
			
		}else if(requestUrl.contains("gcw/auth/getTicket")){
			if(StringUtils.isBlank(query)){
				response.sendRedirect(JOINT_CARE_DOMAIN+"/gcw/auth");
			}else {
				return true;
			}
			
		}else{
			String accessVal = request.getParameter("GCW_ACCESS_KEY");
			String accessOpenId = request.getParameter("openId");
			
			if("GCW_ACCESS_VALUE".equals(accessVal)){
				//HttpSession session = HttpHelper.getRequest().getSession();
				//session.setAttribute(Config.SESSION_OPEN_ID_KEY, accessOpenId);//暂时把前端传入放在sess里，不用改所有接口
				return true;
			}else{
				request.getRequestDispatcher("/gcw/session/invalid").forward(request, response);
			}
			
			//return true;
			/*if(StringUtils.isBlank(openId)){
				response.sendRedirect("/gcw/session/invalid");//重定向到 无效页面
				//request.getRequestDispatcher("/gcw/session/invalid").forward(request, response);
			}else {
				return true;//非 首页，session openId不为空，则放行
			}*/
		}
		
		return false;
	}

	//但进入首页gcw/index 的参数不为空时（不为空，一定是 模拟微信授权 重定向 过来的），初始化session
	private void initSession(HttpServletRequest request) {
		String openId = request.getParameter("openId");
		String subscribe = request.getParameter("subscribe");
		System.out.println("---------initSession   openId = "+ openId +"，subscribe = "+ subscribe);
		//HttpSession session = HttpHelper.getRequest().getSession();
		//session.setAttribute(Config.SESSION_OPEN_ID_KEY, openId);
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView model)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exc)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
