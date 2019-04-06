package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmAccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleJpaDao extends JpaRepository<SsmAccountRole, Integer> {
}
