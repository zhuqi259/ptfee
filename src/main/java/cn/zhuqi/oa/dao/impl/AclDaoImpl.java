package cn.zhuqi.oa.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.AclDao;
import cn.zhuqi.oa.model.ACL;
import cn.zhuqi.oa.model.Principal;
import cn.zhuqi.oa.model.SysResource;

@Repository("aclDao")
public class AclDaoImpl extends BaseDaoImpl implements AclDao {

	@Override
	public void delAcls(String principalType, int principalId,
			String resourceType) {

		// 首先找出所有授权记录
		Iterator acls = getSession()
				.createQuery(
						"select a from ACL a where a.principalType = ? "
								+ "and a.principalId = ? and a.resourceType = ?")
				.setParameter(0, principalType).setParameter(1, principalId)
				.setParameter(2, resourceType).iterate();
		while (acls.hasNext()) {
			getSession().delete(acls.next());
		}
	}

	@Override
	public ACL findACL(String principalType, int principalId,
			String resourceType, int resourceId) {

		return (ACL) getSession()
				.createQuery(
						"select a from ACL a where a.principalType = ?"
								+ " and a.principalId = ? and a.resourceType = ? and a.resourceId = ?")
				.setParameter(0, principalType).setParameter(1, principalId)
				.setParameter(2, resourceType).setParameter(3, resourceId)
				.uniqueResult();
	}

	@Override
	public List<ACL> findAcls(String principalType, int principalId,
			String resourceType) {

		return getSession()
				.createQuery(
						"select a from ACL a where a.principalType = ? "
								+ "and a.principalId = ? and a.resourceType = ?")
				.setParameter(0, principalType).setParameter(1, principalId)
				.setParameter(2, resourceType).list();
	}

	@Override
	public List<SysResource> findAllSysResources(String resourceType) {
		return getSession().createQuery("from " + resourceType).list();
	}

	@Override
	public Principal findPrincipal(String principalType, int principalId) {
		String hql = "select p from " + principalType + " p where p.id=?";
		return (Principal) getSession().createQuery(hql)
				.setParameter(0, principalId).uniqueResult();
	}

	@Override
	public SysResource findSysResourceBySn(String resourceSn) {
		String hql = "select sr from cn.zhuqi.oa.model.SysResource sr where sr.sn=?";
		return (SysResource) getSession().createQuery(hql)
				.setParameter(0, resourceSn).uniqueResult();
	}

}
