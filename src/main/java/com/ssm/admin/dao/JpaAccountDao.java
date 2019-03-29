package com.ssm.admin.dao;

import com.ssm.admin.entity.jpa.JpaAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountDao extends JpaRepository<JpaAccount, String> {
}
