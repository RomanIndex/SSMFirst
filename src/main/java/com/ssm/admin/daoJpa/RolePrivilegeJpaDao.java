package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmRolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePrivilegeJpaDao extends JpaRepository<SsmRolePrivilege, Integer> {
    List<SsmRolePrivilege> findByRoleId(String roleId);
}
