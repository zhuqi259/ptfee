package cn.zhuqi.oa.model;

import cn.zhuqi.system.StringUtil;

public class Pfile {
	private int id;
	private Zfile zfile;
	private String path;
	private String contentType;
	private Project project;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Zfile getZfile() {
		return zfile;
	}

	public void setZfile(Zfile zfile) {
		this.zfile = zfile;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		this.contentType = StringUtil.getType(this.path);
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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
