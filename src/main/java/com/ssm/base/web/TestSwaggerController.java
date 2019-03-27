package com.ssm.base.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.base.util.CookieHelper;
import com.ssm.base.view.Config;
import com.ssm.base.view.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description="公共接口集")
@CrossOrigin("*")
@Controller
public class TestSwaggerController {
	
	private static Logger logger = Logger.getLogger(TestSwaggerController.class.getName());
	
	@ApiOperation(value = "抽奖首页", response = Result.class)
	@ResponseBody
	@RequestMapping(value="match/common/takeAward/index", produces = { "application/json" }, method = RequestMethod.GET)
	public Result takeAwardIndex(@ApiParam(name = "bettingCtrId", value = "猜奖控制ID", required = true)@RequestParam(value = "bettingCtrId") String bettingCtrId) {
		Result result = new Result();
		String openId = CookieHelper.getValueByName(Config.WX_OPEN_ID);
		
		try {
			if(StringUtils.isBlank(bettingCtrId)) {
				result.setCode(1);
				result.setMsg("请检查参数不能为空！");
			}else if(StringUtils.isBlank(openId)){
				result.setCode(2);
				result.setMsg("获取微信信息失败，请稍后再试");
			}else{
				//result = takeAwardService.takeAwardIndex(bettingCtrId, openId);
			}
		}catch(Exception e) {
			result.setCode(-1);
			result.setMsg("服务出错，请稍后再试");
			logger.error("进入抽奖首页出错！", e);
		}
		
		return result;
	}

}