package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.Database;
import cn.zhuqi.oa.model.Zfile;

public interface DatabaseService {

	public void addDatabase(Database database);

	public List<Database> getAllDatabaseList();
	
	public List<Zfile> findAllFileList(int databaseId);

	public Database findDatabaseById(int databaseId);
	
}
