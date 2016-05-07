package cn.zhuqi.oa.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.zhuqi.oa.model.ActionResource;

/**
 * 对ActionResource进行管理的时候，展现一棵树
 * 
 * @author Lee
 * 
 */
public class ActionResourceTreeVO {
	private String data;
	private Map attr = new HashMap();
	private List children = new ArrayList();

	public ActionResourceTreeVO(ActionResource ar) {
		this.data = ar.getName();

		// 给节点添加属性
		attr.put("id", ar.getId()); // 【此属性是必须的！！！在刷新的时候，保持住打开状态的关键！！！】
		attr.put("resourceId", ar.getId());
		Set subres = ar.getChildren();
		for (Iterator iterator = subres.iterator(); iterator.hasNext();) {
			ActionResource subr = (ActionResource) iterator.next();
			ActionResourceTreeVO art = new ActionResourceTreeVO(subr);
			children.add(art);
		}
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Map getAttr() {
		return attr;
	}

	public void setAttr(Map attr) {
		this.attr = attr;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}
}
