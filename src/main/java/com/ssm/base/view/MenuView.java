package com.ssm.base.view;

import java.util.List;

import com.ssm.base.entity.Menu;

public class MenuView extends Menu {

	List<Menu> menuItems;

	public List<Menu> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<Menu> menuItems) {
		this.menuItems = menuItems;
	}
	
}
