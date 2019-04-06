package com.ssm.admin.service;

import com.ssm.admin.daoJpa.PrivilegeJpaDao;
import com.ssm.admin.entity.SsmPrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService {
    @Autowired private PrivilegeJpaDao privilegeJpaDao;

    public SsmPrivilege save(SsmPrivilege obj) {
        SsmPrivilege privilege = privilegeJpaDao.saveAndFlush(obj);
        return privilege;
    }
}
