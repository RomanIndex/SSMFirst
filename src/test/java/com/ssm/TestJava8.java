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
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
/**
 * 建这个特殊 测试类 的原因：方便数据处理的单元测试
 *  引入其他接口 从数据库 取出的数据，再在该测试类调用java8的聚合等操作方法（将一些常用的封装并存入common）
 */
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
