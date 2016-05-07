package cn.zhuqi.oa.model;

import java.util.List;

public class Role implements Principal{
	private int id;
	private String name;
	
	@Override
	public List<Principal> getParentPrincipal() {
		//角色和角色之间没有父子关系
		return null;
	}
	@Override
	public int getPrincipalId() {
		return id;
	}

	@Override
	public String getPrincipalType() {
		return "Role";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
