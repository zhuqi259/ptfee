package cn.zhuqi.oa.service;

import cn.zhuqi.oa.model.Pfile;

public interface PfileService {

	/**
	 * 添加文件
	 * 
	 * @param pfile
	 */
	public void addPfile(Pfile pfile);

	/**
	 * 通过ID查找Pfile对象
	 * 
	 * @param pfileId
	 * @return
	 */
	public Pfile findPfileById(int pfileId);

	/**
	 * 删除Pfile对象
	 * 
	 * @param pfileId
	 */
	public void delPfile(int pfileId);

	/**
	 * 更新Pfile对象
	 * 
	 * @param pfile
	 */
	public void updatePfile(Pfile pfile);

	public Pfile getPFileByZFile(int projectId, int zfileId);

}
