package com.ssm.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ssm.admin.service.SsmAccountService;
import com.ssm.base.Enum.AuthorityTypeEnum;
import com.ssm.common.entity.ValidateAccount;
import com.ssm.base.service.HandleJsonService;
import com.ssm.base.util.Authority;
import com.ssm.base.view.QueryModelView;

@Controller
public class TestPageController {
	//final static Logger logger = LoggerFactory.getLogger(AccountController.class);
	private static Logger logger = Logger.getLogger(TestPageController.class);
	
	@Autowired private SsmAccountService accountService;
	@Autowired private HandleJsonService jsonService;
	
	/**
	 * bootstrap 结合 vue 页面（调查问卷）
	 */
	@RequestMapping(value = "admin/bt_and_vue", method = RequestMethod.GET)
	public String btAndVue(Model model) {
		return "admin/bt_and_vue";
	}
	
	/**
	 * 配合行内编辑，定义的接口
	 */
	@ResponseBody
	@RequestMapping(value = "admin/account/query/inline", method = RequestMethod.POST)
	public Map<String, Object> inline() {
		QueryModelView query = new QueryModelView();
        query.setPageNo(1);
        query.setPageSize(999);
        Object list = accountService.mapperQuery(query).getData();
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
		return map;
	}
	
	@RequestMapping(value = "admin/account/edit/inline", method = RequestMethod.GET)
	public String inlineIndex(Model model) {
		return "admin/eg_edit_innerLine";
	}
	
	@ResponseBody
	@RequestMapping(value = "admin/account/edit/inline/save", method = RequestMethod.POST)
	public String inlineSave(String str) {
		JSONObject JsonObject = JSONObject.parseObject(str);
		ValidateAccount validateAccount = JSONObject.toJavaObject(JsonObject, ValidateAccount.class);
		jsonService.handleJson(validateAccount);//-----------------------------
		System.out.println(validateAccount.getId());
		return "成功了，success!";
	}
	
	/**
	 * bootstrap table 分页
	 */
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
	
	/**
	 * 首先，这个类 没有 被注解（@Authority）
	 * 而 这个 url 是被 Authority拦截器 拦截下来了的
	 * 进入拦截器方法后，authority=null，表示没有“权限”，直接返回 预定错误信息
	 * 而只有当 拦截器验证 通过后，才会进入 方法体 内执行代码
	 */
	@Authority(AuthorityTypeEnum.Validate)
	@RequestMapping(value = "admin/account/drag/index", method = RequestMethod.GET)
	public String dragIndex(Model model) {
		return "admin/eg_drag_in_row";
	}
	
	/**
	 * 接收 行拖拽 传到后台的数据
	 */
	@ResponseBody
	@RequestMapping(value = "admin/account/drag/row", method = RequestMethod.GET)
	public String dragRow(String jsonData) {
		System.out.println(jsonData);
        return "success";
	}

}