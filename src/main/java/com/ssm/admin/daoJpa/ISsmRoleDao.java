package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISsmRoleDao extends JpaRepository<SsmRole, String> {
}
