package cn.zhuqi.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.FundDao;
import cn.zhuqi.oa.model.Fund;

@Repository("fundDao")
public class FundDaoImpl extends BaseDaoImpl implements FundDao {

	@Override
	public List<Fund> getAllFunds(int projectId, int activityId) {
		String hql = "select f from Fund f where f.project.id = ? and f.zfund.activity.id = ? ";
		return getSession().createQuery(hql).setParameter(0, projectId)
				.setParameter(1, activityId).list();
	}

	@Override
	public Fund getFund(int projectId, Integer zfundId) {
		String hql = "select f from Fund f where f.project.id = ? and f.zfund.id = ? ";
		return (Fund) getSession().createQuery(hql).setParameter(0, projectId)
				.setParameter(1, zfundId).uniqueResult();
	}

	@Override
	public List<Fund> getAllProjectFund(int projectId) {
		String hql = "select f from Fund f where f.project.id = ? ";
		return getSession().createQuery(hql).setParameter(0, projectId).list();
	}

}
