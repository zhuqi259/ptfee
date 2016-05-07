package cn.zhuqi.system;

import java.util.List;

import cn.zhuqi.oa.dao.RoleDao;
import cn.zhuqi.oa.model.Party;
import cn.zhuqi.oa.model.Person;
import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.model.User;

public class UserUtil {

	/**
	 * 判断是否是客户代表(CSR) customer's representative
	 * 
	 * @param roles
	 * @param roleDao
	 * @return
	 */
	private static boolean IsCSR(List<Role> roles, RoleDao roleDao) {
		Role role = roleDao.findByRolename("客户代表");
		return roles.contains(role);
	}

	private static boolean IsCSR(User user) {
		Person person = user.getPerson();
		if (person == null) {
			return false;
		}
		Party party = person.getParent();
		if (party == null || party.getName() == null)
			return false;
		return party.getName().equals("客户代表");
	}

	/**
	 * 判断是否是客户代表(CSR) customer's representative
	 * 
	 * @param user
	 * @param roleDao
	 * @return
	 */
	public static boolean IsCSR(User user, RoleDao roleDao) {
		List<Role> roles = user.getMyRoles();
		return IsCSR(roles, roleDao) || IsCSR(user);
	}

	public static boolean testUser(User user, String RoleOrPositionname) {
		if (!"".equals(RoleOrPositionname)) {
			String name = null;
			try {
				name = user.getPerson().getParent().getName();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			// TODO modify by zhuqi 2013-8-20
			if (RoleOrPositionname.equals(name)) {
				return true;
			} else {
				List<Role> roles = user.getMyRoles();
				for (Role role : roles) {
					if (RoleOrPositionname.equals(role.getName())) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
