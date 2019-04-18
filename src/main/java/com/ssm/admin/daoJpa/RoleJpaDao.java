package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleJpaDao extends JpaRepository<SsmRole, String>, JpaSpecificationExecutor<SsmRole> {
}
