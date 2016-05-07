package cn.zhuqi.oa.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.zhuqi.oa.model.Party;

/**
 * 本VO是为了能够在页面上显示jsTree机构树而量身定做的。 根据jsTree对于JSON格式串的要求，设置相关的属性。
 * 
 * 对组织机构进行管理时用到
 * 
 * @author Lee
 * 
 */
public class PartyTreeVO {
	private String data;
	private Map attr = new HashMap();
	private List children = new ArrayList();

	public PartyTreeVO(Party p) {
		this.data = p.getName();

		// 给节点添加属性
		attr.put("id", p.getId()); // 【此属性是必须的！！！在刷新的时候，保持住打开状态的关键！！！】
		attr.put("partyId", p.getId());
		attr.put("partyType", p.getClass().getSimpleName().toLowerCase());

		Set parties = p.getChildren();
		for (Iterator iterator = parties.iterator(); iterator.hasNext();) {
			Party subparty = (Party) iterator.next();
			PartyTreeVO ptv = new PartyTreeVO(subparty);
			children.add(ptv);
		}
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
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
