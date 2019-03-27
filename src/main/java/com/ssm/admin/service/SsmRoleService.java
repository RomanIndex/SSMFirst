package com.ssm.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.admin.dao.ISsmRoleDao;

@Service
public class SsmRoleService {
	@Autowired private ISsmRoleDao roleDao;

}
