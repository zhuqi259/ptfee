package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.model.Fund;

public interface FundDao extends BaseDao {
	public List<Fund> getAllFunds(int projectId, int activityId);

	public Fund getFund(int projectId, Integer zfundId);

	public List<Fund> getAllProjectFund(int projectId);
}
