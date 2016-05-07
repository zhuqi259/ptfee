package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.vo.AuthVO;

public interface AclService {

	/**
	 * 查询某个主体对于某种类型（比如是Menu资源还是ActionResource资源）的资源的授权情况
	 * 
	 * @param principalType
	 *            主体类型，比如：Role/User/Position/Department
	 * @param principalId
	 *            主体ID
	 * @param resourceType
	 *            资源类型
	 * @return 
	 *         [资源ID，操作标识(CREATE/UPDATE/DELETE/READ或其它任何自定义的值)，允许状态(true/false)，是否继承
	 *         (true/false)]
	 */
	public List findAclList(String principalType, int principalId,
							String resourceType);

	/**
	 * 给某种主体授权
	 * 
	 * @param principalType
	 *            主体类型
	 * @param principalId
	 *            主体ID
	 * @param acls
	 *            授权列表
	 */
	public void addOrUpdatePermission(String principalType, int principalId,
									  String resourceType, List<AuthVO> acls);

	/**
	 * 查询某个用户（一般是当前登录用户）具有访问权限的菜单资源 登录以后，在显示管理菜单的时候，将调用本方法
	 * 
	 * @param userId
	 * @return
	 */
	public List findPermitMenus(int userId);

	/**
	 * 判断某个用户，是否拥有对某个资源的某个操作
	 * 
	 * @param userId
	 * @param resourceSn
	 * @param operSn
	 * @return
	 */
	public boolean hasPermission(int userId, String resourceSn, String operSn);

}
