package cn.zhuqi.oa.service;

import cn.zhuqi.oa.model.IdTable;

public interface IdTableService {

	/**
	 * ID生成器
	 * 
	 * @return
	 */
	public String generateId();

	/**
	 * 初始ID生成器
	 */
	public void initId();
	
	public IdTable zgetSingle();
	
	public void updateIdTable(IdTable idTable);
}
