package cn.zhuqi.oa.vo;

public class TaskVO {

	private String id;
	/**
	 * 任务名字,即Activity name
	 */
	private String taskname;
	/**
	 * 发起人
	 */
	private String username;

	/**
	 * 发起时间
	 */
	private String time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
