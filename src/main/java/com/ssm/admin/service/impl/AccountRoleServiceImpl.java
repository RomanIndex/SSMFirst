package com.ssm.admin.service.impl;

import com.ssm.admin.dao.AccountRoleMapper;
import com.ssm.admin.daoJpa.AccountRoleJpaDao;
import com.ssm.admin.entity.SsmAccountRole;
import com.ssm.admin.service.AccountRoleService;
import com.ssm.base.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountRoleServiceImpl extends CommonServiceImpl<SsmAccountRole, Integer> implements AccountRoleService {
    @Autowired private AccountRoleJpaDao accountRoleJpaDao;
    @Autowired private AccountRoleMapper accountRoleMapper;

    @Override
    public List<SsmAccountRole> getByEmpNo(String empNo) {
        return accountRoleJpaDao.findByEmpNo(empNo);
    }

    @Override
    public Result<?> updateRoleByEmpNo(String empNo, List<String> newRoleIds) {
        List<SsmAccountRole> oldRoles = getByEmpNo(empNo);
        List<String> oldRoleIds = oldRoles.stream().map(i -> i.getRoleId()).collect(Collectors.toList());
        //先 删 再 增
        List<String> delList = oldRoleIds.stream().filter(i -> !newRoleIds.contains(i)).collect(Collectors.toList());

        List<String> addList = newRoleIds.stream().filter(i -> !oldRoleIds.contains(i)).collect(Collectors.toList());

        accountRoleMapper.deleteByRoleIds(empNo, delList);

        List<SsmAccountRole> addObject = new ArrayList<>();
        Date createTime = Calendar.getInstance().getTime();
        addList.forEach(i -> {
            SsmAccountRole obj = new SsmAccountRole();
            obj.setEmpNo(empNo);
            obj.setRoleId(i);
            obj.setCreateTime(createTime);
            addObject.add(obj);
        });
        this.batchCreate(addObject);

        return Result.success("用户的角色更新成功！", null);
    }
}
