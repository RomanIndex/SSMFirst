package com.ssm;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ssm.base.service.TestJava8Service;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = "file:D:/eclipse-neon/workspace/SSMMaven/src/main/resources/spring-core-config.xml")
public class TestJava8 {
	@Resource private TestJava8Service java8Service;
	
	@Test
	public void test(){
		System.out.println("test java8 !!!");
	}
	
	@Test
	public void test_1_Optional(){
		java8Service.test_1_Optional();
	}

}
