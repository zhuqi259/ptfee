package cn.zhuqi.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.PaymentDao;
import cn.zhuqi.oa.model.Payment;

@Repository("paymentDao")
public class PaymentDaoImpl extends BaseDaoImpl implements PaymentDao {

	@Override
	public List<Payment> getAllPayment(int projectId, int activityId) {
		String hql = "select p from Payment p where p.project.id = ? and p.zpayment.activity.id = ? ";
		return getSession().createQuery(hql).setParameter(0, projectId)
				.setParameter(1, activityId).list();
	}

	@Override
	public Payment getPayment(int projectId, int zpaymentId) {
		String hql = "select p from Payment p where p.project.id = ? and p.zpayment.id = ? ";
		return (Payment) getSession().createQuery(hql)
				.setParameter(0, projectId).setParameter(1, zpaymentId)
				.uniqueResult();
	}

	@Override
	public List<Payment> getAllProjectPayment(int projectId) {
		String hql = "select p from Payment p where p.project.id = ? ";
		return getSession().createQuery(hql).setParameter(0, projectId).list();
	}

}
