package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.ZPayment;

public interface ZPaymentService {

	public void addZPayment(ZPayment zPayment);

	public ZPayment findZPaymentById(int zPaymentId);

	public void delZPayment(int zPaymentId);

	public void updateZPayment(ZPayment zPayment);
	
	/**
	 * 不分条件，查询出所有的ZPayment对象
	 * 
	 * @return
	 */
	public List<ZPayment> findAllZPayments();
}
