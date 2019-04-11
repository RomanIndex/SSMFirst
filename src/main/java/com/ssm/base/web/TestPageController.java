package com.ssm.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.admin.service.impl.AccountServiceImpl;
import com.ssm.base.Enum.AuthorityTypeEnum;
import com.ssm.common.entity.ValidateAccount;
import com.ssm.base.service.HandleJsonService;
import com.ssm.base.util.Authority;

@Controller
@Api(description="TestPage API", value = "用户API", tags = {"SSM后台：等待整合的一些常用页面功能"})
public class TestPageController {
	//final static Logger logger = LoggerFactory.getLogger(AccountController.class);
	private static Logger logger = Logger.getLogger(TestPageController.class);
	
	@Autowired private AccountServiceImpl accountServiceImpl;
	@Autowired private HandleJsonService jsonService;
	
	/**
	 * bootstrap 结合 vue 页面（调查问卷）
	 */
	@ApiOperation(value = "一款简易的编辑器，收集题目用", notes = "/admin/collect_notepad.ftl")
	@RequestMapping(value = "/admin/collect_notepad", method = RequestMethod.GET)
	public String notepad() {
		return "/admin/collect_notepad";
	}

	@ApiOperation(value = "图表最简单demo", notes = "/admin/echart_demo.ftl")
	@RequestMapping(value = "/admin/echart_demo", method = RequestMethod.GET)
	public String echatDemo() {
		return "/admin/echart_demo";
	}

	@ApiOperation(value = "表单防止重复提交", notes = "/admin/echart_demo.ftl")
	@RequestMapping(value = "/admin/resubmit_form", method = RequestMethod.GET)
	public String resumit() {
		return "/admin/resubmit_form";
	}
	
	/**
	 * bootstrap table 分页
	 */
	@ApiOperation(value = "基于bootstrap的表格", notes = "")
	@ResponseBody
	//@Authority(AuthorityTypeEnum.Validate)
	@RequestMapping(value = "admin/account/bt", method = RequestMethod.GET)
	public Map<String, Object> bt(int limit, int offset, String departmentname, String statu) {
		List<ValidateAccount> list = new ArrayList<>();
		Random rand = new Random();
		//int randNumber = rand.nextInt(MAX - MIN + 1) + MIN
		int randomBegin = rand.nextInt(10 - 0 + 1) + 0;//产生0-10的随机数
		int randomEnd = randomBegin + 5;//rand.nextInt(16 - 10 + 1) + 10;;//产生10-16的随机数
		int circle = rand.nextInt(59 - 0 + 1) + 0;//产生0-59的随机数（总数据条数）
		String uuid = UUID.randomUUID().toString();
		String idPart = uuid.substring(randomBegin, randomEnd);
		int idSeq = 0;
		for(int i = 0; i < circle; i++){
			int random = (int)(Math.random()*(9999-1000+1))+1000;//产生1000-9999的随机数
			ValidateAccount obj = new ValidateAccount();
			obj.setId("ID_"+ idSeq);
			obj.setAccount(i + "*"+ idPart);
			obj.setName((random++) + "");
			obj.setRole("角色[" + (random--) + "]");
			obj.setEmail("abc@"+ random +"hh.com");
			obj.setRemark(i % 4 == 0 ? "备注"+ i*i : "");
			list.add(obj);
			idSeq++;
		}
		int total = list.size();
		int end = (offset + limit) >= total ? total : (offset + limit);
		List<ValidateAccount> rows = list.subList(offset, end);
		Map<String, Object> reMap = new HashMap<>();
		reMap.put("total", total);
		reMap.put("rows", rows);
		return reMap;
	}

}