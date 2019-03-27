package com.ssm.base.intercept;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//import com.google.zxing.Result;
import com.ssm.base.Enum.AuthorityTypeEnum;
import com.ssm.base.util.Authority;
import com.ssm.base.view.Result;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 权限认证拦截器
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			
			Class<?> clazz = hm.getBeanType();
			Method m = hm.getMethod();
			
			String url = request.getRequestURI();//获取请求的全路径
	        String contextPath = request.getContextPath();
	        String subUrl = url.substring(contextPath.length());
	        System.out.println("权限拦截器 -- preHandle："+ url +"：前--后："+ subUrl);
	        
	        /*Authority permission = hm.getMethodAnnotation(Authority.class);
	        if(permission != null){
	        	System.out.println("permission："+ permission.value());
	        }*/
	        
	        ResponseBody reBody = hm.getMethodAnnotation(ResponseBody.class);
	        if(reBody != null){
	        	System.out.println("reBody："+ reBody.toString() + "，annotation" + m.getAnnotation(ResponseBody.class));
	        }
			
			try {
				if (clazz != null && m != null) {
					boolean isClzAnnotation = clazz.isAnnotationPresent(Authority.class);
					boolean isMethondAnnotation = m.isAnnotationPresent(Authority.class);
					Authority authority = null;
					// 如果方法和类声明中同时存在这个注解，那么方法中的会覆盖类中的设定。
					if(isMethondAnnotation) {
						authority = m.getAnnotation(Authority.class);
					}else if (isClzAnnotation) {
						authority = clazz.getAnnotation(Authority.class);
					}
					Result<?> result = new Result<>();
					if (authority != null) {
						if(AuthorityTypeEnum.NoValidate == authority.value()){
							// 标记为不验证,放行
							return true;
						}else if (AuthorityTypeEnum.NoAuthority == authority.value()){
							// 不验证权限，验证是否登录
							return true;
						}else{
							// 验证登录及权限
							result.setCode(0);
							result.setMsg("验证成功！【开放接口，且有访问权限】");
							return true;
						}
					}
					
					if(reBody == null){
						// 1、跳转（似乎不是很友好）
						String tzUrl = "/admin/noAuthority/page";
						//String tzUrl = "/admin/404.html";
						response.getWriter().write("<script>self.location.href='"+ tzUrl + "'</script>");
					}else{
						String tzUrl = "window.parent.openNewTab('无权限访问', '/admin/noAuthority/page');";
						response.setContentType("application/json; charset=utf-8");
						//response.getWriter().write("<script type=\"text/javascript\">(function() {"+ tzUrl +"})();</script>");
						
						result.setCode(-300);
						result.setMsg("访问失败！【非开放接口，禁止访问！】");
						result.setData(null);
						String json = JSONObject.fromObject(result).toString();//注意，是json对象，不是数组
						response.getWriter().write(json);
					}
					return false;
					
					// 2、未通过验证，返回提示json
					/*Map<String, Object> responseMap = new HashMap<String, Object>();
					responseMap.put("code", code);
					responseMap.put("msg", msg);
					responseMap.put("params", "");
					responseMap.put("rows", "");
					responseMap.put("被拦截的url", subUrl);
					//String json = new Gson().toJson(responseMap);
					String json = JSONArray.fromObject(responseMap).toString();
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json; charset=utf-8");
					response.getWriter().write(json);
					return false;*/
				}
			} catch (Exception e) {
				//
			}
		}
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
		System.out.println(response.getStatus());
	}

}