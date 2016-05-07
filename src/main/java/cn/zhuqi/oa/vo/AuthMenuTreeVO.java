package cn.zhuqi.oa.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.zhuqi.oa.model.Menu;

/**
 * 登录之后，显示导航菜单树所用的VO对象
 * 
 * @author Lee
 * 
 */
public class AuthMenuTreeVO {

	private Map data = new HashMap();
	private Map attr = new HashMap();
	private List children = new ArrayList();

	public AuthMenuTreeVO(Menu m) {

		// 菜单项名称
		this.data.put("title", m.getName());

		// 菜单项链接地址
		Map linkAttr = new HashMap();
		linkAttr.put("href", m.getHref());
		this.data.put("attr", linkAttr);

		Set submenus = m.getChildren();
		for (Iterator iterator = submenus.iterator(); iterator.hasNext();) {
			Menu submenu = (Menu) iterator.next();
			AuthMenuTreeVO mtv = new AuthMenuTreeVO(submenu);
			children.add(mtv);
		}
	}

	// TODO modify by zhuqi!!!!!!!!!!
	public AuthMenuTreeVO(Menu m, int count) {

		String name = m.getName();
		// System.out.println("name---------------" + name);
		if (name.equals("待办任务")) {
			m.setName(name + "(" + count + ")");
		}

		// 菜单项名称
		this.data.put("title", m.getName());

		// 菜单项链接地址
		Map linkAttr = new HashMap();
		linkAttr.put("href", m.getHref());
		this.data.put("attr", linkAttr);

		Set submenus = m.getChildren();
		for (Iterator iterator = submenus.iterator(); iterator.hasNext();) {
			Menu submenu = (Menu) iterator.next();
			AuthMenuTreeVO mtv = new AuthMenuTreeVO(submenu, count);
			children.add(mtv);
		}
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public Map getAttr() {
		return attr;
	}

	public void setAttr(Map attr) {
		this.attr = attr;
	}
}
