package cn.zhuqi.oa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.IdTableDao;
import cn.zhuqi.oa.model.IdTable;
import cn.zhuqi.oa.service.IdTableService;

@Service("idTableService")
public class IdTableServiceImpl implements IdTableService {

	@Resource
	private IdTableDao idTableDao;

	@Override
	public String generateId() {
		IdTable idtable = idTableDao.zgetSingle();
		int myId = idtable.getMyId();
		idtable.setMyId(myId + 1);
		idTableDao.update(idtable);
		return String.valueOf(myId);
	}

	@Override
	public void initId() {
		idTableDao.initId();
	}

	@Override
	public IdTable zgetSingle() {
		return idTableDao.zgetSingle();
	}

	@Override
	public void updateIdTable(IdTable idTable) {
		idTableDao.update(idTable);
	}

}
