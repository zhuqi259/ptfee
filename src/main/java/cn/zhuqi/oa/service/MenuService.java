package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.Menu;

public interface MenuService {
	/**
	 * 添加菜单资源
	 * @param menu
	 */
	public void addMenu(Menu menu);
	
	/**
	 * 更新菜单资源
	 * @param menu
	 */
	public void updateMenu(Menu menu);
	
	/**
	 * 删除菜单资源
	 * @param menuId
	 */
	public void delMenu(int menuId);
	
	/**
	 * 查询出所有顶级菜单
	 * @return
	 */
	public List<Menu> findAllTopMenus();
	
	public Menu findMenuById(int menuId);
	
	public List<Integer> findAllTopMenuIds();
}
