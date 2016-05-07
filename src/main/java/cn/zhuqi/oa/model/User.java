package cn.zhuqi.oa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class User implements Principal {
	private int id;
	private String username;
	private String password;
	private Person person;
	private Set<UsersRoles> usersRoles;
	private Set<Message> fMessages;
	private Set<Message> tMessages;

	/**
	 * 一个用户的父亲是它拥有的角色以及它对应的部门/岗位
	 */
	@Override
	public List<Principal> getParentPrincipal() {
		List<Principal> parents = new ArrayList<Principal>();

		// 角色
		if (usersRoles != null) {
			for (UsersRoles ur : usersRoles) {
				parents.add(ur.getRole());
			}
		}

		// 对应的岗位或部门
		Principal parent = person.getParent();
		if (parent != null) {
			parents.add(parent);
		}

		return parents;
	}

	@Override
	public int getPrincipalId() {
		return id;
	}

	@Override
	public String getPrincipalType() {
		return "User";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Set<UsersRoles> getUsersRoles() {
		return usersRoles;
	}

	public void setUsersRoles(Set<UsersRoles> usersRoles) {
		this.usersRoles = usersRoles;
	}

	public List<Role> getMyRoles() {
		List<Role> roles = new ArrayList<Role>();
		// 角色
		if (usersRoles != null) {
			for (UsersRoles ur : usersRoles) {
				roles.add(ur.getRole());
			}
		}
		return roles;
	}

	public Set<Message> getfMessages() {
		return fMessages;
	}

	public void setfMessages(Set<Message> fMessages) {
		this.fMessages = fMessages;
	}

	public Set<Message> gettMessages() {
		return tMessages;
	}

	public void settMessages(Set<Message> tMessages) {
		this.tMessages = tMessages;
	}

}
