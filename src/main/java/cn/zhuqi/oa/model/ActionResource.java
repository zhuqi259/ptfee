package cn.zhuqi.oa.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActionResource implements SysResource {

	private int id;

	/**
	 * 资源所对应的Action类名（可能有多个，用"|"隔开）
	 */
	private String className;

	/**
	 * 资源的命名，比如： 组织机构管理、公文管理
	 */
	private String name;

	/**
	 * 资源的唯一标识 比如： org,document等等
	 */
	private String sn;

	/**
	 * 父资源的唯一标识 比如： party
	 */
	private String parentSn;

	/**
	 * 排序号
	 */
	private int orderNumber;

	/**
	 * 资源所包含的操作 key是操作的唯一标识（助记符），比如：UPDATE,DEL,READ,CREATE
	 */
	private Map<String, ActionMethodOper> opers;

	/**
	 * 父资源
	 */
	private ActionResource parent;

	private Set<ActionResource> children;

	@Override
	public int getResourceId() {
		return id;
	}

	@Override
	public int[] getOpersIndex() {
		if (opers != null) {
			Collection<ActionMethodOper> amo = opers.values();
			int[] opersIndex = new int[amo.size()];
			int i = 0;
			for (ActionMethodOper o : amo) {
				opersIndex[i++] = o.getOperIndex();
			}
			return opersIndex;
		}
		return null;
	}

	@Override
	public String getResourceType() {
		return "ActionResource";
	}

	@Override
	public List<SysResource> getChildrenResource() {
		if (children != null) {
			List<SysResource> cs = new ArrayList<SysResource>();
			cs.addAll(children);
			return cs;
		}
		return null;
	}

	@Override
	public int getOperIndexBySn(String operSn) {
		return opers.get(operSn).getOperIndex();
	}

	/**
	 * 根据方法名来得到对应的操作的唯一标识
	 * 
	 * @param methodName
	 * @return
	 */
	public String getOperSnByMethodName(String methodName) {
		if (opers == null) {
			return null;
		}
		for (ActionMethodOper amo : opers.values()) {
			if (contains(amo.getMethodName(), methodName)) {
				return amo.getOperSn();
			}
		}
		return null;
	}

	/**
	 * amoMethodName字符串是否包含有methodName字符串 amoMethodName字符串的格式是： add|addInput|...
	 * 
	 * @param amoMethodName
	 * @param methodName
	 * @return
	 */
	private boolean contains(String amoMethodName, String methodName) {
		// 按照"|"隔开方法名
		String[] allMethods = amoMethodName.split("\\|");
		for (String m : allMethods) {
			if (m.equals(methodName)) {
				return true;
			}
		}
		return false;
	}

	public void addClassName(String clsName) {
		if (this.className == null) {
			this.className = clsName;
		} else {
			this.className = this.className + "|" + clsName;
		}
	}

	public void addActionMethodOper(String methodName, String operName,
			String operSn, int operIndex) {
		if (opers == null) {
			opers = new HashMap<String, ActionMethodOper>();
		}
		ActionMethodOper amo = opers.get(operSn);
		if (amo != null) {
			amo.addMethodName(methodName);
		} else {

			// 首先，判断索引值是否已存在，如果已经存在，则抛出异常，不允许重复
			for (ActionMethodOper o : opers.values()) {
				if (o.getOperIndex() == operIndex) {
					throw new RuntimeException("针对资源【" + name + "】的操作" + "【"
							+ o.getOperName() + "】已经和索引【" + o.getOperIndex()
							+ "】绑定，" + "无法再次把一个新的操作【" + operName + "】绑定到该索引值");
				}
			}

			amo = new ActionMethodOper();
			amo.setMethodName(methodName);
			amo.setOperName(operName);
			amo.setOperIndex(operIndex);
			amo.setOperSn(operSn);
			opers.put(operSn, amo);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Map<String, ActionMethodOper> getOpers() {
		return opers;
	}

	public void setOpers(Map<String, ActionMethodOper> opers) {
		this.opers = opers;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public ActionResource getParent() {
		return parent;
	}

	public void setParent(ActionResource parent) {
		this.parent = parent;
	}

	public Set<ActionResource> getChildren() {
		return children;
	}

	public void setChildren(Set<ActionResource> children) {
		this.children = children;
	}

	public String getParentSn() {
		return parentSn;
	}

	public void setParentSn(String parentSn) {
		this.parentSn = parentSn;
	}
}
