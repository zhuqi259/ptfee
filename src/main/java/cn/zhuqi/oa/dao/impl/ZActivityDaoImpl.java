package cn.zhuqi.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.ZActivityDao;
import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.model.ZPayment;
import cn.zhuqi.oa.model.Zfile;
import cn.zhuqi.oa.model.Zfund;
import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.system.EActivity;

@Repository("zactivityDao")
public class ZActivityDaoImpl extends BaseDaoImpl implements ZActivityDao {

	@Override
	public void initActivity() {
		for (EActivity eactivity : EActivity.values()) {
			ZActivity activity = new ZActivity();
			activity.setName(eactivity.getName());
			getSession().save(activity);
		}
	}

	@Override
	public List findAllActivity(String activityName) {
		String hql = "select a.id,a.name from ZActivity a where a.name like ?";
		return getSession().createQuery(hql)
				.setParameter(0, "%" + activityName + "%").list();
	}

	@Override
	public PagerVO findAllFiles(int activityId) {
		String hql = "select z.id, z.name, z.base.name from Zfile z where z.activity.id = ?";
		return findPaginated(hql, activityId);
	}

	@Override
	public List<Zfile> findAllFileList(int activityId) {
		String hql = "select z from Zfile z where z.activity.id = ? order by z.id";
		return getSession().createQuery(hql).setParameter(0, activityId).list();
	}

	@Override
	public ZActivity findActivityByName(String activityName) {
		String hql = "select a from ZActivity a where a.name = ?";
		return (ZActivity) getSession().createQuery(hql)
				.setParameter(0, activityName).uniqueResult();
	}

	@Override
	public PagerVO findAllZActivity(String activityName) {
		String hql = "select a.id,a.name from ZActivity a where a.name like ?";
		return findPaginated(hql, "%" + activityName + "%");
	}

	@Override
	public PagerVO findAllPayments(int activityId) {
		String hql = "select zp.id, zp.name from ZPayment zp where zp.activity.id = ? ";
		return findPaginated(hql, activityId);
	}

	@Override
	public List<ZPayment> findAllPaymentList(int activityId) {
		String hql = "select zp from ZPayment zp where zp.activity.id = ? order by zp.id";
		return getSession().createQuery(hql).setParameter(0, activityId).list();
	}

	@Override
	public PagerVO findAllFunds(Integer activityId) {
		String hql = "select zf.id, zf.fund_name, zf.fn_use, zf.fund_time, zf.ft_use, zf.fund_oper, zf.fo_use from Zfund zf where zf.activity.id = ?";
		return findPaginated(hql, activityId);
	}

	@Override
	public List<Zfund> findAllFundList(Integer activityId) {
		String hql = "select zf from Zfund zf where zf.activity.id = ? order by zf.id";
		return getSession().createQuery(hql).setParameter(0, activityId).list();
	}

	@Override
	public List<ZActivity> findAllActivityByOrder() {
		String hql = "select z from ZActivity z order by z.id";
		return getSession().createQuery(hql).list();
	}

}
