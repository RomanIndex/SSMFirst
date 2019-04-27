package com.ssm.base.intercept;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssm.admin.service.PrivilegeService;
import com.ssm.base.view.Config;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 		SSM系统可以这样理解URL：静态资源（Module）的 “访问” 路径，且一一对应
 * 		角色其实可以直接和模块表对应，
 * 		权限表只是起到一个中介作用（票据作用），标识一下对静态资源“访问”的类型、有效时间段等，还可以避免静态资源的一些信息对外暴露
 * 		*count（	权限表） = count（模块表）才对，所有【一个code对应一个url】
 *
 * 1、会对controller层的【@Authority注解】进行判断，并根据方法返回值类型，判断是返回json还是重定向页面
 *
 * 2、对（1）里面，需要权限认证的进行校验
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {
	@Autowired private PrivilegeService privilegeService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();//获取请求的全路径
		String contextPath = request.getContextPath();
		String subUrl = url.substring(contextPath.length());
		System.out.println("权限拦截器preHandle："+ url +"<--前 || 后-->"+ subUrl);

		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			
			Class<?> clazz = hm.getBeanType();//请求方法所在的类
			System.out.println("hm.getBeanType()："+ clazz);
			Method m = hm.getMethod();//请求的方法
			System.out.println("hm.getMethod()："+ m);

			Annotation[] mAnnos = m.getDeclaredAnnotations();
			for(Annotation each : mAnnos){
				System.out.println("方法上的注解："+ each.annotationType());
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
							// 验证登录及权限（一个url对应一个code，即一个url可以生成一张票据，但这张票据可以分给多个角色使用）
							String account = (String) request.getSession().getAttribute(Config.SSM_ACCOUNT);
							authResult = privilegeService.checkAuth(account, url);
						}
					}else{
						authResult = new Result<>(-9, "啊欧！被拦截的方法没有@Authority注解！", null, null);
					}

					if(authResult.getCode() == Result.SUCCESS){
						return true;
					}else {
						/**
						 * 如何确定返回类型，是 Result 还是 页面？
						 *
						 * 1、根据controller方法 有无@ResponseBody标签，不好，有的是类里面的@RestController
						 *
						 * 2、根据controller的返回值 类型，是否是Result
						 */

						Type returnType = m.getGenericReturnType();//获取返回值类型
						if(returnType.getTypeName().indexOf(Result.class.getTypeName()) > -1){
							//Result rejectResult = Result.fail("访问失败！【非开放接口，禁止访问！】");//上线对用户提供一致提示信息
							String json = JSONObject.fromObject(authResult).toString();
							//response.setCharacterEncoding("UTF-8");
							response.setContentType("application/json; charset=utf-8");
							response.getWriter().write(json);
						}else{
							//response.getWriter().write("<script>self.location.href='"+ AUTH_REJECT_PAGE + "'</script>");
							//response.getWriter().write("<script type=\"text/javascript\">(function() {"+ tzUrl +"})();</script>");
							//谨防跨域问题（现在前后端一起部署，域名相同，是不会有跨域的错的）
							//response.sendRedirect(Config.SSM_DOMAIN + Config.AUTH_REJECT_PAGE);//这样反而不行
							if(authResult.getCode() == -9){
								//防止有些方法忘了加@Authority注解，特别页面
								//response.sendRedirect(Config.AUTH_FORGET_PAGE);
								request.getRequestDispatcher(Config.AUTH_FORGET_PAGE).forward(request, response);
							}else{
								//response.sendRedirect(Config.AUTH_REJECT_PAGE);
								request.getRequestDispatcher(Config.AUTH_REJECT_PAGE).forward(request, response);
							}
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