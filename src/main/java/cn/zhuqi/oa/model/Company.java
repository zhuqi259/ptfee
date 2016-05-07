package cn.zhuqi.oa.model;


public class Company extends Party {
	
	/**
	 * 电话
	 */
	private String tel;
	
	/**
	 * 传真
	 */
	private String fax;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 邮编
	 */
	private String postcode;
	
	/**
	 * 网站
	 */
	private String site;
	
	/**
	 * 电子邮件
	 */
	private String email;
	
	/**
	 * 所属行业
	 */
	private String industry;

	@Override
	public String getPrincipalType() {
		return "Company";
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
}
