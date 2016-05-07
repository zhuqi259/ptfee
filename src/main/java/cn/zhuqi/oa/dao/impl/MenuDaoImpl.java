package cn.zhuqi.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.MenuDao;
import cn.zhuqi.oa.model.Menu;

@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl implements MenuDao {

	@Override
	public List<Menu> findAllTopMenus() {
		String hql = "select m from Menu m where m.parent is null order by m.orderNumber";

		return getSession().createQuery(hql).list();
	}

	@Override
	public List<Integer> findAllTopMenuIds() {
		String hql = "select m.id from Menu m where m.parent is null order by m.orderNumber";
		return getSession().createQuery(hql).list();
	}

}
