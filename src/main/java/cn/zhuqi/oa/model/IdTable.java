package cn.zhuqi.oa.model;

import cn.zhuqi.system.TimeUtil;

public class IdTable {

	private int id;

	private Integer myYear = TimeUtil.getToday();

	private Integer myId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getMyYear() {
		return myYear;
	}

	public void setMyYear(Integer myYear) {
		this.myYear = myYear;
	}

	public Integer getMyId() {
		return myId;
	}

	public void setMyId(Integer myId) {
		this.myId = myId;
	}

}
