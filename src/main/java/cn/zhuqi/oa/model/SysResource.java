package cn.zhuqi.oa.model;

import java.util.List;

public interface SysResource {
	public int getResourceId();
	public String getResourceType();
	public int[] getOpersIndex();
	public int getOperIndexBySn(String operSn);
	public List<SysResource> getChildrenResource();
}
