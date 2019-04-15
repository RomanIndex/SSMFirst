package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmAccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRoleJpaDao extends JpaRepository<SsmAccountRole, Integer> {
    List<SsmAccountRole> findByEmpNo(String empNo);
}
