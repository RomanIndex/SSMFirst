package com.ssm.common;

import com.ssm.common.util.RedisUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class RedisTest {

    public static void main(String[] args) throws Exception {
        @SuppressWarnings("resource")
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext-redis.xml");
        RedisUtil redisUtil=(RedisUtil) context.getBean("redisUtil");

        //=====================testString======================
        System.out.println(redisUtil.set("ssm_address", "河北邯郸", 50));

        System.out.println(redisUtil.get("ssm_address"));

        System.out.println(redisUtil.get("userMap"));

		Map<String,Object> map = new HashMap<>();
		map.put("name", "王赛超");
		map.put("age", 24);
        map.put("mobile", "15699874411");
		map.put("address", "河北邯郸666");
		redisUtil.hmset("userMap", map, 1000);

        System.out.println(redisUtil.getExpire("userMap"));
    }

}
