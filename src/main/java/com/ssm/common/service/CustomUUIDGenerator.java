package com.ssm.common.service;

import com.ssm.base.service.ReflectFieldService;
import com.ssm.common.enumeration.ArtificialKeyEnum;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 自定义UUID生成器
 * @author sevenlin
 */
public class CustomUUIDGenerator extends UUIDGenerator {
    ArtificialKeyService keyService = new ArtificialKeyService();

    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        System.out.println("CustomUUIDGenerator Object："+ object);
        //先获取主键属性名
        Field[] fields = object.getClass().getDeclaredFields();
        String idField = null;
        for(Field field : fields){
            //Annotation[] fieldAnnotations = field.getAnnotations();//获取属性上所有注解
            Annotation idAnno = field.getAnnotation(Id.class);//获取指定的注解
            if(null != idAnno){
                idField = field.getName();
                break;
            }
        }
        Object id = null;
        if(null != idField){
            //有注解@Id的，则先取 对应属性的值
            id = ReflectFieldService.getValueByKey(object, idField);
        }

        if(null == id){
            //注解@id的若没有set值，则取 自定义 生成的主键值
            id = keyService.getCustomUUIDByClass(object);
        }
        System.out.println("keyService id = "+ (Serializable) id);
        //如果枚举类里面没有自定义主键值，就使用系统默认生成的值
        if (id != null) {
            return (Serializable)id;
        }
        return super.generate(session, object);//36位的，带4个-的值
    }
}
