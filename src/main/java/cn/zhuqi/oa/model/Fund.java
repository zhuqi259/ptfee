package cn.zhuqi.oa.model;

public class Fund {

	private Integer id;
	private Zfund zfund;
	private String money;
	private String time;
	private String oper;
	private Project project;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Zfund getZfund() {
		return zfund;
	}

	public void setZfund(Zfund zfund) {
		this.zfund = zfund;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
