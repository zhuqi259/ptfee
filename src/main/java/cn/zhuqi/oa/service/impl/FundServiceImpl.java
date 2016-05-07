package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.FundDao;
import cn.zhuqi.oa.model.Fund;
import cn.zhuqi.oa.service.FundService;

@Service("fundService")
public class FundServiceImpl implements FundService {

	@Resource
	private FundDao fundDao;

	@Override
	public void addFund(Fund fund) {
		fundDao.save(fund);
	}

	@Override
	public void updateFund(Fund fund) {
		fundDao.update(fund);
	}

	@Override
	public Fund findFundById(Integer fundId) {
		return fundDao.findById(Fund.class, fundId);
	}

	@Override
	public void delFund(Integer fundId) {
		fundDao.del(findFundById(fundId));
	}

	@Override
	public List<Fund> getAllFunds(int projectId, int activityId) {
		return fundDao.getAllFunds(projectId, activityId);
	}

	@Override
	public Fund getFund(int projectId, Integer zfundId) {
		return fundDao.getFund(projectId, zfundId);
	}

	@Override
	public List<Fund> getAllProjectFund(int projectId) {
		return fundDao.getAllProjectFund(projectId);
	}

}
