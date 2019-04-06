package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmRolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePrivilegeJpaDao extends JpaRepository<SsmRolePrivilege, Integer> {
}
