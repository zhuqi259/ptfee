package cn.zhuqi.oa.model;

import java.util.HashSet;
import java.util.Set;

public class Workflow {

	/**
	 * 工作流ID
	 */
	private int id;
	/**
	 * 工作流名称
	 */
	private String name;
	/**
	 * 工作流图片
	 */
	private byte[] processImage;

	private Set<ZActivity> activities = new HashSet<ZActivity>();

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

	public byte[] getProcessImage() {
		return processImage;
	}

	public void setProcessImage(byte[] processImage) {
		this.processImage = processImage;
	}

	public Set<ZActivity> getActivities() {
		return activities;
	}

	public void setActivities(Set<ZActivity> activities) {
		this.activities = activities;
	}

}
