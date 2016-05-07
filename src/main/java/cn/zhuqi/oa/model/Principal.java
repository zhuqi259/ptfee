package cn.zhuqi.oa.model;

import java.util.List;

public interface Principal {
	/**
	 * 获得实体的ID
	 * 
	 * @return
	 */
	public int getPrincipalId();

	/**
	 * 获得实体的类型
	 * 
	 * @return
	 */
	public String getPrincipalType();

	/**
	 * 获得实体的父亲
	 * 
	 * @return
	 */
	public List<Principal> getParentPrincipal();
}
