package cn.zhuqi.oa.model;

import cn.zhuqi.system.StringUtil;

public class Zfile {
	private int id;
	private String name;
	private String path;
	private String contentType;
	private ZActivity activity;
	private Database base;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		this.contentType = StringUtil.getType(this.path);
	}

	public ZActivity getActivity() {
		return activity;
	}

	public void setActivity(ZActivity activity) {
		this.activity = activity;
	}

	public Database getBase() {
		return base;
	}

	public void setBase(Database base) {
		this.base = base;
	}

	public String getContentType() {
		if (contentType == null) {
			return StringUtil.getType(this.path);
		}
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
