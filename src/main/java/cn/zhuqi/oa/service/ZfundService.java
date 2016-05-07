package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.Zfund;

public interface ZfundService {

	public void addZfund(Zfund zfund);
	
	public Zfund findZfundById(Integer zfundId);
	
	public void delZfund(Integer zfundId);
	
	public void updateZfund(Zfund zfund);
	
	public List<Zfund> findAllZfunds();
}
