package com.ssm.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidationUtil{
	
	private static Validator validator;

    static {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    //原为void，若size > 0，则 当做异常抛出 返回结果
    public static <T> void validate(T t) throws ValidationException {
        Set<ConstraintViolation<T>> set = validator.validate(t);
        System.out.println("------------::::"+ set);
        
        if (set.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            
            for (ConstraintViolation<T> val : set) {
                validateError.append(val.getMessage() + " ;");
            }
            
            throw new ValidationException(validateError.toString());
        }
    }
    
    /**
     * 注意groups，如果 加 Default.class，那么默认没有加 限定接口（如Add.class、Update.class等），也会一起 被校样
     * 如果 不加 Default.class，那么只会验证加了 限定接口 类型的（描述不清楚！！！）
     * @param t
     * @param groups 第二个参数可以为空（为空，则默认调用 上面 的方法了）
     * @return
     */
	public static <T> List<String> validate(T t, Class<?>... groups) {
        //validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> cvSet = validator.validate(t, groups);
        List<String> msgList = new ArrayList<>();
        
        cvSet.stream().map(cvItem -> msgList.add(cvItem.getMessage())).collect(Collectors.toSet());
        
        return msgList;
    }
    
}
