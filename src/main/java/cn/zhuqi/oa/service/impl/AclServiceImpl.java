package cn.zhuqi.oa.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.AclDao;
import cn.zhuqi.oa.dao.MenuDao;
import cn.zhuqi.oa.dao.UserDao;
import cn.zhuqi.oa.model.ACL;
import cn.zhuqi.oa.model.Menu;
import cn.zhuqi.oa.model.Principal;
import cn.zhuqi.oa.model.SysResource;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.service.AclService;
import cn.zhuqi.oa.vo.AuthVO;

@Service("aclService")
public class AclServiceImpl implements AclService {

	@Resource
	private AclDao aclDao;

	@Resource
	private MenuDao menuDao;

	@Resource
	private UserDao userDao;

	@Override
	public List findAclList(String principalType, int principalId,
			String resourceType) {
		List<AuthVO> vos = new ArrayList<AuthVO>();

		// 查询出主体对象
		Principal principal = aclDao.findPrincipal(principalType, principalId);

		// 查询出某种类型的所有资源
		List<SysResource> resources = aclDao.findAllSysResources(resourceType);
		for (SysResource res : resources) {
			// 针对此资源的所有操作
			int[] indexs = res.getOpersIndex();
			if (indexs != null) {
				for (int operIndex : indexs) {
					// 查询本主体对应的某资源的某操作的权限
					AuthVO vo = searchAcl(principal, resourceType,
							res.getResourceId(), operIndex);
					if (vo != null) {
						vos.add(vo);
					}
				}
			}
		}
		return vos;
	}

	private AuthVO searchAcl(Principal principal, String resourceType,
			int resourceId, int operIndex) {
		// 首先查询主体与资源之间是否有关联（即是否授予了权限）
		ACL acl = aclDao.findACL(principal.getPrincipalType(),
				principal.getPrincipalId(), resourceType, resourceId);
		AuthVO vo = null;

		// 如果授予了权限，而且不是继承的，则马上可以得到授权信息
		if (acl != null && !acl.isExt(operIndex)) {
			vo = new AuthVO();
			vo.setResourceId(resourceId);
			vo.setOperIndex(operIndex);
			vo.setExt(false);
			vo.setPermit(acl.isPermit(operIndex));
			return vo;
		}

		// 如果没有定义授权，或者它的权限是继承的，则继续搜索其父亲的权限
		List<Principal> parents = principal.getParentPrincipal();
		if (parents == null) {
			return null;
		}

		// 对于每一个父亲，判断其授权信息
		for (Principal parent : parents) {
			AuthVO pvo = searchAcl(parent, resourceType, resourceId, operIndex);

			// 如果父亲有授权，而且是拒绝的，则立刻返回
			if (pvo != null && !pvo.isPermit()) {
				vo = new AuthVO();
				vo.setResourceId(resourceId);
				vo.setOperIndex(operIndex);
				vo.setExt(true); // 继承
				vo.setPermit(false); // 拒绝
				return vo;
			}

			// 如果父亲有授权，而且是允许的
			if (pvo != null && pvo.isPermit()) {
				if (vo == null) { // 只需创建一遍
					vo = new AuthVO();
					vo.setResourceId(resourceId);
					vo.setOperIndex(operIndex);
					vo.setExt(true);
					vo.setPermit(true);
				}
			}
		}

		return vo;
	}

	@Override
	public void addOrUpdatePermission(String principalType, int principalId,
			String resourceType, List<AuthVO> acls) {

		// 先把所有授权删除
		aclDao.delAcls(principalType, principalId, resourceType);

		// 逐条插入
		if (acls != null) {
			for (AuthVO auth : acls) {
				int resourceId = auth.getResourceId();
				int operIndex = auth.getOperIndex();
				boolean permit = auth.isPermit();
				boolean ext = auth.isExt();
				ACL acl = aclDao.findACL(principalType, principalId,
						resourceType, resourceId);
				if (acl == null) { // 创建ACL对象
					acl = new ACL();
					acl.setAclTriState(-1); // 默认为继承，即针对某种资源，如果其中有某些操作未定义，默认即是继承
					acl.setPrincipalType(principalType);
					acl.setPrincipalId(principalId);
					acl.setResourceType(resourceType);
					acl.setResourceId(resourceId);
					acl.setPermission(operIndex, permit, ext);
					aclDao.save(acl);
				} else { // 更新ACL对象
					acl.setPermission(operIndex, permit, ext);
					aclDao.update(acl);
				}
			}
		}
	}

	@Override
	public List findPermitMenus(int userId) {

		// 首先查询出所有顶级菜单
		List<Menu> topMenus = menuDao.findAllTopMenus();

		// 针对每一个顶级菜单及其下面的孩子节点，判断用户对其是否拥有访问的允许权限
		// 如果没有允许的权限，则把此菜单节点删除
		removeDenyMenus(topMenus, userId);

		return topMenus;
	}

	// 删除那些不允许的菜单项
	private void removeDenyMenus(Collection<Menu> menus, int userId) {

		for (Iterator<Menu> iterator = menus.iterator(); iterator.hasNext();) {
			Menu menu = iterator.next();

			// 查询出当前用户针对对应菜单项的权限
			AuthVO vo = searchAcl(userDao.findById(User.class, userId),
					menu.getResourceType(), menu.getResourceId(),
					menu.getOpersIndex()[0]);

			// 如果此菜单尚未授权，或已授权但不允许访问本菜单项
			if (vo == null || !vo.isPermit()) {
				iterator.remove();
			} else {
				// 如果本菜单项是允许的，则继续搜索其子菜单
				removeDenyMenus(menu.getChildren(), userId);
			}
		}
	}

	@Override
	public boolean hasPermission(int userId, String resourceSn, String operSn) {

		// 主体对象
		User user = userDao.findById(User.class, userId);

		// 根据资源唯一标识查找资源对象
		SysResource resource = aclDao.findSysResourceBySn(resourceSn);

		// 从资源对象中，根据操作的唯一标识，得到操作的索引值
		int operIndex = resource.getOperIndexBySn(operSn);

		AuthVO vo = searchAcl(user, resource.getResourceType(),
				resource.getResourceId(), operIndex);

		if (vo != null && vo.isPermit()) {
			return true;
		}

		return false;
	}

}
