package com.ssm.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssm.admin.daoJpa.ISsmModuleDao;
import com.ssm.admin.entity.SsmModule;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.admin.entity.SsmMenu;
import com.ssm.base.dao.ISsmMenuDao;
import com.ssm.base.view.Result;

@Service
public class SsmMenuService {
	@Autowired private ISsmMenuDao menuDao;
	@Autowired private ISsmModuleDao moduleDao;

	public Result<?> save(SsmModule module){
		moduleDao.save(module);
		return null;
	}
	
	//为什么是ByRole？如果不同角色的菜单有重复，在service过滤
	public Result<?> listMenuByRole(String roleId){
		//List<SsmMenu> rootMenu = menuDao.selectAll();
		List<SsmMenu> rootMenu = menuDao.getMenuList();
		//rootMenu.stream().forEach(item -> System.out.println(item.getMenuId() + "：" + item.getName()));
		// 最后的结果
	    List<SsmMenu> menuList = new ArrayList<SsmMenu>();
	    // 先找到所有的一级菜单
	    for (int i = 0; i < rootMenu.size(); i++) {
	        // 一级菜单没有parentId
	        if (StringUtils.isBlank(rootMenu.get(i).getParentId())) {
	            menuList.add(rootMenu.get(i));
	        }
	    }
	    // 为一级菜单设置子菜单，getChild是递归调用的
	    for (SsmMenu menu : menuList) {
	        menu.setChildMenus(getChild(menu.getMenuId(), rootMenu));
	    }
	    Map<String,Object> map = new HashMap<>();
	    map.put("menu", menuList);
	    //System.out.println(JSONObject.fromObject(jsonMap).toString());
	    
	    return new Result<>(0, "", "", map);
	}
	
	/**
	 * 递归查找子菜单
	 * 
	 * @param id 当前菜单id
	 * @param rootMenu 要查找的列表
	 * @return
	 */
	private List<SsmMenu> getChild(String id, List<SsmMenu> rootMenu) {
	    // 子菜单
	    List<SsmMenu> childList = new ArrayList<>();
	    for (SsmMenu menu : rootMenu) {
	        // 遍历所有节点，将父菜单id与传过来的id比较
	        if (StringUtils.isNotBlank(menu.getParentId())) {
	            if (menu.getParentId().equals(id)) {
	                childList.add(menu);
	            }
	        }
	    }
	    // 把子菜单的子菜单再循环一遍
	    for (SsmMenu menu : childList) {
	    	/*// 没有url子菜单还有子菜单（与 我现在的场景不符，不需要这个判断）
	        if (StringUtils.isBlank(menu.getUrl())) {
	            // 递归
	            menu.setChildMenus(getChild(menu.getMenuId(), rootMenu));
	        }*/
	    	menu.setChildMenus(getChild(menu.getMenuId(), rootMenu));
	    }
	    // 递归退出条件
	    if (childList.size() == 0) {
	        return null;
	    }
	    
	    return childList;
	}

}