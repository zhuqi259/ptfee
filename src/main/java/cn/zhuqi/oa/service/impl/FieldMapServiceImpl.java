package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.FieldMapDao;
import cn.zhuqi.oa.dao.ZSequenceDao;
import cn.zhuqi.oa.model.FieldMap;
import cn.zhuqi.oa.model.ZSequence;
import cn.zhuqi.oa.service.FieldMapService;

@Service("fieldMapService")
public class FieldMapServiceImpl implements FieldMapService {

	@Resource
	private FieldMapDao fieldMapDao;

	@Resource
	private ZSequenceDao zSequenceDao;

	@Override
	public void addFieldMap(FieldMap fieldMap) {
		fieldMapDao.save(fieldMap);
	}

	@Override
	public FieldMap findFieldMapById(int fieldMapId) {
		return fieldMapDao.findById(FieldMap.class, fieldMapId);
	}

	@Override
	public void delFieldMap(int fieldMapId) {
		List<ZSequence> zSequences = zSequenceDao
				.findAllZSequencesByFieldMap(fieldMapId);
		for (ZSequence zSequence : zSequences) {
			zSequenceDao.del(zSequence);
		}
		fieldMapDao.del(findFieldMapById(fieldMapId));
	}

	@Override
	public void updateFieldMap(FieldMap fieldMap) {
		fieldMapDao.update(fieldMap);
	}

	@Override
	public List<FieldMap> findAllFieldMaps() {
		return fieldMapDao.findAll(FieldMap.class);
	}

}
