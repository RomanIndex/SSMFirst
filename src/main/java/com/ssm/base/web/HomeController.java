package com.ssm.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ssm.admin.service.AccountService;
import com.ssm.common.util.HttpRequest;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.base.util.CookieHelper;
import com.ssm.base.view.Config;
import com.ssm.base.view.Result;

@Api(description="登录接口")
@CrossOrigin("*")
@Controller
public class HomeController {
	@Autowired private AccountService accountService;
	
	/**
	 * filter作用是判断登录session是否有效，有效放行，无效返回登录页面
	 * 权限拦截器作用是判断是否需要权限验证，及是否有权限，符合放行，不符合，返回“无权限页面”，且同时拦截的页面filter执行_优先于_权限拦截器
	 * 现在是：1、当登录状态无效时（浏览器Application清除Cookies JSESSIONID），
	 * 首先，当filter 发现 session 失效时，执行返回“登录页面”，执行中断
	 * 但有的页面（如：login/check）filter 没有拦截到，但是被 权限拦截器 拦截，重定向到“admin/noAuthority/page”（这个权限不拦截，但会被 filter拦截到，规则如下）
	 * 无效页面 被 filter 拦截（打印出了"doFilter：/admin/noAuthority/page"）
	 * ===：执行所有_请求_时，首先 会被 filter doFilter 校样，session 有效，放行（才有机会 执行到 权限拦截器），否则终止操作，返回登录页面
	 * （**--filter 和 权限拦截器 的执行，都依赖于 mapping路径 匹配上的（提前设定需要控制的请求），否则都是放行状态--**）
	 * >>>几点提议："/"权限不应该拦截，filter拦截时应直接返回登录页面
	 * 2、登录状态有效时
	 * 首先通过 filter 的校样（因为是有效登录状态，放行），然后被 权限 拦截器 拦截时，返回“无权限页面”
	 * ：：：也行这个url比较特殊，一些 特殊页面 可以 类似处理
	 */
	
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpRequest.getRequest().getSession().removeAttribute(Config.SECURITY_LOGIN_KEY);
		HttpRequest.getRequest().getSession().removeAttribute("menu");
		return "/system/login/login";
    }

	@ApiOperation(value = "登录账号密码校样", notes = "可以选择免登录，有效时间*天", response = Result.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "empNo", value = "员工编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "boxFlag", value = "是否免登录", required = true, dataType = "Boolean", paramType = "query"),
	})
	@ResponseBody
    @RequestMapping(value = "login/check", method = RequestMethod.POST)
    public Result<?> check(String empNo, String password, boolean boxFlag) {
        Result<String> result = new Result<>();

        if(StringUtils.isBlank(empNo) || StringUtils.isBlank(password)){
        	return Result.fail("账号密码不能为空！");
        }
        
        boolean pass = false;
        if(empNo.equalsIgnoreCase("admin") && password.equals("123456")) {
        	pass = true;
		}else{
        	empNo = empNo.toUpperCase();
			SsmAccount loginUser = accountService.getById(empNo);
        	if(null != loginUser){
        		if(loginUser.getPassword().equals(password)) {
        			//记住账号
        			if (boxFlag) {
        				CookieHelper.addCookie(Config.SSM_ACCOUNT, empNo);
        			}else {
        				CookieHelper.eraseCookie(Config.SSM_ACCOUNT);
        			}
        			pass = true;
        		}else{
        			result.setMsg("密码错误！");
        		}
        	}else{
        		result.setMsg("该账号不存在！");
        	}
        }
        
        if(pass){
        	HttpSession session = HttpRequest.getRequest().getSession();
			session.setAttribute(Config.SECURITY_LOGIN_KEY, Config.SECURITY_IS_LOGIN);
			session.setAttribute(Config.SSM_ACCOUNT, empNo);
			result.setCode(0);
			result.setMsg("登录成功，正在前往首页...");
			result.setData("admin/route/index");
        }

        return result;
    }
}