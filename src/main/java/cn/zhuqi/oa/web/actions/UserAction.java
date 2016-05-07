package cn.zhuqi.oa.web.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.annotations.Oper;
import cn.zhuqi.oa.annotations.Res;
import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.service.PartyService;
import cn.zhuqi.oa.service.RoleService;
import cn.zhuqi.oa.service.UserService;
import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.oa.web.JSONUtils;
import cn.zhuqi.system.MD5Util;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("userAction")
@Scope("prototype")
@Res(name = "用户操作", sn = "user", orderNumber = 80, parentSn = "security")
public class UserAction extends BaseAction implements ModelDriven {

	@Resource
	private UserService userService;

	@Resource
	private PartyService partyService;

	@Resource
	private RoleService roleService;

	private User user;

	private int[] roleIds;

	// private String sSearch;

	private int userId;

	private String used;

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	@Override
	public Object getModel() {
		if (user == null) {
			user = new User();
		}
		userId = currentUser().getId();
		return user;
	}

	@Oper
	public String execute() {

		return "index";
	}

	// 查出所有的人员信息
	@Oper
	public void list() {
		Map map = new HashMap();

		PagerVO pv = userService.findPersonUsers(sSearch);

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	@Oper
	public String addInput() {
		// 查出所有的角色来，方便选择
		List<Role> roles = roleService.findAllRoles();
		// System.out.println("RRRRRRRRRRR==========>" + roles);

		ActionContext.getContext().put("roles", roles);

		return "add_input";
	}

	@Oper
	public String add() {
		// TODO 或者这个放到ajax检查中去..
		if (userService.findUserByUsername(user.getUsername()) != null) {
			throw new RuntimeException("该账号已经存在，请重新定义!!");
		}
		String password = MD5Util.Str2Md5_32(user.getPassword());
		user.setPassword(password);
		userService.addUser(user, roleIds);
		return "add_success";
	}

	@Oper
	public String updateInput() {
		user = userService.getUserById(user.getId());
		// 这里是实际得到这个User,非延迟加载~~
		if (user == null) {
			throw new RuntimeException("该用户还没有登陆账号，请添加~");
		}
		// 查询出所有的角色来，以方便在下拉列表框中选择它们
		List<Role> roles = roleService.findAllRoles();
		ActionContext.getContext().put("roles", roles);

		// 查询出某个用户拥有的所有角色来，以方便打开更新页面时，自动选中这些角色
		List<Integer> selectedRoleIds = userService.findRoleIdsOfUser(user
				.getId());
		ActionContext.getContext().put("selectedRoleIds", selectedRoleIds);

		return "update_input";
	}

	// 判断roleId是否在所属的selectedRoleIds列表中
	public String hasSelected(int roleId, List<Integer> selectedRoleIds) {
		if (selectedRoleIds == null) {
			return "";
		}
		for (Integer i : selectedRoleIds) {
			if (i.equals(roleId)) {
				return "selected";
			}
		}
		return "";
	}

	@Oper
	public String update() {
		if ("1".equals(used)) {
			String password = MD5Util.Str2Md5_32(user.getPassword());
			user.setPassword(password);
		} else {
			User original = userService.findUserById(user.getId());
			user.setPassword(original.getPassword());
		}
		userService.updateUser(user, roleIds);
		return "update_success";
	}

	public String updatePwdInput() {
		user = userService.findUserById(userId);
		return "update_pwd_input";
	}

	@Oper
	public void del() {
		userService.delUser(user.getId());
	}

	public int[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(int[] roleIds) {
		this.roleIds = roleIds;
	}
}
