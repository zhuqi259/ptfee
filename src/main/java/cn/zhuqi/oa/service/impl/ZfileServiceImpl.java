package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.PfileDao;
import cn.zhuqi.oa.dao.ZfileDao;
import cn.zhuqi.oa.model.Pfile;
import cn.zhuqi.oa.model.Zfile;
import cn.zhuqi.oa.service.ZfileService;

@Service("zfileService")
public class ZfileServiceImpl implements ZfileService {

	@Resource
	private ZfileDao ZfileDao;

	@Resource
	private PfileDao pfileDao;

	@Override
	public void addZfile(Zfile zfile) {
		ZfileDao.save(zfile);
	}

	@Override
	public Zfile findZfileById(int zfileId) {
		return ZfileDao.findById(Zfile.class, zfileId);
	}

	@Override
	public void delZfile(int zfileId) {
		List<Pfile> pfiles = pfileDao.getPFileByZFile(zfileId);
		for (Pfile pfile : pfiles) {
			// TODO ---------------具体删除文件
			pfileDao.del(pfile);
		}
		ZfileDao.del(findZfileById(zfileId));
	}

	@Override
	public void updateZfile(Zfile zfile) {
		ZfileDao.update(zfile);
	}

	@Override
	public List<Zfile> findAllZfiles() {
		return ZfileDao.findAll(Zfile.class);
	}

}
