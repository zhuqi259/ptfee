package cn.zhuqi.oa.web.actions;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.vo.ProjectBVO;

import com.opensymphony.xwork2.ModelDriven;

@Controller("projectBUpdateAction")
@Scope("prototype")
public class ProjectBUpdateAction extends ProjectAction implements ModelDriven {

	private ProjectBVO projectVO;

	private int userId;

	@Override
	public Object getModel() {
		if (projectVO == null) {
			projectVO = new ProjectBVO();
		}
		userId = currentUser().getId();
		return projectVO;
	}

	public String updateFid() {
		System.out.println("id->>>>>>>>>>>>>>>>" + projectVO.getId());
		Project project = projectService.findProjectById(projectVO.getId());
		try {
			BeanUtils.copyProperties(project, projectVO);
		} catch (Exception e) {
			throw new RuntimeException("分配工程编号出错，请联系管理员。");
		}

		// 添加操作记录
		int approverId = userId;
		ZActivity activity = activityService.findActivityById(activityId);
		projectService.addApproveInfo("修改【" + activity.getName() + "】",
				activity.getName(), project.getId(), approverId);

		return "update_success";
	}

}
