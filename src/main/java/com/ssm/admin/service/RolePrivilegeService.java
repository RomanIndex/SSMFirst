package com.ssm.admin.service;

import com.ssm.admin.daoJpa.RolePrivilegeJpaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePrivilegeService {
    @Autowired private RolePrivilegeJpaDao rolePrivilegeJpaDao;
}
