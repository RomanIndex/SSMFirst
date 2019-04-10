package com.ssm.admin.service.impl;

import com.ssm.admin.daoJpa.RolePrivilegeJpaDao;
import com.ssm.admin.entity.SsmRolePrivilege;
import com.ssm.admin.service.RolePrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePrivilegeServiceImpl extends CommonServiceImpl<SsmRolePrivilege, Integer> implements RolePrivilegeService {
    @Autowired private RolePrivilegeJpaDao rolePrivilegeJpaDao;
}
