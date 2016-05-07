package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.model.ACL;
import cn.zhuqi.oa.model.Principal;
import cn.zhuqi.oa.model.SysResource;

public interface AclDao extends BaseDao {

	/**
	 * 删除某个主体下面的某种资源类型的所有授权
	 * 
	 * @param principalType
	 * @param principalId
	 * @param resourceType
	 */
	public void delAcls(String principalType, int principalId,
						String resourceType);

	/**
	 * 查询ACL对象
	 * 
	 * @param principalType
	 * @param principalId
	 * @param resourceType
	 * @param resourceId
	 * @return
	 */
	public ACL findACL(String principalType, int principalId,
					   String resourceType, int resourceId);

	/**
	 * 查找直接分配给某个主体的某种资源类型的所有授权记录
	 * 
	 * @param principalType
	 * @param principalId
	 * @param resourceType
	 * @return
	 */
	public List<ACL> findAcls(String principalType, int principalId,
							  String resourceType);

	/**
	 * 查询某种类型的所有资源对象
	 * 
	 * @param resourceType
	 * @return
	 */
	public List<SysResource> findAllSysResources(String resourceType);

	/**
	 * 根据主体类型和主体ID查询主体对象
	 * 
	 * @param principalType
	 * @param principalId
	 * @return
	 */
	public Principal findPrincipal(String principalType, int principalId);

	/**
	 * 根据资源的唯一标识查询资源对象
	 * 
	 * @param resourceSn
	 * @return
	 */
	public SysResource findSysResourceBySn(String resourceSn);
}
