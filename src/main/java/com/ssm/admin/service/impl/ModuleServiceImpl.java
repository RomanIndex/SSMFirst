package com.ssm.admin.service.impl;

import com.ssm.admin.dao.ModuleMapper;
import com.ssm.admin.daoJpa.ModuleJpaDao;
import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.service.ModuleService;
import com.ssm.admin.service.PrivilegeService;
import com.ssm.admin.view.RecursionMenuVo;
import com.ssm.admin.entity.SsmModule;
import com.ssm.admin.view.TreegridView;
import com.ssm.base.view.Result;
import com.ssm.common.enumeration.OperateEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleServiceImpl extends CommonServiceImpl<SsmModule, String> implements ModuleService {
    @Autowired private ModuleJpaDao moduleJpaDao;
    @Autowired private PrivilegeService privilegeService;
    @Autowired private ModuleMapper moduleMapper;

    //取module，封装成treegril数据格式，带条件查询
    @Override
    public List<TreegridView> getModuleForTreegrid(String roleId) {
        //获取所有 生成了operate = show 的module
        List<SsmPrivilege> haveTicketModules = privilegeService.getTicket(OperateEnum.select);
        //获取所有 有show票据的module
        List<SsmPrivilege> roleGetShowTicket = privilegeService.getTicket("", OperateEnum.select);
        //所有module表之间的父子关系集合//name参数作模糊查询用
        List<TreegridView> baseTreegrid = moduleMapper.getModuleForTreegrid("");
        for(TreegridView eachModule : baseTreegrid){

            if(eachModule.getType() == 1){
                eachModule.setIconCls("icon-edit");
            }

            for(SsmPrivilege eachHTM : haveTicketModules){
                //权限表是否生成了show
                if(eachHTM.getModuleId().equals(eachModule.getModuleId())){
                    eachModule.setShowTicket(true);
                    break;
                }
            }

            for(SsmPrivilege eachGST : roleGetShowTicket){
                //角色 拥有的show的模块
                if(eachGST.getModuleId().equals(eachModule.getModuleId())){
                    eachModule.setGetShowTicket(true);
                    eachModule.setChecked(true);
                    break;
                }
            }
        }

        return baseTreegrid;
    }

    @Override
    public Result<?> getTopMenu() {
        /*SsmModule entity = new SsmModule();
        entity.setType((short) 1);
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("id", lam -> lam.contains());
        Example<SsmModule> example = Example.of(entity, matcher);
        List<SsmModule> list = this.listByExample(example);*/
        List<SsmModule> jpaList = moduleJpaDao.findByTypeAndParentIdIsNull(1);
        return Result.success(jpaList);
    }

    @Override
    public Result<?> getSecondMenu(String belongId) {
        List<SsmModule> list = moduleJpaDao.findByTypeAndBelongModule(2, belongId);
        return Result.success(list);
    }

    //1：模块；2：菜单；3：按钮
    @Override
    public Result<?> getBtnMenu(String belongId) {
        List<SsmModule> list = moduleJpaDao.findByTypeAndBelongModule(3, belongId);
        return Result.success(list);
    }

    public Result<?> listMenuByRoleId(String roleId) {
        //查询 角色 拥有的 所有菜单权限，待补充
        List<SsmModule> modules = moduleJpaDao.findAll();
        //module转成常用menu
        List<RecursionMenuVo> rootMenus = getMemuFromModule(modules);
        //递归处理menu
        List<RecursionMenuVo> recursionMenuVos = recursionedMenu(rootMenus);
        return Result.success(recursionMenuVos);
    }

    private List<RecursionMenuVo> recursionedMenu(List<RecursionMenuVo> rootMenus) {
        List<RecursionMenuVo> menuList = new ArrayList<RecursionMenuVo>();

        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenus.size(); i++) {
            // 一级菜单没有parentId
            if (StringUtils.isBlank(rootMenus.get(i).getParentId())) {
                menuList.add(rootMenus.get(i));
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (RecursionMenuVo menu : menuList) {
            menu.setChildMenus(getChild(menu.getMenuId(), rootMenus));
        }

        return menuList;
    }

    /**
     * 递归查找子菜单
     *
     * @param id 当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private List<RecursionMenuVo> getChild(String id, List<RecursionMenuVo> rootMenu) {
        // 子菜单
        List<RecursionMenuVo> childList = new ArrayList<>();
        for (RecursionMenuVo menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(menu.getParentId())) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (RecursionMenuVo menu : childList) {
	    	// 没有url子菜单还有子菜单（与 我现在的场景不符，不需要这个判断）
	        if (StringUtils.isBlank(menu.getUrl())) {
	            // 递归
	            menu.setChildMenus(getChild(menu.getMenuId(), rootMenu));
	        }
            menu.setChildMenus(getChild(menu.getMenuId(), rootMenu));
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }

        return childList;
    }

    //菜单 来自 module，又不完全是，这里特殊处理（菜单比较特殊）
    private List<RecursionMenuVo> getMemuFromModule(List<SsmModule> modules) {
        List<RecursionMenuVo> menus = modules.stream().map(each -> {
            RecursionMenuVo menu = new RecursionMenuVo();
            menu.setParentId(each.getParentId());
            menu.setMenuId(each.getModuleId());
            menu.setName(each.getName());
            menu.setUrl(each.getUrl());
            menu.setSeq(each.getSeq());
            menu.setIcon(each.getStyle());
            menu.setCreateTime(each.getCreateTime());
            menu.setStatus(each.isStatus());
            return menu;
        }).collect(Collectors.toList());
        return menus;
    }

}
