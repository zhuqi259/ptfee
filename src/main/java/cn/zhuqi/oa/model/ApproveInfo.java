package cn.zhuqi.oa.model;

import java.util.Date;

import cn.zhuqi.system.TimeUtil;

/**
 * 审批历史信息
 * 
 * @author Zhuqi
 */
public class ApproveInfo {

	private int id;

	/**
	 * 审批时间
	 */
	private Date approveTime;

	/**
	 * 审批时间表现值
	 */
	private String rTime;

	/**
	 * 审批人
	 */
	private User approver;

	/**
	 * 工程被审批时候的状态
	 */
	private String status;

	/**
	 * 审批意见
	 */
	private String comment;

	/**
	 * 被审批的工程
	 */
	private Project project;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
		this.rTime = TimeUtil.Date2Str(approveTime);
	}

	public void setrTime(String rTime) {
		this.rTime = rTime;
	}

	public String getrTime() {
		return rTime;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
