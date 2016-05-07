package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.Zfile;

public interface ZfileService {

	/**
	 * 添加文件
	 * 
	 * @param zfile
	 */
	public void addZfile(Zfile zfile);

	/**
	 * 通过ID查找Zfile对象
	 * 
	 * @param zfileId
	 * @return
	 */
	public Zfile findZfileById(int zfileId);

	/**
	 * 删除Zfile对象
	 * 
	 * @param zfileId
	 */
	public void delZfile(int zfileId);

	/**
	 * 更新Zfile对象
	 * 
	 * @param zfile
	 */
	public void updateZfile(Zfile zfile);

	/**
	 * 不分条件，查询出所有的Zfile对象
	 * 
	 * @return
	 */
	public List<Zfile> findAllZfiles();
}
