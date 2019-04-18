package com.ssm.admin.service.impl;

import com.ssm.admin.dao.AccountRoleMapper;
import com.ssm.admin.daoJpa.AccountRoleJpaDao;
import com.ssm.admin.entity.SsmAccountRole;
import com.ssm.admin.entity.SsmRole;
import com.ssm.admin.service.AccountRoleService;
import com.ssm.admin.service.RoleService;
import com.ssm.base.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountRoleServiceImpl extends CommonServiceImpl<SsmAccountRole, Integer> implements AccountRoleService {
    @Autowired private AccountRoleJpaDao accountRoleJpaDao;
    @Autowired private AccountRoleMapper accountRoleMapper;
    @Autowired private RoleService roleService;

    @Override
    public List<SsmAccountRole> getByEmpNo(String empNo) {
        return accountRoleJpaDao.findByEmpNo(empNo);
    }

    @Override
    public Result<?> updateByAccount(String empNo, List<String> newRoleIds) {
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

    @Override
    public Result<?> getRoleScopeByAccount(String empNo) {
        List<SsmAccountRole> accountRoles = this.getByEmpNo(empNo);
        List<String> roleIds = accountRoles.stream().map(i -> i.getRoleId()).collect(Collectors.toList());
        List<SsmRole> totalRole = roleService.selectAll();
        Map<String, Object> map = new HashMap<>();
        //List<SsmRole> leftRole = totalRole.stream().filter(i -> !roleIds.contains(i.getRoleId())).collect(Collectors.toList());
        map.put("leftRole", totalRole.stream().filter(i -> !roleIds.contains(i.getRoleId())).collect(Collectors.toList()));
        map.put("hadRole", totalRole.stream().filter(i -> roleIds.contains(i.getRoleId())).collect(Collectors.toList()));
        return Result.success(map);
    }

}
