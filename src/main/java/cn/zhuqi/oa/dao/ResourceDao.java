package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.model.ActionResource;

public interface ResourceDao extends BaseDao{
	public void delAllActionResources();
	public ActionResource findActionResourceBySn(String sn);
	
	/**
	 * 无条件查询所有的ActionResource对象
	 * @return
	 */
	public List<ActionResource> findAll();
	
	/**
	 * 查找所有顶级的ActionResource对象（即没有parent的ActionResource对象）
	 * @return
	 */
	public List<ActionResource> findAllTopActionResource();
	
	public void update(ActionResource ar);
	
	/**
	 * 根据全路径类名来查找对应的ActionResource对象
	 * @param className 全路径类名
	 * @return
	 */
	public ActionResource findByClassName(String className);
}
