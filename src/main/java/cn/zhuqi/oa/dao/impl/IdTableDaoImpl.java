package cn.zhuqi.oa.dao.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.IdTableDao;
import cn.zhuqi.oa.model.IdTable;

@Repository("idTableDao")
public class IdTableDaoImpl extends BaseDaoImpl implements IdTableDao {

	@Override
	public IdTable zgetSingle() {
		int myYear = new GregorianCalendar().get(Calendar.YEAR);
		IdTable id = null;
		try {
			id = (IdTable) getSession()
					.createQuery("select i from IdTable i where i.myYear = ?")
					.setParameter(0, myYear).uniqueResult();
			if (id == null) {
				id = new IdTable();
				id.setMyId(1);
				id.setMyYear(myYear);
				getSession().save(id);
			}
		} catch (HibernateException e) {
			// throw new RuntimeException("无法得到ID，有可能数据库中不存在或者数据不只一条");
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void initId() {
		System.out
				.println("-------------idTable-------init-------------------");
		int myYear = new GregorianCalendar().get(Calendar.YEAR);
		List<IdTable> ids = getSession()
				.createQuery("select i from IdTable i where i.myYear = ?")
				.setParameter(0, myYear).list();
		if (ids.isEmpty()) {
			IdTable id = new IdTable();
			id.setMyId(1);
			getSession().save(id);
		} else if (ids.size() > 1) {
			getSession()
					.createQuery("delete from IdTable i where i.myYear = ?")
					.setParameter(0, myYear).executeUpdate();
			IdTable id = new IdTable();
			id.setMyId(1);
			getSession().save(id);
		} else {
			IdTable id = ids.get(0);
			id.setMyId(1);
			getSession().update(id);
		}

	}

	@Override
	public boolean IsYearExisted() {
		int myYear = new GregorianCalendar().get(Calendar.YEAR);
		List<IdTable> ids = getSession()
				.createQuery("select i from IdTable i where i.myYear = ?")
				.setParameter(0, myYear).list();
		if (ids.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

}
