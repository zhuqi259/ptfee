package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.Fund;

public interface FundService {
	public void addFund(Fund fund);

	public void updateFund(Fund fund);

	public Fund findFundById(Integer fundId);

	public void delFund(Integer fundId);

	public List<Fund> getAllFunds(int projectId, int activityId);

	public Fund getFund(int projectId, Integer zfundId);

	public List<Fund> getAllProjectFund(int projectId);
}
