package com.ssm;

import com.alibaba.fastjson.JSON;
import com.ssm.common.entity.ComStudent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class SpringInitBean {

    @Test
    public void springBean(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-init-bean.xml");
        ComStudent springBean = context.getBean("testSpringBeanId", ComStudent.class);
        System.out.println("》》》"+ JSON.toJSONString(springBean));
    }
}
