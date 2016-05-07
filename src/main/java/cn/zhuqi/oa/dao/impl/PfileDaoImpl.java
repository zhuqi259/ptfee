package cn.zhuqi.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.PfileDao;
import cn.zhuqi.oa.model.Pfile;

@Repository("pfileDao")
public class PfileDaoImpl extends BaseDaoImpl implements PfileDao {

	@Override
	public Pfile getPFileByZFile(int projectId, int zfileId) {
		String hql = "select p from Pfile p where p.project.id = ? and p.zfile.id = ?";
		return (Pfile) getSession().createQuery(hql).setParameter(0, projectId)
				.setParameter(1, zfileId).uniqueResult();
	}

	@Override
	public List<Pfile> getPFileByZFile(int zfileId) {
		String hql = "select p from Pfile p where p.zfile.id = ?";
		return getSession().createQuery(hql).setParameter(0, zfileId).list();
	}

}
