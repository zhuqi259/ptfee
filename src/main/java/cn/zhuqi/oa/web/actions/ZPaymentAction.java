package cn.zhuqi.oa.web.actions;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.ZPayment;
import cn.zhuqi.oa.service.ZPaymentService;

import com.opensymphony.xwork2.ModelDriven;

@Controller("zpaymentAction")
@Scope("prototype")
public class ZPaymentAction extends BaseAction implements ModelDriven {

	private ZPayment zPayment;

	@Resource
	private ZPaymentService zPaymentService;

	@Override
	public Object getModel() {
		if (zPayment == null) {
			zPayment = new ZPayment();
		}
		return zPayment;
	}

	public String addInput() {
		return "add_input";
	}

	public String add() {
		zPaymentService.addZPayment(zPayment);
		return "add_success";
	}

	public String updateInput() {
		zPayment = zPaymentService.findZPaymentById(zPayment.getId());
		return "update_input";
	}

	public String update() {
		zPaymentService.updateZPayment(zPayment);
		return "update_success";
	}

	public void del() {
		zPaymentService.delZPayment(zPayment.getId());
	}

}
