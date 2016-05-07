package cn.zhuqi.oa.dao;

import java.util.List;

import cn.zhuqi.oa.model.Payment;

public interface PaymentDao extends BaseDao {

	public List<Payment> getAllPayment(int projectId, int activityId);

	public Payment getPayment(int projectId, int zpaymentId);

	public List<Payment> getAllProjectPayment(int projectId);
}
