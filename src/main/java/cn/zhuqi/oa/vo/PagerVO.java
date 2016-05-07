package cn.zhuqi.oa.vo;

import java.util.List;

/**
 * 分页类
 * @author zhuqi
 *
 */
public class PagerVO {
	private int total;
	private List datas;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getDatas() {
		return datas;
	}
	public void setDatas(List datas) {
		this.datas = datas;
	}
}
