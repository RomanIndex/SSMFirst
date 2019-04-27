package com.ssm.base.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssm.base.Enum.AuthorityTypeEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
//@Target({ElementType.TYPE})
@Documented

public @interface Authority {
	//默认验证
	AuthorityTypeEnum value() default AuthorityTypeEnum.Validate;
}