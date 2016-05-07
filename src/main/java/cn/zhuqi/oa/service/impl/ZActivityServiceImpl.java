package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.ZActivityDao;
import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.model.ZPayment;
import cn.zhuqi.oa.model.Zfile;
import cn.zhuqi.oa.model.Zfund;
import cn.zhuqi.oa.service.ZActivityService;
import cn.zhuqi.oa.vo.PagerVO;

@Service("zactivityService")
public class ZActivityServiceImpl implements ZActivityService {

	@Resource
	private ZActivityDao activityDao;

	@Override
	public void initActivity() {
		activityDao.initActivity();
	}

	@Override
	public List findAllActivity(String activityName) {
		return activityDao.findAllActivity(activityName);
	}

	@Override
	public PagerVO findAllFiles(int activityId) {
		return activityDao.findAllFiles(activityId);
	}

	@Override
	public List<Zfile> findAllFileList(int activityId) {
		return activityDao.findAllFileList(activityId);
	}

	@Override
	public ZActivity findActivityById(int activityId) {
		return activityDao.findById(ZActivity.class, activityId);
	}

	@Override
	public ZActivity findActivityByName(String activityName) {
		return activityDao.findActivityByName(activityName);
	}

	@Override
	public void update(ZActivity activity) {
		activityDao.update(activity);
	}

	@Override
	public PagerVO findAllZActivity(String activityName) {
		return activityDao.findAllZActivity(activityName);
	}

	@Override
	public List<ZActivity> getAllZActivity() {
		return activityDao.findAll(ZActivity.class);
	}

	@Override
	public PagerVO findAllPayments(int activityId) {
		return activityDao.findAllPayments(activityId);
	}

	@Override
	public List<ZPayment> findAllPaymentList(int activityId) {
		return activityDao.findAllPaymentList(activityId);
	}

	@Override
	public PagerVO findAllFunds(Integer activityId) {
		return activityDao.findAllFunds(activityId);
	}

	@Override
	public List<Zfund> findAllFundList(Integer activityId) {
		return activityDao.findAllFundList(activityId);
	}

	@Override
	public List<ZActivity> findAllActivityByOrder() {
		return activityDao.findAllActivityByOrder();
	}
}
