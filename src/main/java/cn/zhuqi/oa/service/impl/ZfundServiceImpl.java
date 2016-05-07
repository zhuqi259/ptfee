package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.ZfundDao;
import cn.zhuqi.oa.model.Zfund;
import cn.zhuqi.oa.service.ZfundService;

@Service("zfundService")
public class ZfundServiceImpl implements ZfundService {

	@Resource
	private ZfundDao zfundDao;

	@Override
	public void addZfund(Zfund zfund) {
		zfundDao.save(zfund);
	}

	@Override
	public Zfund findZfundById(Integer zfundId) {
		return zfundDao.findById(Zfund.class, zfundId);
	}

	@Override
	public void delZfund(Integer zfundId) {
		zfundDao.del(findZfundById(zfundId));
	}

	@Override
	public void updateZfund(Zfund zfund) {
		zfundDao.update(zfund);
	}

	@Override
	public List<Zfund> findAllZfunds() {
		return zfundDao.findAll(Zfund.class);
	}

}
