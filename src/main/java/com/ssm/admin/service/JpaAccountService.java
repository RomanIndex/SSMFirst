package com.ssm.admin.service;

import com.ssm.admin.entity.jpa.JpaAccount;
import com.ssm.admin.daoJpa.JpaAccountDao;
import com.ssm.base.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaAccountService {
    @Autowired private JpaAccountDao jpaAccountDao;

    public Result<?> add(JpaAccount jpaAccount){
        jpaAccountDao.save(jpaAccount);
        return null;
    }

}
