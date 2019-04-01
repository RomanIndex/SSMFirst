package com.ssm.common;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.service.SsmAccountService;
import com.ssm.base.view.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.UnsupportedEncodingException;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class ValidatorEntityTest {
    @Autowired
    private SsmAccountService accountService;

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

    private SsmAccount createAccount() {
        SsmAccount obj = new SsmAccount();
        int fourNumber = (int) (Math.random() * 10000);
        obj.setEmpNo("ssm_account_1"+ fourNumber);
        obj.setName("ssmTest"+ fourNumber);
        obj.setPassword(fourNumber+ "");
        return obj;
    }
}
