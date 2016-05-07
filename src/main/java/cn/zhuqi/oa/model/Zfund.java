package cn.zhuqi.oa.model;

public class Zfund {
	private Integer id;
	private String fund_name;
	private Integer fn_use;
	private String fund_time;
	private Integer ft_use;
	private String fund_oper;
	private Integer fo_use;
	private ZActivity activity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFund_name() {
		return fund_name;
	}

	public void setFund_name(String fund_name) {
		this.fund_name = fund_name;
	}

	public Integer getFn_use() {
		return fn_use;
	}

	public void setFn_use(Integer fn_use) {
		this.fn_use = fn_use;
	}

	public String getFund_time() {
		return fund_time;
	}

	public void setFund_time(String fund_time) {
		this.fund_time = fund_time;
	}

	public Integer getFt_use() {
		return ft_use;
	}

	public void setFt_use(Integer ft_use) {
		this.ft_use = ft_use;
	}

	public String getFund_oper() {
		return fund_oper;
	}

	public void setFund_oper(String fund_oper) {
		this.fund_oper = fund_oper;
	}

	public Integer getFo_use() {
		return fo_use;
	}

	public void setFo_use(Integer fo_use) {
		this.fo_use = fo_use;
	}

	public ZActivity getActivity() {
		return activity;
	}

	public void setActivity(ZActivity activity) {
		this.activity = activity;
	}

}
