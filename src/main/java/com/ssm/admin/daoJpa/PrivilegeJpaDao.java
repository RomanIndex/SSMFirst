package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeJpaDao extends JpaRepository<SsmPrivilege, String> {
}
