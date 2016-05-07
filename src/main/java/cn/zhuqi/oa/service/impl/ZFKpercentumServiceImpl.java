package cn.zhuqi.oa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.ZFKpercentumDao;
import cn.zhuqi.oa.model.ZFKpercentum;
import cn.zhuqi.oa.service.ZFKpercentumService;

@Service("zFKpercentumService")
public class ZFKpercentumServiceImpl implements ZFKpercentumService {

	@Resource
	private ZFKpercentumDao zfKpercentumDao;

	@Override
	public void addZpercentum(ZFKpercentum zpercentum) {
		zfKpercentumDao.save(zpercentum);
	}

	@Override
	public ZFKpercentum findZpercentumById(Integer zpercentumId) {
		return zfKpercentumDao.findById(ZFKpercentum.class, zpercentumId);
	}

	@Override
	public void delZpercentum(Integer zpercentumId) {
		zfKpercentumDao.del(findZpercentumById(zpercentumId));
	}

	@Override
	public void updateZpercentum(ZFKpercentum zpercentum) {
		zfKpercentumDao.update(zpercentum);
	}

	@Override
	public ZFKpercentum findZFKpercentumByActivity(Integer activityId) {
		return zfKpercentumDao.findZFKpercentumByActivity(activityId);
	}

}
