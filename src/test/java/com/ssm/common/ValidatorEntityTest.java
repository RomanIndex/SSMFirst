package com.ssm.common;

import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.service.AccountService;
import com.ssm.base.view.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    private AccountService accountService;

    @Test
    public void validateAccount() throws UnsupportedEncodingException {
        //JSONObject jsonObject = JSONObject.fromObject(obj);
        //Result<?> result = accountService.mapperSave(jsonObject.toString(), "add");
        Result<?> result = null;//accountService.mapperSave(createAccount(), "add");
        System.out.println(result);
    }

    /**
     * ����ο���https://www.cnblogs.com/zengda/p/4783514.html
     *
     * �������� ��� �Դ��ķ����ͷ��ؽ�������Ǻ�ֱ�ۣ��Ӹ�util�����࣬�Լ���װһ�±ȽϺ�
     */
    @Test
    public void validateTest() throws UnsupportedEncodingException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        SsmAccount obj = createAccount();
        Set<ConstraintViolation<SsmAccount>> yz = validator.validate(obj);//validate�Ὣ���е����Խ���Լ��У��
        //Set<ConstraintViolation<Account>> yz = validator.validateProperty(obj, "age");//validateProperty�����ĳһ����������Խ���У��
        //Set<ConstraintViolation<Account>> yz = validator.validateValue(Account.class, "email", "123qq.com");//validateValue�ǶԾ����ĳһ�����Ժ��ض���ֵ����У��
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
