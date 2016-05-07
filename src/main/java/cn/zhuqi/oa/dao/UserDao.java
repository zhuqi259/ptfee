package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.model.UsersRoles;
import cn.zhuqi.oa.vo.PagerVO;

public interface UserDao extends BaseDao {
	/**
	 * 按照用户名username查找用户
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);

	/**
	 * 根据用户ID查找用户的角色列表(多对多)
	 * 
	 * @param userId
	 * @return
	 */
	public List<Role> findRoles(int userId);

	/**
	 * 根据角色ID查找用户列表(多对多)
	 * 
	 * @param roleId
	 * @return
	 */
	public List<User> findUsers(int roleId);

	/**
	 * 根据userId，roleId查找UsersRoles
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public UsersRoles findUsersRoles(int userId, int roleId);

	/**
	 * 根据人员姓名，查询：人员ID、人员姓名、岗位/部门名称、用户帐号
	 * 本查询主要用于“用户管理主界面”，在这个界面上，需要显示出所有的人员及其帐号，对于 未分配帐号的人员，可以分配帐号
	 * 
	 * @param personName
	 * @return 返回的列表元素是数组：人员ID、人员姓名、岗位/部门名称、用户帐号
	 */
	public PagerVO findPersonUsers(String personName);

	/**
	 * 根据personName查找用户列表
	 * 
	 * @param personName
	 * @return
	 */
	public List findPersonsWithUser(String personName);

	/**
	 * 根据用户ID查找该用户的角色ID列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> findRoleIdsOfUser(int userId);

	/**
	 * 更新用户，同时更新用户的角色列表
	 * 
	 * @param user
	 * @param roleIds
	 */
	public void update(User user, int[] roleIds);

	/**
	 * 在添加用户信息的同时，建立用户与角色之间的关联
	 * 
	 * @param user
	 * @param roleIds
	 */
	public void add(User user, int[] roleIds);

}
