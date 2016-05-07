package cn.zhuqi.oa.web.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.service.ZActivityService;
import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.oa.web.JSONUtils;

import com.opensymphony.xwork2.ModelDriven;

@Controller("zactivityAction")
@Scope("prototype")
public class ZActivityAction extends BaseAction implements ModelDriven {

	@Resource
	private ZActivityService activityService;

	private ZActivity activity;

	@Override
	public Object getModel() {
		if (activity == null) {
			activity = new ZActivity();
		}
		return activity;
	}

	public String execute() {
		return "index";
	}

	public void activityList() {
		List activities = activityService.findAllActivity(sSearch);
		Map map = new HashMap();
		map.put("aaData", activities);
		JSONUtils.toJSON(map);
	}

	public void activityPage() {
		Map map = new HashMap();

		PagerVO pv = activityService.findAllZActivity(sSearch);

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	public String updateInput() {
		activity = activityService.findActivityById(activity.getId());
		return "update_input";
	}

	public String updatePaymentInput() {
		activity = activityService.findActivityById(activity.getId());
		return "update_input";
	}

	public String update() {
		activityService.update(activity);
		return "update_success";
	}

	public void allFiles() {
		Map map = new HashMap();

		PagerVO pv = activityService.findAllFiles(activity.getId());

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	public void allPayments() {
		Map map = new HashMap();

		PagerVO pv = activityService.findAllPayments(activity.getId());

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	public void del() {
		throw new RuntimeException("节点下存在文件，无法删除!!");
	}

	
	/// modify by zhuqi 2013/12/01
	public String updateZfundInput() {
		activity = activityService.findActivityById(activity.getId());
		return "update_input";
	}
	
	public void allFunds() {
		Map map = new HashMap();

		PagerVO pv = activityService.findAllFunds(activity.getId());

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

}
