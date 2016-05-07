package cn.zhuqi.oa.web.actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.vo.ProjectAVO;

import com.opensymphony.xwork2.ModelDriven;

@Controller("projectAUpdateAction")
@Scope("prototype")
public class ProjectAUpdateAction extends ProjectAction implements ModelDriven {

	private ProjectAVO projectVO;

	private int userId;

	@Override
	public Object getModel() {
		if (projectVO == null) {
			projectVO = new ProjectAVO();
		}
		userId = currentUser().getId();
		return projectVO;
	}

	/**
	 * 客户代表 update
	 * 
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String update() throws IOException, IllegalAccessException,
			InvocationTargetException {
		// 修改对应属性
		Project project = projectService.findProjectById(projectVO.getId());
		BeanUtils.copyProperties(project, projectVO);
		projectService.updateProject(project);
		// 上传文件
		update_upload(projectVO.getId());
		// 添加操作记录
		int approverId = userId;
		ZActivity activity = activityService.findActivityById(activityId);
		projectService.addApproveInfo("修改【" + activity.getName() + "】",
				activity.getName(), project.getId(), approverId);

		return "update_success";
	}
}
