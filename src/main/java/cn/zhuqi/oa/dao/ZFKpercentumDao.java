package cn.zhuqi.oa.dao;

import cn.zhuqi.oa.model.ZFKpercentum;

public interface ZFKpercentumDao extends BaseDao {
	
	public ZFKpercentum findZFKpercentumByActivity(Integer activityId);

}
