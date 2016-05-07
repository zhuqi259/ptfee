package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.Payment;

public interface PaymentService {

	public void addPayment(Payment payment);
	
	public void updatePayment(Payment payment);

	public Payment findPaymentById(int paymentId);
	
	public void delPayment(int paymentId);

	public List<Payment> getAllPayment(int projectId, int activityId);
	
	public Payment getPayment(int projectId, int zpaymentId);
	
	public List<Payment> getAllProjectPayment(int projectId);
}
