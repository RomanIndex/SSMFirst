package com.ssm.common.service;

import com.ssm.common.enumeration.ArtificialKeyEnum;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

/**
 * 自定义UUID生成器
 * @author sevenlin
 */
public class CustomUUIDGenerator extends UUIDGenerator {
    ArtificialKeyService keyService = new ArtificialKeyService();

    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        System.out.println("CustomUUIDGenerator Object："+ object);
        Object id = keyService.getCustomUUIDByClass(object);
        System.out.println("keyService id = "+ (Serializable) id);
        if (id != null) {
            return (Serializable)id;
        }
        return super.generate(session, object);
    }
}
