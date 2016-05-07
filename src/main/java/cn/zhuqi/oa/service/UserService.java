package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.vo.PagerVO;

public interface UserService {

	/**
	 * 添加用户对象
	 * 
	 * @param user
	 */
	public void addUser(User user);

	/**
	 * 在添加用户信息的同时，建立用户与角色之间的关联
	 * 
	 * @param user
	 * @param roleIds
	 */
	public void addUser(User user, int[] roleIds);

	/**
	 * 删除用户对象 【注意】在删除用户对象时候，将自动把用户与角色之间的关联删除
	 * 
	 * @param userId
	 */
	public void delUser(int userId);

	/**
	 * 判断登录用户信息
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password);

	/**
	 * 更新用户对象
	 * 
	 * @param user
	 */
	public void updateUser(User user);

	/**
	 * 更新用户对象的同时，更新用户与角色之间的关联
	 * 
	 * @param user
	 * @param roleIds
	 */
	public void updateUser(User user, int[] roleIds);

	/**
	 * 根据username查询用户对象
	 * 
	 * @param username
	 * @return
	 */
	public User findUserByUsername(String username);

	/**
	 * 根据ID查询用户对象
	 * 
	 * @param userId
	 * @return
	 */
	public User findUserById(int userId);

	/**
	 * 在特定的角色和用户之间建立关联
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void addRoleToUser(int userId, int roleId);

	/**
	 * 删除特定的角色和用户之间的关联
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void delRoleFromUser(int userId, int roleId);

	/**
	 * 根据人员姓名，查询：人员ID、人员姓名、岗位/部门名称、用户帐号
	 * 本查询主要用于“用户管理主界面”，在这个界面上，需要显示出所有的人员及其帐号，对于 未分配帐号的人员，可以分配帐号
	 * 
	 * @param personName
	 * @return 返回的列表元素是数组：人员ID、人员姓名、岗位/部门名称、用户帐号
	 */
	public PagerVO findPersonUsers(String personName);

	/**
	 * 根据人员姓名，查询：人员ID、人员姓名，不分页 本查询主要用于“用户授权”界面，在这个界面上，需要显示出所有已分配了登录帐号的人员
	 * 列表。如果某个人员尚未分配登录帐号，则无需查询出来。
	 * 
	 * @param personName
	 *            人员姓名，查询条件
	 * @return 返回的列表元素是数组：人员ID、人员姓名
	 */
	public List findPersonsWithUser(String personName);

	/**
	 * 查询某个用户拥有的角色ID列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> findRoleIdsOfUser(int userId);

	public List<User> findAllUsers();

	/**
	 * 对于Hibernate
	 * get方法，Hibernate会确认一下该id对应的数据是否存在，首先在session缓存中查找，然后在二级缓存中查找，还没有就查询数据库
	 * ，数据库中没有就返回null。
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(int userId);
}
