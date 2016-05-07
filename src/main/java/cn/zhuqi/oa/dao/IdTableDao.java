package cn.zhuqi.oa.dao;

import cn.zhuqi.oa.model.IdTable;

public interface IdTableDao extends BaseDao {

	public IdTable zgetSingle();
	
	public void initId();
	
	public boolean IsYearExisted();
}
