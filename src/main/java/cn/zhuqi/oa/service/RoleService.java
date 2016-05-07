package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.vo.PagerVO;

public interface RoleService {
	
	/**
	 * 添加角色
	 * @param role
	 */
	public void addRole(Role role);
	
	/**
	 * 根据ID查询角色对象
	 * @param roleId
	 * @return
	 */
	public Role findRoleById(int roleId);
	
	/**
	 * 删除角色对象
	 * @param roleId
	 */
	public void delRole(int roleId);
	
	/**
	 * 更新角色对象
	 * @param role
	 */
	public void updateRole(Role role);
	
	/**
	 * 根据条件查询所有的角色对象
	 * 查询条件将匹配角色的名称
	 * 
	 * 本方法主要用于角色管理的主界面
	 * @param query 查询条件
	 * @return 返回列表中的元素是数组：角色ID，角色名称
	 */
	public PagerVO findAllRoles(String query);
	
	/**
	 * 不分条件，查询出所有的角色对象
	 * @return
	 */
	public List<Role> findAllRoles();
}
