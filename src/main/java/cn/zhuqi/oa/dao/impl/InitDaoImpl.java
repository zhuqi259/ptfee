package cn.zhuqi.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.InitDao;
import cn.zhuqi.oa.model.ACL;
import cn.zhuqi.oa.model.Person;
import cn.zhuqi.oa.model.Principal;
import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.model.SysResource;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.model.UsersRoles;
import cn.zhuqi.system.MD5Util;

@Repository("initDao")
public class InitDaoImpl extends BaseDaoImpl implements InitDao {

	@Override
	public void initAdmin() {

		getSession().flush();
		getSession().clear();

		// 添加超级管理员（人员及登录帐号）
		Person adminPerson = new Person();
		adminPerson.setName("超级管理员");
		getSession().save(adminPerson);
		User adminUser = new User();
		adminUser.setUsername("admin");
		// adminUser.setPassword("admin");
		adminUser.setPassword(MD5Util.Str2Md5_32("admin"));
		adminUser.setPerson(adminPerson);
		getSession().save(adminUser);

		// 创建系统管理员角色
		Role adminRole = new Role();
		adminRole.setName("系统管理员");
		getSession().save(adminRole);

		// 创建普通员工角色
		Role commonRole = new Role();
		commonRole.setName("普通员工");
		getSession().save(commonRole);

		// 让管理员用户拥有系统管理员角色
		UsersRoles ur1 = new UsersRoles();
		ur1.setRole(adminRole);
		ur1.setUser(adminUser);
		getSession().save(ur1);

		// 让管理员用户拥有普通员工角色
		UsersRoles ur2 = new UsersRoles();
		ur2.setRole(commonRole);
		ur2.setUser(adminUser);
		getSession().save(ur2);

		// 查询出：系统管理相关菜单、安全相关操作、组织机构相关操作
		String hql = "select r from cn.zhuqi.oa.model.SysResource r "
				+ "where r.sn = 'system' or r.sn='security' or r.sn = 'party'";
		List<SysResource> res = getSession().createQuery(hql).list();
		for (SysResource r : res) {
			saveAllPermitAcl(adminRole, r); // 将这些权限全部赋予系统管理员角色
			saveAllPermitAcl(adminUser, r); // 将这些权限全部赋予管理员用户
		}

		// 个人办公与工作流相关的菜单项资源
		// TODO modified by zhuqi
		hql = "select r from cn.zhuqi.oa.model.SysResource r "
				+ "where r.sn = 'personal' or r.sn='workflow'";
		res = getSession().createQuery(hql).list();
		for (SysResource r : res) {
			saveAllPermitAcl(commonRole, r); // 将这些权限全部赋予普通员工角色
		}

	}

	@Override
	public void addUser() {

		getSession().flush();
		getSession().clear();

		Person personA = new Person();
		personA.setName("10000");

		getSession().save(personA);

		User userA = new User();
		userA.setUsername("login");
		userA.setPassword(null);
		userA.setPerson(personA);
		getSession().save(userA);

		Person personB = new Person();
		personB.setName("10001");

		getSession().save(personB);

		User userB = new User();
		userB.setUsername("system");
		userB.setPassword(null);
		userB.setPerson(personB);
		getSession().save(userB);

		String rolename = "普通员工";
		Role commonRole = (Role) getSession()
				.createQuery("select r from Role r where r.name = ?")
				.setParameter(0, rolename).uniqueResult();

		UsersRoles urA = new UsersRoles();
		urA.setRole(commonRole);
		urA.setUser(userA);
		getSession().save(urA);

		UsersRoles urB = new UsersRoles();
		urB.setRole(commonRole);
		urB.setUser(userB);
		getSession().save(urB);

	}

	/**
	 * 将某个资源对象（包括其所有子对象）的所有操作的允许权限授予principal对象
	 * 
	 * @param principal
	 * @param resource
	 */
	private void saveAllPermitAcl(Principal principal, SysResource resource) {
		ACL acl = new ACL();
		acl.setPrincipalType(principal.getPrincipalType());
		acl.setPrincipalId(principal.getPrincipalId());
		acl.setResourceType(resource.getResourceType());
		acl.setResourceId(resource.getResourceId());
		acl.setAclState(-1); // 全部操作都允许
		acl.setAclTriState(0); // 全部操作都不继承
		getSession().save(acl);

		List<SysResource> children = resource.getChildrenResource();
		if (children != null) {
			for (SysResource c : children) {
				saveAllPermitAcl(principal, c);
			}
		}
	}

}
