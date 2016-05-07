package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.ZPaymentDao;
import cn.zhuqi.oa.model.ZPayment;
import cn.zhuqi.oa.service.ZPaymentService;

@Service("zpaymentService")
public class ZPaymentServiceImpl implements ZPaymentService {

	@Resource
	private ZPaymentDao zPaymentDao;

	@Override
	public void addZPayment(ZPayment zPayment) {
		zPaymentDao.save(zPayment);
	}

	@Override
	public ZPayment findZPaymentById(int zPaymentId) {
		return zPaymentDao.findById(ZPayment.class, zPaymentId);
	}

	@Override
	public void delZPayment(int zPaymentId) {
		zPaymentDao.del(findZPaymentById(zPaymentId));
	}

	@Override
	public void updateZPayment(ZPayment zPayment) {
		zPaymentDao.update(zPayment);
	}

	@Override
	public List<ZPayment> findAllZPayments() {
		return zPaymentDao.findAll(ZPayment.class);
	}

}
