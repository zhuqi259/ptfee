package cn.zhuqi.oa.web.actions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.model.ZFKpercentum;
import cn.zhuqi.oa.service.ZActivityService;
import cn.zhuqi.oa.service.ZFKpercentumService;
import cn.zhuqi.system.EActivity;

@Controller("zpercentumAction")
@Scope("prototype")
public class ZpercentumAction extends BaseAction {

	@Resource
	private ZFKpercentumService zfKpercentumService;

	@Resource
	private ZActivityService activityService;

	private List<ZActivity> activities;

	private Integer[] percentum;

	public Integer[] getPercentum() {
		return percentum;
	}

	public void setPercentum(Integer[] percentum) {
		this.percentum = percentum;
	}

	/**
	 * 判断是否是正式工程付款
	 * 
	 * @param str
	 * @return
	 */
	private boolean IsZFK(String str) {
		return str.equals(EActivity.JK30.getName())
				|| str.equals(EActivity.JK60.getName())
				|| str.equals(EActivity.JK80.getName())
				|| str.equals(EActivity.JK15.getName())
				|| str.equals(EActivity.SGJK5.getName());
	}

	public String execute() {
		return "index";
	}

	public List<ZActivity> getAllZFKActivities() {
		if (activities == null) {
			activities = new ArrayList<ZActivity>();
			List<ZActivity> ats = activityService.findAllActivityByOrder();
			for (ZActivity activity : ats) {
				if (IsZFK(activity.getName())) {
					activities.add(activity);
				}
			}
		}
		return activities;
	}

	public String update() {
		List<ZActivity> ats = getAllZFKActivities();
		int sum = 0;
		for (int i = 0; i < ats.size(); i++) {
			sum += percentum[i];
		}
		if (sum != 100) {
			throw new RuntimeException("设置比例出错,总和是100%,请查看!!!");
		}

		for (int i = 0; i < ats.size(); i++) {
			ZActivity activity = ats.get(i);
			ZFKpercentum zFKpercentum = zfKpercentumService
					.findZFKpercentumByActivity(activity.getId());
			if (zFKpercentum == null) {
				zFKpercentum = new ZFKpercentum();
				zFKpercentum.setNum(percentum[i]);
				zFKpercentum.setActivity(activity);
				zfKpercentumService.addZpercentum(zFKpercentum);
			} else {
				zFKpercentum.setNum(percentum[i]);
				zfKpercentumService.updateZpercentum(zFKpercentum);
			}
		}
		return "update_success";
	}

	public Integer getPercentum(Integer activityId) {
		ZFKpercentum zFKpercentum = zfKpercentumService
				.findZFKpercentumByActivity(activityId);
		return zFKpercentum.getNum();
	}

}
