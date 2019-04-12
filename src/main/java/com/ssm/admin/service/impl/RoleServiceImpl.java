package com.ssm.admin.service.impl;

import com.ssm.admin.daoJpa.RoleJpaDao;
import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.entity.SsmRole;
import com.ssm.admin.service.RoleService;
import com.ssm.admin.view.AdminQueryView;
import com.ssm.base.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends CommonServiceImpl<SsmRole, String> implements RoleService {
    @Autowired private RoleJpaDao roleDao;

    @Override
    public Result<?> jpaQuery(AdminQueryView query) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");//2.x写法，推荐
        Pageable pageable = PageRequest.of(query.getPageNo() - 1, query.getPageSize(), sort);
        Page<SsmRole> pageData = this.page(pageable);//js取值也要对应的改
        return Result.success(pageData);
    }
}
