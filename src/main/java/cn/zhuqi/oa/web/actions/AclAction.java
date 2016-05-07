package cn.zhuqi.oa.web.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.ActionResource;
import cn.zhuqi.oa.model.Menu;
import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.service.AclService;
import cn.zhuqi.oa.service.MenuService;
import cn.zhuqi.oa.service.ResourceService;
import cn.zhuqi.oa.service.RoleService;
import cn.zhuqi.oa.service.UserService;
import cn.zhuqi.oa.vo.AuthVO;
import cn.zhuqi.oa.vo.MenuTreeVO;
import cn.zhuqi.oa.web.JSONUtils;

import com.opensymphony.xwork2.ActionContext;

@Controller("aclAction")
@Scope("prototype")
public class AclAction extends BaseAction {

	@Resource
	private RoleService roleService;

	@Resource
	private ResourceService resourceService;

	@Resource
	private UserService userService;

	@Resource
	private MenuService menuService;

	@Resource
	private AclService aclService;

	private String principalType;
	private int principalId;

	private int topMenuId;

	// 用来接收给菜单授权时，选中的菜单ID列表
	private List<AuthVO> authvos;

	// 用来接收查询条件（在用户授权界面上，可以输入姓名，查询人员）
	// private String sSearch;

	/**
	 * 打开角色授权主界面
	 */
	public String roleAuthIndex() {
		return "role_auth_index";
	}

	/**
	 * 角色授权主界面上显示的角色列表树
	 */
	public void roleAuthIndexTree() {
		// 查出所有的角色对象
		List<Role> roles = roleService.findAllRoles();

		// 角色树
		List roleTreeVOs = new ArrayList();

		// 每个角色创建一个VO对象
		for (Role r : roles) {
			Map roleTreeVO = new HashMap();
			roleTreeVO.put("data", r.getName());

			Map attr = new HashMap();
			attr.put("id", r.getId());
			attr.put("principalId", r.getId());
			attr.put("principalType", "Role");

			roleTreeVO.put("attr", attr);

			roleTreeVOs.add(roleTreeVO);
		}
		JSONUtils.toJSON(roleTreeVOs);
	}

	// 用户授权主界面
	public String userAuthIndex() {
		return "user_auth_index";
	}

	// 用户授权主界面上显示的用户列表
	public void userAuthIndexList() {
		// 只需要查出那些分配了登录帐号的人员即可，因为如果没有分配登录帐号，是无法
		// 进行授权的，即授权的主体是User，而不是Person
		List persons = userService.findPersonsWithUser(sSearch);
		Map map = new HashMap();
		map.put("aaData", persons);
		JSONUtils.toJSON(map);
	}

	// 部门/岗位授权主界面
	public String partyAuthIndex() {
		return "party_auth_index";
	}

	// 部门/岗位授权主界面上的部门/岗位树
	public void partyAuthIndexTree() {

	}

	// 给主体赋予菜单授权
	public void authMenu() {
		aclService.addOrUpdatePermission(principalType, principalId, "Menu",
				authvos);
	}

	// 给主体赋予ActionResource授权
	public void authActionResource() {
		aclService.addOrUpdatePermission(principalType, principalId,
				"ActionResource", authvos);
	}

	// 所有的菜单资源主界面
	public String allMenuResource() {

		// 查询出所有顶级菜单的ID列表
		List<Integer> topMenuIds = menuService.findAllTopMenuIds();

		ActionContext.getContext().put("menuIds", topMenuIds);

		return "all_menu_resource";
	}

	// 在菜单资源主界面上显示菜单树
	public void allMenuResourceTree() {

		// 接收顶级菜单的ID，构造本菜单的菜单树
		Menu topMenu = menuService.findMenuById(topMenuId);
		MenuTreeVO mtv = new MenuTreeVO(topMenu);
		JSONUtils.toJSON(mtv);
	}

	// 把菜单树显示完毕之后，需要将已有授权查询出来并显示
	public void findMenuAcls() {
		// List<ACL> acls = aclService.findAclList(principalType, principalId,
		// "Menu");
		// List<AuthVO> vos = new ArrayList<AuthVO>();
		// for(ACL acl:acls){
		// AuthVO vo = new AuthVO();
		// vo.setResourceId(acl.getResourceId());
		// vo.setOperIndex(0);
		// vo.setPermit(acl.isPermit(0));
		// vo.setExt(acl.isExt(0));
		// vos.add(vo);
		// }
		List<AuthVO> vos = aclService.findAclList(principalType, principalId,
				"Menu");
		JSONUtils.toJSON(vos);
	}

	// 所有的操作资源主界面
	public String allActionResource() {

		List<ActionResource> res = resourceService.findAllActionResources();
		System.out.println(res.size());
		ActionContext.getContext().put("ress", res);

		return "all_action_resource";
	}

	public void findActionResourceAcls() {
		// List<ACL> acls = aclService.findAclList(principalType, principalId,
		// "ActionResource");
		// List<AuthVO> vos = new ArrayList<AuthVO>();
		// for(ACL acl:acls){
		//
		// int resourceId = acl.getResourceId();
		//
		// //这条ACL记录对应的资源有多少种操作
		// Collection<ActionMethodOper> opers =
		// resourceService.findActionResourceById(resourceId)
		// .getOpers().values();
		//
		// //针对每种操作创建一个AuthVO对象
		// for(ActionMethodOper oper:opers){
		// AuthVO vo = new AuthVO();
		// vo.setResourceId(acl.getResourceId());
		// vo.setOperIndex(oper.getOperIndex());
		// vo.setPermit(acl.isPermit(oper.getOperIndex()));
		// vo.setExt(acl.isExt(oper.getOperIndex()));
		// vos.add(vo);
		// }
		// }
		List<AuthVO> vos = aclService.findAclList(principalType, principalId,
				"ActionResource");
		JSONUtils.toJSON(vos);
	}

	public String getPrincipalType() {
		return principalType;
	}

	public void setPrincipalType(String principalType) {
		this.principalType = principalType;
	}

	public int getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(int principalId) {
		this.principalId = principalId;
	}

	public int getTopMenuId() {
		return topMenuId;
	}

	public void setTopMenuId(int topMenuId) {
		this.topMenuId = topMenuId;
	}

	public List<AuthVO> getAuthvos() {
		return authvos;
	}

	public void setAuthvos(List<AuthVO> authvos) {
		this.authvos = authvos;
	}

	/*
	 * public String getSSearch() { return sSearch; }
	 * 
	 * public void setSSearch(String search) { sSearch = search; }
	 */
}
