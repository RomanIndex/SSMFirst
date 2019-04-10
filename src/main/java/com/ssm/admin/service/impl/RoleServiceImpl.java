package com.ssm.admin.service.impl;

import com.ssm.admin.daoJpa.RoleJpaDao;
import com.ssm.admin.entity.SsmRole;
import com.ssm.admin.service.RoleService;
import com.ssm.base.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends CommonServiceImpl<SsmRole, String> implements RoleService {
    @Autowired private RoleJpaDao roleDao;

    public Result save(SsmRole obj) {
        SsmRole role = roleDao.save(obj);
        return new Result(Result.SUCCESS, "", null, obj);
    }
}
