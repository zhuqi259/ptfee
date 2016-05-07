package cn.zhuqi.oa.model;

public class ACL {
	private int id;
	private String principalType;
	private int principalId;
	private String resourceType;
	private int resourceId;
	private int aclState;
	private int aclTriState;
	
	public void setPermission(int index,boolean permit,boolean ext){
		if(index < 0 || index > 31){
			throw new RuntimeException("操作索引值有误！");
		}
		if(ext){
			//要设置aclTriState相应的位为1，以示继承
			aclTriState = setBit(aclTriState,index,true);
		}else{
			//要设置aclTriState相应的位为0，以示不继承
			aclTriState = setBit(aclTriState,index,false);
		}
		
		//设置aclState相应位的取值！
		aclState = setBit(aclState,index,permit);
	}
	
	public boolean isPermit(int index){
		return getBit(aclState,index);
	}
	
	public boolean isExt(int index){
		return getBit(aclTriState,index);
	}
	
	/**
	 * 将某个int类型的值的第index位改为1或0
	 * @param i 某个需要改变的值
	 * @param index 第几位
	 * @param value true表示改为1，false表示改为0
	 * @return
	 */
	private int setBit(int i,int index,boolean value){
		int temp = 1;
		temp = temp << index;
		if(value){ //如果要把这一位设置为1
			i = i | temp;
		}else{ //如果要把这一位设置为0
			temp = ~temp;
			i = i & temp;
		}
		return i;
	}
	
	private boolean getBit(int i,int index){
		int temp = 1;
		temp = temp << index;
		temp = i & temp;
		if(temp == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPrincipalType() {
		return principalType;
	}
	public void setPrincipalType(String principalType) {
		this.principalType = principalType;
	}
	public int getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(int principalId) {
		this.principalId = principalId;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public int getAclState() {
		return aclState;
	}
	public void setAclState(int aclState) {
		this.aclState = aclState;
	}
	public int getAclTriState() {
		return aclTriState;
	}
	public void setAclTriState(int aclTriState) {
		this.aclTriState = aclTriState;
	}
}
