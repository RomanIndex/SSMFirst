package com.ssm;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.entity.SsmMenu;
import com.ssm.admin.service.SsmAccountService;
import com.ssm.base.service.SsmMenuService;
import com.ssm.base.view.Result;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = "file:D:/eclipse-neon/workspace/SSMMaven/src/main/resources/spring-core-config.xml")
public class LoginCong {
	@Autowired private SsmMenuService menuService;
	@Autowired private SsmAccountService accountService;
	
	@Test
	public void getMenus() {
		Result<?> result = menuService.getMenuList();
		Map<String,Object> map = (Map<String, Object>) result.getData();
		List<SsmMenu> menus = (List<SsmMenu>) map.get("menu");
		for(SsmMenu each : menus){
			System.out.println(each.getName());
			if(ObjectUtils.notEqual(each.getChildMenus(), null) && each.getChildMenus().size() > 0){
				cycleChild(each.getName(), each.getChildMenus());
			}
		}
	}
	
	//递归遍历，并打印
	private void cycleChild(String pareantName, List<SsmMenu> childMenus) {
		for(SsmMenu each : childMenus){
			System.out.println("--"+ pareantName+ "："+ each.getName());
			if(ObjectUtils.notEqual(each.getChildMenus(), null) && each.getChildMenus().size() > 0){
				cycleChild(each.getName(), each.getChildMenus());
			}
		}
	}

	@Test
	public void validateAccount() throws UnsupportedEncodingException {
		//JSONObject jsonObject = JSONObject.fromObject(obj);
		//Result<?> result = accountService.mapperSave(jsonObject.toString(), "add");
		Result<?> result = accountService.mapperSave(createAccount(), "add");
		System.out.println(result);
	}
	
	/**
	 * 具体参考：https://www.cnblogs.com/zengda/p/4783514.html
	 * 
	 * 这种是用 插件 自带的方法和返回结果，不是很直观，加个util工具类，自己封装一下比较好
	 */
	@Test
	public void validateTest() throws UnsupportedEncodingException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		SsmAccount obj = createAccount();
		Set<ConstraintViolation<SsmAccount>> yz = validator.validate(obj);//validate会将所有的属性进行约束校验
		//Set<ConstraintViolation<Account>> yz = validator.validateProperty(obj, "age");//validateProperty是针对某一个具体的属性进行校验
		//Set<ConstraintViolation<Account>> yz = validator.validateValue(Account.class, "email", "123qq.com");//validateValue是对具体的某一个属性和特定的值进行校验
		//assertEquals(1, yz.size());
		System.out.println(yz);
	}
	
	@Test
	public void addAccount() {
		Result<?> result = accountService.mapperSave(createAccount(), "add");
		System.out.println(result.getCode() + "："+ result.getMsg());
	}
	
	@Test
	public void updateAccount() {
		String empNo = "ssm_account_12712";
		SsmAccount obj = createAccount();
		obj.setEmpNo(empNo);
		obj.setPassword("456");
		Result<?> result = accountService.mapperSave(obj, "update");
		System.out.println(result.getCode() + "："+ result.getMsg());
	}
	
	private SsmAccount createAccount() {
		SsmAccount obj = new SsmAccount();
		int fourNumber = (int) (Math.random() * 10000);
		obj.setEmpNo("ssm_account_1"+ fourNumber);
		obj.setName("ssmTest"+ fourNumber);
		obj.setPassword(fourNumber+ "");
		return obj;
	}
	
}