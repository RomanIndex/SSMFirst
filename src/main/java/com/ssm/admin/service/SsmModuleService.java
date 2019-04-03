package com.ssm.admin.service;

import com.ssm.admin.daoJpa.ISsmModuleDao;
import com.ssm.admin.entity.RecursionMenu;
import com.ssm.admin.entity.SsmModule;
import com.ssm.base.view.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SsmModuleService {
    @Autowired private ISsmModuleDao moduleDao;

    public Result<?> save(SsmModule module) {
        SsmModule obj = moduleDao.save(module);
        return new Result(Result.SUCCESS, "", null, obj);
    }

    public Result<?> batchSave(List<SsmModule> modules) {
        List<SsmModule> reList = moduleDao.save(modules);
        return new Result(Result.SUCCESS, "", null, reList);
    }

    public Result<?> listMenuByRoleId(String roleId) {
        List<SsmModule> modules = moduleDao.findAll();
        List<RecursionMenu> rootMenus = getMemuFromModule(modules);
        List<RecursionMenu> recursionMenus = recursionedMenu(rootMenus);
        return new Result<>(Result.SUCCESS, "", null, recursionMenus);
    }

    private List<RecursionMenu> recursionedMenu(List<RecursionMenu> rootMenus) {
        List<RecursionMenu> menuList = new ArrayList<RecursionMenu>();

        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenus.size(); i++) {
            // 一级菜单没有parentId
            if (StringUtils.isBlank(rootMenus.get(i).getParentId())) {
                menuList.add(rootMenus.get(i));
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (RecursionMenu menu : menuList) {
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
    private List<RecursionMenu> getChild(String id, List<RecursionMenu> rootMenu) {
        // 子菜单
        List<RecursionMenu> childList = new ArrayList<>();
        for (RecursionMenu menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(menu.getParentId())) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (RecursionMenu menu : childList) {
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
    private List<RecursionMenu> getMemuFromModule(List<SsmModule> modules) {
        List<RecursionMenu> menus = modules.stream().map(each -> {
            RecursionMenu menu = new RecursionMenu();
            menu.setParentId(each.getParentId());
            menu.setMenuId(each.getModuleId());
            menu.setName(each.getName());
            menu.setUrl(each.getUrl());
            menu.setSequence(each.getSeq());
            menu.setIcon(each.getStyle());
            menu.setCreateTime(each.getCreateTime());
            menu.setStatus(each.getStatus());
            return menu;
        }).collect(Collectors.toList());
        return menus;
    }
}
