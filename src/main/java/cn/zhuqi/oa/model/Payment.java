package cn.zhuqi.oa.model;

import java.util.Date;

import cn.zhuqi.system.TimeUtil;

public class Payment {
	private int id;
	// private String name;
	private ZPayment zpayment;
	private String money;
	private String payer;
	private User operator;
	// private ZActivity activity;
	private Date operTime;
	private String rTime;
	private String payTime;
	private Project project;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ZPayment getZpayment() {
		return zpayment;
	}

	public void setZpayment(ZPayment zpayment) {
		this.zpayment = zpayment;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
		this.rTime = TimeUtil.Date2Str(operTime);
	}

	public String getrTime() {
		return rTime;
	}

	public void setrTime(String rTime) {
		this.rTime = rTime;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

}
