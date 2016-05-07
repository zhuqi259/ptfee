package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.DatabaseDao;
import cn.zhuqi.oa.model.Database;
import cn.zhuqi.oa.model.Zfile;
import cn.zhuqi.oa.service.DatabaseService;

@Service("databaseService")
public class DatabaseServiceImpl implements DatabaseService {

	@Resource
	private DatabaseDao databaseDao;

	@Override
	public void addDatabase(Database database) {
		databaseDao.save(database);
	}

	@Override
	public List<Database> getAllDatabaseList() {
		return databaseDao.findAll(Database.class);
	}

	@Override
	public List<Zfile> findAllFileList(int databaseId) {
		return databaseDao.findAllFileList(databaseId);
	}

	@Override
	public Database findDatabaseById(int databaseId) {
		return databaseDao.findById(Database.class, databaseId);
	}

}
