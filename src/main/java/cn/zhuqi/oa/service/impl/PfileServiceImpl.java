package cn.zhuqi.oa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.PfileDao;
import cn.zhuqi.oa.model.Pfile;
import cn.zhuqi.oa.service.PfileService;

@Service("pfileService")
public class PfileServiceImpl implements PfileService {

	@Resource
	private PfileDao pfileDao;

	@Override
	public void addPfile(Pfile pfile) {
		pfileDao.save(pfile);
	}

	@Override
	public Pfile findPfileById(int pfileId) {
		return pfileDao.findById(Pfile.class, pfileId);
	}

	@Override
	public void delPfile(int pfileId) {
		pfileDao.del(findPfileById(pfileId));
	}

	@Override
	public void updatePfile(Pfile pfile) {
		pfileDao.update(pfile);
	}

	@Override
	public Pfile getPFileByZFile(int projectId, int zfileId) {
		return pfileDao.getPFileByZFile(projectId, zfileId);
	}

}
