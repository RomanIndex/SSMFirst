package com.ssm.admin.service;

import com.ssm.admin.daoJpa.AccountRoleJpaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountRoleService {
    @Autowired private AccountRoleJpaDao accountRoleJpaDao;
}
