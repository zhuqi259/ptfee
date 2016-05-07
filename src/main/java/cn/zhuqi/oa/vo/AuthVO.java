package cn.zhuqi.oa.vo;

/**
 * 在界面上授权的时候，向后台传输的授权信息
 * @author Lee
 *
 */
public class AuthVO {
	private int resourceId;
	private int operIndex;
	private boolean permit;//允许：true，拒绝：false
	private boolean ext;//继承：true，不继承：false

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public int getOperIndex() {
		return operIndex;
	}

	public void setOperIndex(int operIndex) {
		this.operIndex = operIndex;
	}

	public boolean isPermit() {
		return permit;
	}

	public void setPermit(boolean permit) {
		this.permit = permit;
	}

	public boolean isExt() {
		return ext;
	}

	public void setExt(boolean ext) {
		this.ext = ext;
	}

}
