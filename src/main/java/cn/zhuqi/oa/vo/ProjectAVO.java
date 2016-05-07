package cn.zhuqi.oa.vo;

public class ProjectAVO {
	/**
	 * 工程内部ID号
	 */
	private int id;

	/**
	 * 工程名称
	 */
	private String proname;

	/**
	 * 开发单位
	 */
	private String developer;

	/**
	 * 受理建筑面积
	 */
	private String area;
	/**
	 * 坐落地点
	 */
	private String location;
	/**
	 * 收费标准
	 */
	private String fee;
	/**
	 * 收取款项
	 */
	private String sfee;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getSfee() {
		return sfee;
	}

	public void setSfee(String sfee) {
		this.sfee = sfee;
	}

}
