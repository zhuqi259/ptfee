package cn.zhuqi.oa.dao;

import cn.zhuqi.oa.model.Role;


public interface RoleDao extends BaseDao {

	public Role findByRolename(String rolename);
}
