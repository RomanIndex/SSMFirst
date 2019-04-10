package com.ssm.admin.service.impl;

import com.ssm.admin.daoJpa.AccountRoleJpaDao;
import com.ssm.admin.entity.SsmAccountRole;
import com.ssm.admin.service.AccountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountRoleServiceImpl extends CommonServiceImpl<SsmAccountRole, Integer> implements AccountRoleService {
    @Autowired private AccountRoleJpaDao accountRoleJpaDao;
}
