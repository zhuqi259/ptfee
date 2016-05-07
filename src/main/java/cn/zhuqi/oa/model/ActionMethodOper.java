package cn.zhuqi.oa.model;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Action方法操作！
 * @author Lee
 *
 */
public class ActionMethodOper {
	
	/**
	 * 方法名，同一种操作可以对应多个方法
	 * 比如：addInput,add,del,update|updateInput
	 */
	private String methodName;
	
	/**
	 * 操作名称
	 * 比如：添加、删除、更新、发布等等等等
	 */
	private String operName;
	
	/**
	 * 操作唯一标识：
	 * 比如：ADD,DEL,UPDATE,READ
	 */
	private String operSn;
	
	/**
	 * 操作存储索引，这个索引值必须大于或等于0，并且小于或等于31
	 * 同一个资源所包含的操作中，其索引值是唯一的，不允许重复。
	 */
	private int operIndex;
	
	public void addMethodName(String mn){
		if(methodName == null){
			this.methodName = mn;
		}else{
			String[] methodNames = this.methodName.split("\\|");
			if(!ArrayUtils.contains(methodNames, mn)){
				this.methodName = this.methodName + "|" + mn;
			}
		}
	}
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getOperSn() {
		return operSn;
	}
	public void setOperSn(String sn) {
		this.operSn = sn;
	}
	public int getOperIndex() {
		return operIndex;
	}
	public void setOperIndex(int operIndex) {
		this.operIndex = operIndex;
	}
}
