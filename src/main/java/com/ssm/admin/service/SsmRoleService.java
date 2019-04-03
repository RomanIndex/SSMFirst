package com.ssm.admin.service;

import com.ssm.admin.daoJpa.ISsmRoleDao;
import com.ssm.admin.entity.SsmRole;
import com.ssm.base.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SsmRoleService {
    @Autowired private ISsmRoleDao roleDao;

    public Result save(SsmRole obj) {
        SsmRole role = roleDao.save(obj);
        return new Result(Result.SUCCESS, "", null, obj);
    }
}
