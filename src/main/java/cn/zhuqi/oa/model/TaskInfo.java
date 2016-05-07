package cn.zhuqi.oa.model;

import java.util.Date;

public class TaskInfo {
	private Integer id;
	private User ufr;
	private Project project;
	private ZActivity activity;
	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUfr() {
		return ufr;
	}

	public void setUfr(User ufr) {
		this.ufr = ufr;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ZActivity getActivity() {
		return activity;
	}

	public void setActivity(ZActivity activity) {
		this.activity = activity;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
