package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.RoleDao;
import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.service.RoleService;
import cn.zhuqi.oa.vo.PagerVO;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleDao roleDao;
	
	@Override
	public void addRole(Role role) {
		roleDao.save(role);
	}

	@Override
	public void delRole(int roleId) {
		roleDao.del(findRoleById(roleId));
	}

	@Override
	public PagerVO findAllRoles(String query) {
		if(query == null || query.trim().equals("")){
			return roleDao.findPaginated("select r.id,r.name from Role r");
		}else{
			return roleDao.findPaginated("select r.id,r.name from Role r where r.name like ?","%"+query+"%");
		}
	}

	@Override
	public List<Role> findAllRoles() {
		return roleDao.findAll(Role.class);
	}

	@Override
	public Role findRoleById(int roleId) {
		return roleDao.findById(Role.class, roleId);
	}


	@Override
	public void updateRole(Role role) {
		roleDao.update(role);
	}
}
