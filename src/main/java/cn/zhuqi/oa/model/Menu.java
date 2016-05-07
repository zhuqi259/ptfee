package cn.zhuqi.oa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Menu implements SysResource {
	private int id;
	private String name;
	private String href;
	private int orderNumber;
	private String sn;
	private boolean display;
	private Menu parent;
	private Set<Menu> children;
	
	@Override
	public int getResourceId() {
		return id;
	}
	@Override
	public int[] getOpersIndex() {
		return new int[]{0};
	}
	@Override
	public List<SysResource> getChildrenResource() {
		if(children != null){
			List<SysResource> cs = new ArrayList<SysResource>();
			cs.addAll(children);
			return cs;
		}
		return null;
	}
	@Override
	public String getResourceType() {
		return "Menu";
	}
	@Override
	public int getOperIndexBySn(String operSn) {
		return 0;
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
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public Set<Menu> getChildren() {
		return children;
	}
	public void setChildren(Set<Menu> children) {
		this.children = children;
	}
}
