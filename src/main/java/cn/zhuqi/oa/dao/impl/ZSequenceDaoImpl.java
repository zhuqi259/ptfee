package cn.zhuqi.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.ZSequenceDao;
import cn.zhuqi.oa.model.FieldMap;
import cn.zhuqi.oa.model.ZSequence;

@Repository("zSequenceDao")
public class ZSequenceDaoImpl extends BaseDaoImpl implements ZSequenceDao {

	@Override
	public List<ZSequence> getAllSequences() {
		String hql = "select s from ZSequence s order by s.id asc";
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<FieldMap> getSequenceFields() {
		String hql = "select s.fieldMap from ZSequence s order by s.id asc";
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<ZSequence> findAllZSequencesByFieldMap(int fieldMapId) {
		String hql = "select s from ZSequence s where s.fieldMap = ? ";
		return getSession().createQuery(hql).setParameter(0, fieldMapId).list();
	}

	@Override
	public List<FieldMap> getCustomSequenceFields() {
		String hql = "select s.fieldMap from ZSequence s where s.used = 'Y' order by s.index asc";
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<ZSequence> getCustomSequences() {
		String hql = "select s from ZSequence s where s.used = 'Y' order by s.index asc";
		return getSession().createQuery(hql).list();
	}

}
