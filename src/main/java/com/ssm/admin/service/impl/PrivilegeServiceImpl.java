package com.ssm.admin.service.impl;

import com.ssm.admin.daoJpa.PrivilegeJpaDao;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl extends CommonServiceImpl<SsmPrivilege, String> implements PrivilegeService {
    @Autowired private PrivilegeJpaDao privilegeJpaDao;

    public SsmPrivilege save(SsmPrivilege obj) {
        SsmPrivilege privilege = privilegeJpaDao.saveAndFlush(obj);
        return privilege;
    }
}
