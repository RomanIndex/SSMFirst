package com.ssm.admin.daoJpa;

import com.ssm.admin.entity.SsmModule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISsmModuleDao extends JpaRepository<SsmModule, Integer> {
}
