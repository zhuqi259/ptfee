package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.model.Pfile;

public interface PfileDao extends BaseDao{
	
	public Pfile getPFileByZFile(int projectId, int zfileId);

	public List<Pfile> getPFileByZFile(int zfileId);
}
