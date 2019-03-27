package com.ssm.base.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssm.base.entity.Account;

@Service
public class TestJava8Service {
	
	public void test_1_Optional() {
		Account account = new Account();
		//account.setAvatar("AVATAR");
		//Optional<String> opt = Optional.of(account.getAvatar());
		Optional<String> opt = Optional.ofNullable(account.getAvatar());
		System.out.println(opt.orElse("are you sa!"));//存在即返回, 无则提供默认值
		System.out.println(opt.orElseGet(() -> hanlderNull()));//存在即返回, 无则由函数来产生
		System.out.println(opt.map(a -> a + "----").orElse("vvvvv"));//存在才对它做点什么（map 函数隆重登场）
	}

	private String hanlderNull() {
		return "you a null!!";
	}

}
