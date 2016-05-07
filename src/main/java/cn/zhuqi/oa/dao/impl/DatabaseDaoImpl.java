package cn.zhuqi.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.DatabaseDao;
import cn.zhuqi.oa.model.Zfile;

@Repository("databaseDao")
public class DatabaseDaoImpl extends BaseDaoImpl implements DatabaseDao {

	@Override
	public List<Zfile> findAllFileList(int databaseId) {
		String hql = "select z from Zfile z where z.base.id = ?";
		return getSession().createQuery(hql).setParameter(0, databaseId).list();
	}

}
