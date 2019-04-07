package com.ssm.admin.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import com.ssm.common.util.CheckSubmitUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.service.AccountService;
import com.ssm.base.Enum.AuthorityTypeEnum;
import com.ssm.common.entity.ValidateAccount;
import com.ssm.base.util.Authority;
import com.ssm.base.view.QueryModelView;
import com.ssm.base.view.Result;

import net.sf.json.JSONObject;

@Controller
public class AccountController {
	private static Logger logger = Logger.getLogger(AccountController.class);
	
	@Autowired private AccountService accountService;

	@Authority(AuthorityTypeEnum.Validate)//不加直接返回“无权限访问页面”
	@RequestMapping(value = "admin/account/index", method = RequestMethod.GET)
	public String index() {
		return "admin/account_index_noMaster";
	}
	
	@ResponseBody
	@Authority(AuthorityTypeEnum.Validate)//上面加，这里不加的话，页面出来，但数据为空
	@RequestMapping(value = "admin/account/mapperQuery", method = RequestMethod.POST)
	public Result<?> mapperQuery(QueryModelView query) {
		//logger.debug("----Temperature set to {}. Old temperature was {}.", new Date(), "测试logger.debug----");
		logger.debug("----Temperature set to {}. Old temperature was {}  测试logger.debug----");
        logger.info("----Temperature has risen above 50 degrees.测试logger.info----");
		return null;//accountService.mapperQuery(query);
	}
	
	//这应该是一个公共接口
	@ResponseBody
	@Authority
	@RequestMapping(value="admin/account/checkAuth", method = RequestMethod.POST)
	public Result<?> checkAuth(String url) {
		return new Result<>(0, "访问成功【开发接口，允许任何人访问！】", "", url);
	}
	
	/**
	 * 测试session防止重复提交----begin------begin----------begin-----------
	 * 跳转表单页面
	 */
	@Authority
	@RequestMapping(value="admin/account/gotoForm", method = RequestMethod.GET)
	public String preventForm(HttpServletRequest request, Model model){
		String token = CheckSubmitUtil.addSubmitToken(request);//alt+enter（创建方法）
        model.addAttribute("token", token);
        return "admin/account_form";
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

	/**
	 * 测试session防止重复提交 --------------end----------end----------------end-------------
	 */
	
	@Authority
	@RequestMapping(value="admin/account/add", method = RequestMethod.GET)
	public String add(){
		return "admin/account_edit";
	}
	
	@Authority
	@RequestMapping(value="admin/account/update", method = RequestMethod.GET)
	public String update() {
		return "admin/account_edit";
	}
	
	@ResponseBody
	@RequestMapping(value="admin/account/delete", method = RequestMethod.POST)
	public Result<?> delete(String empNo) {
		return null;//new Result<>(0, "", "", accountService.getAccountByKey(empNo));
	}
	
	@ResponseBody
	@RequestMapping(value="admin/account/getAccountByKey", method = RequestMethod.POST)
	public Result<?> getAccountByKey(String empNo) {
		return null;//new Result<>(0, "", "", accountService.getAccountByKey(empNo));
	}
	
	@ResponseBody
	@RequestMapping(value="admin/account/mapperSave", method = RequestMethod.POST)
	public Result<?> mapperSave(String str, String operate) throws UnsupportedEncodingException {
		String decStr = URLDecoder.decode(str, "utf-8");
		System.out.println("String入参对象："+ decStr);
		JSONObject jsonObject = JSONObject.fromObject(str);
		SsmAccount account = (SsmAccount) JSONObject.toBean(jsonObject, ValidateAccount.class);
		
		return null;//accountService.mapperSave(account, operate);
	}

}