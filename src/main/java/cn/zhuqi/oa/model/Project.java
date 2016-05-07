package cn.zhuqi.oa.model;

import java.util.Date;
import java.util.Set;

public class Project {

	public static final String STATUS_NEW = "新建";
	public static final String STATUS_FINISH = "完成";

	/**
	 * 工程内部ID号
	 */
	private int id;

	/**
	 * 工程实际编号
	 */
	private String fid;
	/**
	 * 工程所属年限
	 */
	private Integer myYear;

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

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 工程状态
	 */
	private String status;

	/**
	 * 所属流程实例ID
	 */
	private String processInstanceId;

	/**
	 * 所属工作流
	 */
	private Workflow workflow;

	/**
	 * 流程创建者，即客户代表
	 */
	private User creator;

	/**
	 * 该工程的文件列表
	 */
	private Set<Pfile> files;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getMyYear() {
		return myYear;
	}

	public void setMyYear(Integer myYear) {
		this.myYear = myYear;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Set<Pfile> getFiles() {
		return files;
	}

	public void setFiles(Set<Pfile> files) {
		this.files = files;
	}

}
