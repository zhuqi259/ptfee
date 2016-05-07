package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.model.Zfile;

public interface DatabaseDao extends BaseDao {

	public List<Zfile> findAllFileList(int databaseId);
}
