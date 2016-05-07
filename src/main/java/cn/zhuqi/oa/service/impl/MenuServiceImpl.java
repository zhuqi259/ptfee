package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.MenuDao;
import cn.zhuqi.oa.model.Menu;
import cn.zhuqi.oa.service.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService {

	@Resource
	private MenuDao menuDao;

	@Override
	public void addMenu(Menu menu) {
		menuDao.save(menu);
	}

	@Override
	public void delMenu(int menuId) {
		menuDao.del(menuDao.findById(Menu.class, menuId));
	}

	@Override
	public void updateMenu(Menu menu) {
		menuDao.update(menu);
	}

	@Override
	public List<Menu> findAllTopMenus() {
		return menuDao.findAllTopMenus();
	}

	@Override
	public Menu findMenuById(int menuId) {
		return menuDao.findById(Menu.class, menuId);
	}

	@Override
	public List<Integer> findAllTopMenuIds() {
		return menuDao.findAllTopMenuIds();
	}

}
