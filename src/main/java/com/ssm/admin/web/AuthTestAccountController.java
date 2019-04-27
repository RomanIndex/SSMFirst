package com.ssm.admin.web;

import javax.servlet.http.HttpServletRequest;

import com.ssm.common.util.CheckSubmitUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.base.util.Authority;
import com.ssm.base.view.Result;

/**
 * 说下这个 控制器 要进行测试的内容
 * 1、权限拦截 和 校验
 * 2、自动校验
 * 3、日志
 * 4、自定义异常
 * 5、表单防重复提交
 * 6、。。。
 */

@Controller
public class AuthTestAccountController {
	/**
	 * 测试session防止重复提交----begin------begin----------begin-----------
	 * 跳转表单页面
	 */
	@Authority
	@RequestMapping(value="admin/account/gotoForm", method = RequestMethod.GET)
	public String preventForm(HttpServletRequest request, Model model){
		String token = CheckSubmitUtil.addSubmitToken(request);//alt+enter（创建方法）
        model.addAttribute("token", token);
        return "/admin/resubmit_form.ftl";
	}
	
	//提交表单及校样
	@Authority
	@ResponseBody
	@RequestMapping(value="admin/account/submitForm", method = RequestMethod.GET)
	public Result<?> submitForm(HttpServletRequest request){
		String client_token = request.getParameter("token");//实际调用可能用 参数传递 更方便
		Result<?> submitResult = CheckSubmitUtil.validitySubmitToken(request, client_token);

		//服务器的根目录
		String serviceRoot = request.getSession().getServletContext().getRealPath("/");
		System.out.println("服务器的根目录："+ serviceRoot);

		if(submitResult.getCode() != Result.SUCCESS){
			return submitResult;
		}else{
			//处理逻辑。。。
			//处理完逻辑后，要清除session里面的token
			CheckSubmitUtil.removeSubmitToken(request);
		}
		return new Result<>(Result.SUCCESS, "特定业务处理完成！", null, null);
	}
}