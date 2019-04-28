package com.ssm.admin.service.impl;

import com.ssm.admin.dao.RolePrivilegeMapper;
import com.ssm.admin.daoJpa.RolePrivilegeJpaDao;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.entity.SsmRolePrivilege;
import com.ssm.admin.param.SsmTicket;
import com.ssm.admin.service.ModuleService;
import com.ssm.admin.service.PrivilegeService;
import com.ssm.admin.service.RolePrivilegeService;
import com.ssm.admin.view.RolePrivilegeView;
import com.ssm.admin.view.TreegridView;
import com.ssm.base.view.Result;
import com.ssm.common.enumeration.OperateEnum;
import com.ssm.common.util.Java8Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RolePrivilegeServiceImpl extends CommonServiceImpl<SsmRolePrivilege, Integer> implements RolePrivilegeService {
    @Autowired private RolePrivilegeJpaDao rolePrivilegeJpaDao;
    @Autowired private RolePrivilegeMapper rolePrivilegeMapper;
    @Autowired private PrivilegeService privilegeService;
    @Autowired private ModuleService moduleService;

    @Override
    public List<SsmRolePrivilege> getByRole(String roleId) {
        SsmRolePrivilege entity = new SsmRolePrivilege();
        entity.setRoleId(roleId);
        entity.setStatus(true);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("roleId", m -> m.exact())
                .withMatcher("status", m -> m.equals(true));

        Example<SsmRolePrivilege> example = Example.of(entity, matcher);
        return this.listByExample(example);
    }

    @Override
    public Result<?> mixUpdatePrivilege(String roleId, List<String> newCodes) {
        List<SsmRolePrivilege> oldPrivileges = rolePrivilegeJpaDao.findByRoleId(roleId);
        List<String> oldCodes = oldPrivileges.stream().map(i -> i.getPriCode()).collect(Collectors.toList());
        //先 删 再 增
        List<String> delList = oldCodes.stream().filter(i -> !newCodes.contains(i)).collect(Collectors.toList());

        List<String> addList = newCodes.stream().filter(i -> !oldCodes.contains(i)).collect(Collectors.toList());

        rolePrivilegeMapper.deleteByCodes(roleId, delList);

        List<SsmRolePrivilege> addObject = new ArrayList<>();
        Date createTime = Calendar.getInstance().getTime();
        addList.forEach(i -> {
            SsmRolePrivilege obj = new SsmRolePrivilege();
            obj.setRoleId(roleId);
            obj.setPriCode(i);
            obj.setCreateTime(createTime);
            addObject.add(obj);
        });
        this.batchCreate(addObject);

        return Result.success("角色的权限更新成功！", null);
    }

    /**
     * 菜单权限易混点
     * 显示的地方：
     * （1）左侧菜单栏，符合要求，点击要有页面加载的，【在哪标记、怎么标记】
     *
     * （2）module管理表格，就是module表，即所有的静态资源都要显示（parentId、belongModule都要显示）
     * （3）角色授权列表，也是module，也是要加载所有module静态资源
     */

    @Override
    public List<TreegridView> getMenuTreegrid(String roleId) {
        //获取 由module生成的 菜单票据（即operateEnumName = show的 权限）
        List<SsmPrivilege> menuTicket = privilegeService.getTicket(OperateEnum.show);
        //角色 已经 拥有的 菜单票据
        List<RolePrivilegeView> roleTicket = rolePrivilegeMapper.getByRoleAndOperate(roleId, OperateEnum.show.name());
        ////取module表之间的父子关系集合，封装成treegril可解析的数据格式，带条件查询
        List<TreegridView> baseTreegrid = moduleService.getMenuTreegrid("");
        for(TreegridView eachModule : baseTreegrid){

            if(eachModule.getType() == 3){
                //按钮的样式
                eachModule.setIconCls("icon-edit");
            }

            for(SsmPrivilege pri : menuTicket){
                //权限表是否生成了show
                if(pri.getModuleId().equals(eachModule.getModuleId())){
                    SsmTicket ticket = new SsmTicket();
                    ticket.setCode(pri.getCode());
                    ticket.setModuleId(pri.getModuleId());
                    ticket.setOperate(pri.getOperateEnumName());
                    eachModule.setTicket(ticket);
                    break;
                }
            }

            for(RolePrivilegeView rolePriView : roleTicket){
                //角色 拥有的show的模块
                if(rolePriView.getModuleId().equals(eachModule.getModuleId())){
                    eachModule.setRoleTicket(true);
                    eachModule.setChecked(true);
                    break;
                }
            }
        }

        return baseTreegrid;
    }

    @Override
    public List<SsmRolePrivilege> listDistinctByRoleIds(List<String> roleIds) {
        List<SsmRolePrivilege> list = new ArrayList<>();
        if(roleIds.size() > 0){
            roleIds.stream().forEach(e -> {
                List<SsmRolePrivilege> subList = this.getByRole(e);
                list.addAll(subList);
            });
        }
        //下面这种网上说需要重写实体类的equals和hashcode方法，未亲自测试
        //List<SsmRolePrivilege> distinct = list.stream().distinct().collect(Collectors.toList());
        //根据 属性 过滤，顺便引入这个公共方法
        List<SsmRolePrivilege> distinct = list.stream().filter(Java8Util.distinctByKey(b -> b.getPriCode())).collect(Collectors.toList());
        return distinct;
    }

}
