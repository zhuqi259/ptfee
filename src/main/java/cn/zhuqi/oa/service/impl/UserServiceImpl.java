package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.ProjectDao;
import cn.zhuqi.oa.dao.RoleDao;
import cn.zhuqi.oa.dao.UserDao;
import cn.zhuqi.oa.model.ApproveInfo;
import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.model.UsersRoles;
import cn.zhuqi.oa.service.UserService;
import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.system.MD5Util;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Resource
	private RoleDao roleDao;
	
	@Resource
	private ProjectDao projectDao;

	@Override
	public void addUser(User user) {
		userDao.save(user);
	}

	@Override
	public void addUser(User user, int[] roleIds) {
		userDao.add(user, roleIds);
	}

	@Override
	public void delUser(int userId) {

		User user = findUserById(userId);
		// 删除approveInfo
		List<ApproveInfo> infos = projectDao.getAllApproveInfosByUserId(userId);
		for (ApproveInfo info : infos) {
			projectDao.del(info);
		}
		userDao.del(user);
		
	}

	@Override
	public User login(String username, String password) {
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new RuntimeException("用户【" + username + "】不存在");
		}

		if (user.getPassword() == null || password == null
				|| !user.getPassword().equals(MD5Util.Str2Md5_32(password))) {
			throw new RuntimeException("密码错误！");
		}

		return user;
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

	@Override
	public void updateUser(User user, int[] roleIds) {
		userDao.update(user, roleIds);
	}

	@Override
	public User findUserByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findUserById(int userId) {
		return userDao.findById(User.class, userId);
	}

	@Override
	public void addRoleToUser(int userId, int roleId) {

		UsersRoles ur = new UsersRoles();
		User user = findUserById(userId);
		Role role = roleDao.findById(Role.class, roleId);
		ur.setUser(user);
		ur.setRole(role);

		userDao.save(ur);
	}

	@Override
	public void delRoleFromUser(int userId, int roleId) {
		UsersRoles ur = userDao.findUsersRoles(userId, roleId);
		if (ur != null) {
			userDao.del(ur);
		}
	}

	@Override
	public PagerVO findPersonUsers(String personName) {
		return userDao.findPersonUsers(personName);
	}

	@Override
	public List findPersonsWithUser(String personName) {
		return userDao.findPersonsWithUser(personName);
	}

	@Override
	public List<Integer> findRoleIdsOfUser(int userId) {
		return userDao.findRoleIdsOfUser(userId);
	}

	@Override
	public List<User> findAllUsers() {
		return userDao.findAll(User.class);
	}

	@Override
	public User getUserById(int userId) {
		return userDao.getById(User.class, userId);
	}

}
