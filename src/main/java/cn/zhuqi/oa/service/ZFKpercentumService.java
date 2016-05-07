package cn.zhuqi.oa.service;

import cn.zhuqi.oa.model.ZFKpercentum;

public interface ZFKpercentumService {

	public void addZpercentum(ZFKpercentum zpercentum);

	public ZFKpercentum findZpercentumById(Integer zpercentumId);

	public void delZpercentum(Integer zpercentumId);

	public void updateZpercentum(ZFKpercentum zpercentum);
	
	public ZFKpercentum findZFKpercentumByActivity(Integer activityId);
}
