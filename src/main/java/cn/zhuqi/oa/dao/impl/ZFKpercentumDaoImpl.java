package cn.zhuqi.oa.dao.impl;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.ZFKpercentumDao;
import cn.zhuqi.oa.model.ZFKpercentum;

@Repository("zFKpercentumDao")
public class ZFKpercentumDaoImpl extends BaseDaoImpl implements ZFKpercentumDao {

	@Override
	public ZFKpercentum findZFKpercentumByActivity(Integer activityId) {
		return (ZFKpercentum) getSession()
				.createQuery(
						"select zf from ZFKpercentum zf where zf.activity.id = ? ")
				.setParameter(0, activityId).uniqueResult();
	}

}
