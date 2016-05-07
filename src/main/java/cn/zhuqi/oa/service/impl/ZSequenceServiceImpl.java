package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.ZSequenceDao;
import cn.zhuqi.oa.model.FieldMap;
import cn.zhuqi.oa.model.ZSequence;
import cn.zhuqi.oa.service.ZSequenceService;

@Service("zSequenceService")
public class ZSequenceServiceImpl implements ZSequenceService {

	@Resource
	private ZSequenceDao zSequenceDao;

	@Override
	public void addZSequence(ZSequence zSequence) {
		zSequenceDao.save(zSequence);
	}

	@Override
	public ZSequence findZSequenceById(int zSequenceId) {
		return zSequenceDao.findById(ZSequence.class, zSequenceId);
	}

	@Override
	public void delZSequence(int zSequenceId) {
		zSequenceDao.del(findZSequenceById(zSequenceId));
	}

	@Override
	public void updateZSequence(ZSequence zSequence) {
		zSequenceDao.update(zSequence);
	}

	@Override
	public List<FieldMap> getSequenceFields() {
		return zSequenceDao.getSequenceFields();
	}

	@Override
	public List<ZSequence> getAllSequences() {
		return zSequenceDao.getAllSequences();
	}

	@Override
	public List<FieldMap> getCustomSequenceFields() {
		return zSequenceDao.getCustomSequenceFields();
	}

	@Override
	public List<ZSequence> getCustomSequences() {
		return zSequenceDao.getCustomSequences();
	}

}
