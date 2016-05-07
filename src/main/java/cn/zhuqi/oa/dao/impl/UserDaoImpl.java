package cn.zhuqi.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.UserDao;
import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.model.UsersRoles;
import cn.zhuqi.oa.vo.PagerVO;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public User findByUsername(String username) {
		return (User) getSession()
				.createQuery("select u from User u where u.username = ?")
				.setParameter(0, username).uniqueResult();
	}

	@Override
	public List<Role> findRoles(int userId) {
		String hql = "select r from UsersRoles ur join ur.role r join ur.user u "
				+ "where u.id = ?";
		return getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<User> findUsers(int roleId) {
		String hql = "select u from UsersRoles ur join ur.role r join ur.user u "
				+ "where r.id = ?";
		return getSession().createQuery(hql).setParameter(0, roleId).list();
	}

	@Override
	public UsersRoles findUsersRoles(int userId, int roleId) {
		String hql = "select ur from UsersRoles ur join ur.role r join ur.user u "
				+ "where u.id = ? and r.id = ? ";
		return (UsersRoles) getSession().createQuery(hql)
				.setParameter(0, userId).setParameter(1, roleId).uniqueResult();
	}

	@Override
	public PagerVO findPersonUsers(String personName) {
		// System.out.println("TODO Test=====> URI " + personName);
		String hql = "select p.id,p.name,pt.name,u.username from Person p left join p.parent pt left join p.user u where p.name like ?";
		return findPaginated(hql, "%" + personName + "%");
	}

	@Override
	public List findPersonsWithUser(String personName) {
		// System.out.println("TODO Test=====> URI " + personName);
		String hql = "select p.id,p.name from Person p join p.user u where p.name like ?";
		return getSession().createQuery(hql)
				.setParameter(0, "%" + personName + "%").list();
	}

	@Override
	public List<Integer> findRoleIdsOfUser(int userId) {
		String hql = "select r.id from UsersRoles ur join ur.role r join ur.user u "
				+ "where u.id = ?";
		return getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public void update(User user, int[] roleIds) {
		// a different object with the same identifier value was already
		// associated with the session。
		// 在hibernate中同一个session里面有了两个相同标识但是是不同实体。
		// PS:使用merge代替update
		// 出现的原因是当前用户更新个人信息的时候,若是管理员更新他人信息就不会出错.
		getSession().merge(user);
		// 删除旧的关联
		String hql = "select ur from UsersRoles ur join ur.user u where u.id = ?";
		List<UsersRoles> urs = getSession().createQuery(hql)
				.setParameter(0, user.getId()).list();

		for (UsersRoles ur : urs) {
			getSession().delete(ur);
		}

		// 建立新的关联
		updateUsersRoles(user, roleIds);
	}

	@Override
	public void add(User user, int[] roleIds) {
		// 对于重复添加用户操作的判断应该放在Service层.
		getSession().save(user);
		updateUsersRoles(user, roleIds);
	}

	/**
	 * 建立新的关联
	 * 
	 * @param user
	 * @param roleIds
	 */
	private void updateUsersRoles(User user, int[] roleIds) {
		if (roleIds != null) {
			for (int roleId : roleIds) {
				UsersRoles ur = new UsersRoles();
				ur.setUser(user);
				ur.setRole((Role) getSession().load(Role.class, roleId));
				getSession().save(ur);
			}
		}
	}

}
