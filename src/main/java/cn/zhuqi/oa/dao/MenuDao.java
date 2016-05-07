package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.model.Menu;

public interface MenuDao extends BaseDao {
	public List<Menu> findAllTopMenus();
	public List<Integer> findAllTopMenuIds();
}
