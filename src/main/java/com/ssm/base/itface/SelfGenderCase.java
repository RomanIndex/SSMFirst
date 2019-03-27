package com.ssm.base.itface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SelfGenderCaseValidator.class)

/**
 * 这里需要关注的有三点：
 * 第一个是@Constraint(validatedBy = GenderTypeValidator.class) 这里指定了下面将要说的约束校验的实现类。
 * 第二个是message属性，用于校验值非法是缺省的消息模版。
 * 第三个是注解对应的约束值value。在这个例子中，注解的约束值用的是一个枚举值表示男/女，缺省值为女
 * 
 * 第二步是实现了javax.validation.ConstraintValidator<A extends Annotation, T>接口的约束校验实现类，
 * 上面说的validatedBy指向的就是该实现类，
 * 其中A表示自定义的注解类，
 * T表示进行校验的字段的类型。具体的逻辑定于如下：SelfGenderCaseValidator.class类里面
 *
 */

public @interface SelfGenderCase {
	String message() default "genderType invalid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	// GenderType value() default GenderType.FEMALE;
	String value() default "FEMALE";

}
