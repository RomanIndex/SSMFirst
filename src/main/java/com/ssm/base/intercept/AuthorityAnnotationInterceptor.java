package com.ssm.base.intercept;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssm.base.view.Config;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ssm.base.Enum.AuthorityTypeEnum;
import com.ssm.base.util.Authority;
import com.ssm.base.view.Result;

import net.sf.json.JSONObject;

/**
 * 权限认证拦截器
 *
 * 被拦截的所有请求（url？），都是有“双重”校验的
 *
 * 1、会对controller层的【@Authority注解】进行判断，并根据方法返回值类型，判断是返回json还是重定向页面
 *
 * 2、对（1）里面，需要权限认证的进行校验
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();//获取请求的全路径
		String contextPath = request.getContextPath();
		String subUrl = url.substring(contextPath.length());
		System.out.println("权限拦截器preHandle："+ url +"<--前 || 后-->"+ subUrl);

		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			
			Class<?> clazz = hm.getBeanType();
			Method m = hm.getMethod();
	        
	        Authority permission = hm.getMethodAnnotation(Authority.class);
	        if(permission != null){
	        	System.out.println("permission："+ permission.value());
	        }

			ResponseBody returnAnnotation = hm.getMethodAnnotation(ResponseBody.class);
			if(returnAnnotation != null){
				System.out.println("被拦截方法 注解："+ returnAnnotation.toString() + "，get指定注解" + m.getAnnotation(ResponseBody.class));
			}

			Type returnType = m.getGenericReturnType();//获取返回值类型
			if(null != returnType){
				System.out.println("被拦截方法 返回值："+ returnType.toString() + "，getTypeName"+ returnType.getTypeName());
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

					Result<?> authResult = new Result<>();
					if (authority != null) {
						if(AuthorityTypeEnum.NoValidate == authority.value()){
							// 标记为不验证,放行
							authResult = Result.success("不验证,放行！", null);
						}else if (AuthorityTypeEnum.NoAuthority == authority.value()){
							// 不验证权限，验证是否登录
							authResult = Result.success("不验证权限，验证是否登录！", null);
						}else{
							// 验证登录及权限
							//提问：一个url对应一个privilege吗，或者说应该对应一个吗，url = module + operate? = code?
							//TODO 模拟失败或成功
							authResult = Result.fail("无权操作该功能！");
							//authResult = Result.success("验证成功！【开放接口，且有访问权限】", "需要返回什么");
						}
					}

					if(authResult.getCode() == Result.SUCCESS){
						return true;
					}else {
						/**
						 * 如何确定返回类型，是 Result 还是 页面？
						 *
						 * 1、根据controller方法 有无@ResponseBody标签
						 *
						 * 2、根据controller的返回值 类型，是否是Result
						 */

						//TODO returnAnnotation 和 returnType

						/*if(returnAnnotation == null){
							//response.getWriter().write("<script>self.location.href='"+ AUTH_REJECT_PAGE + "'</script>");
							//response.getWriter().write("<script type=\"text/javascript\">(function() {"+ tzUrl +"})();</script>");
							//String tzUrl = "window.parent.openNewTab('无权限访问', '/admin/noAuthority/page');";
							//谨防跨域问题（现在前后端一起部署，域名相同，是不会有跨域的错的）
							//response.sendRedirect(Config.SSM_DOMAIN + Config.AUTH_REJECT_PAGE);
							response.sendRedirect(Config.AUTH_REJECT_PAGE);
						}else{
							Result rejectResult = Result.fail("访问失败！【非开放接口，禁止访问！】");
							String json = JSONObject.fromObject(rejectResult).toString();//注意，是json对象，不是数组
							//response.setCharacterEncoding("UTF-8");
							response.setContentType("application/json; charset=utf-8");
							response.getWriter().write(json);
						}*/

						//判断有问题？？
						if(returnType != Result.class){
							response.sendRedirect(Config.AUTH_REJECT_PAGE);
						}else{
							Result rejectResult = Result.fail("访问失败！【非开放接口，禁止访问！】");
							String json = JSONObject.fromObject(rejectResult).toString();
							response.setContentType("application/json; charset=utf-8");
							response.getWriter().write(json);
						}

						return false;
					}
				}
			} catch (Exception e) {
				System.out.println("======="+ e);
			}
		}
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
		System.out.println(response.getStatus());
	}

}