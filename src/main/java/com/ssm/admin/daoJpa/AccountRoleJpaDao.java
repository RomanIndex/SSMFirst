package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmAccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AccountRoleJpaDao extends JpaRepository<SsmAccountRole, Integer>, JpaSpecificationExecutor<SsmAccountRole> {
    List<SsmAccountRole> findByEmpNo(String empNo);
}
