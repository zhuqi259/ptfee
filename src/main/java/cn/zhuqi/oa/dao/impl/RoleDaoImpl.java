package cn.zhuqi.oa.dao.impl;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.RoleDao;
import cn.zhuqi.oa.model.Role;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {

	@Override
	public Role findByRolename(String rolename) {
		return (Role) getSession()
				.createQuery("select r from Role r where r.name = ?")
				.setParameter(0, rolename).uniqueResult();
	}


}
