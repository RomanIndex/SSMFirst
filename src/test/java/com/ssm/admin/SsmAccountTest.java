package com.ssm.admin;

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
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class SsmAccountTest {
	@Autowired private SsmAccountService accountService;
	
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