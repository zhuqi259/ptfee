package cn.zhuqi.oa.vo;

public class ProjectVO {
	private int id;
	private String fid;
	private String proname;
	private String status;

	public ProjectVO(int id, String fid, String proname, String status) {
		super();
		this.id = id;
		this.fid = fid;
		this.proname = proname;
		this.status = status;
	}

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

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
