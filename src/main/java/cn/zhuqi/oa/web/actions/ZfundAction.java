package cn.zhuqi.oa.web.actions;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.Zfund;
import cn.zhuqi.oa.service.ZfundService;

import com.opensymphony.xwork2.ModelDriven;

@Controller("zfundAction")
@Scope("prototype")
public class ZfundAction extends BaseAction implements ModelDriven {

	private Zfund zfund;
	@Resource
	private ZfundService zfundService;

	@Override
	public Object getModel() {
		if (zfund == null) {
			zfund = new Zfund();
		}
		return zfund;
	}

	public String addInput() {
		return "add_input";
	}

	public String add() {
		zfundService.addZfund(zfund);
		return "add_success";
	}

	public String updateInput() {
		zfund = zfundService.findZfundById(zfund.getId());
		return "update_input";
	}

	public String update() {
		zfundService.updateZfund(zfund);
		return "update_success";
	}

	public void del() {
		zfundService.delZfund(zfund.getId());
	}

}
