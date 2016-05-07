package cn.zhuqi.oa.web.actions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.annotations.Oper;
import cn.zhuqi.oa.annotations.Res;
import cn.zhuqi.oa.model.ActionMethodOper;
import cn.zhuqi.oa.model.ActionResource;
import cn.zhuqi.oa.service.ResourceService;
import cn.zhuqi.oa.vo.ActionResourceTreeVO;
import cn.zhuqi.oa.web.JSONUtils;

import com.opensymphony.xwork2.ModelDriven;

@Controller("resourceAction")
@Scope("prototype")
@Res(name = "资源操作", sn = "resource", orderNumber = 100, parentSn = "security")
public class ResourceAction extends BaseAction implements ModelDriven {

	private ActionResource model;

	@Resource
	private ResourceService resourceService;

	// 操作标识，用于删除资源对应的操作的时候
	private String operSn;

	private String methodName;

	private String operName;

	private int operIndex;

	@Override
	public Object getModel() {
		if (model == null) {
			model = new ActionResource();
		}
		return model;
	}

	// 打开资源管理主界面
	@Oper
	public String execute() {
		return "index";
	}

	// 请求资源树
	public void tree() {
		List<ActionResource> resources = resourceService
				.findAllTopActionResources();
		List<ActionResourceTreeVO> vos = new ArrayList<ActionResourceTreeVO>();
		for (ActionResource ar : resources) {
			ActionResourceTreeVO artv = new ActionResourceTreeVO(ar);
			vos.add(artv);
		}
		JSONUtils.toJSON(vos);
	}

	@Oper
	public String addInput() {
		return "add_input";
	}

	@Oper
	public String add() {
		resourceService.addActionResource(model);
		return "add_success";
	}

	@Oper
	public String updateInput() {
		model = resourceService.findActionResourceById(model.getId());
		return "update_input";
	}

	@Oper
	public String update() {
		resourceService.updateActionResource(model);
		return "update_success";
	}

	@Oper
	public String del() {
		resourceService.delActionResource(model.getId());
		return "del_success";
	}

	// 删除资源的某个操作，本请求由jQuery的AJAX发起
	// 参数是：id,operSn
	@Oper
	public void delOper() {
		resourceService.delActionResourceOper(model.getId(), operSn);
	}

	// 打开资源的操作添加界面
	@Oper(name = "添加", index = 0, sn = "CREATE")
	public String operInput() {
		return "oper_input";
	}

	// 添加资源的某个操作
	@Oper
	public String addOper() {
		ActionMethodOper oper = new ActionMethodOper();
		oper.setMethodName(methodName);
		oper.setOperIndex(operIndex);
		oper.setOperName(operName);
		oper.setOperSn(operSn);
		resourceService.addActionResourceOper(model.getId(), oper);

		return "add_success";
	}

	public String getOperSn() {
		return operSn;
	}

	public void setOperSn(String operSn) {
		this.operSn = operSn;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public int getOperIndex() {
		return operIndex;
	}

	public void setOperIndex(int operIndex) {
		this.operIndex = operIndex;
	}

}
