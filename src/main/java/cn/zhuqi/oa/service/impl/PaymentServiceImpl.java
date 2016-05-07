package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.PaymentDao;
import cn.zhuqi.oa.model.Payment;
import cn.zhuqi.oa.service.PaymentService;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	@Resource
	private PaymentDao paymentDao;

	@Override
	public void addPayment(Payment payment) {
		paymentDao.save(payment);
	}

	@Override
	public List<Payment> getAllPayment(int projectId, int activityId) {
		return paymentDao.getAllPayment(projectId, activityId);
	}

	@Override
	public Payment findPaymentById(int paymentId) {
		return paymentDao.findById(Payment.class, paymentId);
	}

	@Override
	public void updatePayment(Payment payment) {
		paymentDao.update(payment);
	}

	@Override
	public void delPayment(int paymentId) {
		paymentDao.del(findPaymentById(paymentId));
	}

	@Override
	public Payment getPayment(int projectId, int zpaymentId) {
		return paymentDao.getPayment(projectId, zpaymentId);
	}

	@Override
	public List<Payment> getAllProjectPayment(int projectId) {
		return paymentDao.getAllProjectPayment(projectId);
	}

}
