package cn.zhuqi.oa.web.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.ApproveInfo;
import cn.zhuqi.oa.model.Menu;
import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.model.TaskInfo;
import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.service.AclService;
import cn.zhuqi.oa.service.MessageService;
import cn.zhuqi.oa.service.ProjectService;
import cn.zhuqi.oa.service.TaskInfoService;
import cn.zhuqi.oa.service.UserService;
import cn.zhuqi.oa.service.WorkflowService;
import cn.zhuqi.oa.service.ZActivityService;
import cn.zhuqi.oa.vo.AuthMenuTreeVO;
import cn.zhuqi.oa.vo.LoginInfoVO;
import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.oa.vo.ProjectVO;
import cn.zhuqi.oa.vo.TaskVO;
import cn.zhuqi.oa.web.JSONUtils;
import cn.zhuqi.system.MessageUtil;
import cn.zhuqi.system.TimeUtil;

/**
 * 用来处理登录之后，主界面上的各种请求
 * 
 * @author Lee
 * 
 */
@Controller("indexAction")
@Scope("prototype")
public class IndexAction extends BaseAction {

	@Resource
	private AclService aclService;

	@Resource
	private UserService userService;

	@Resource
	protected ProjectService projectService;

	@Resource
	protected MessageService messageService;

	@Resource
	protected WorkflowService workflowService;

	@Resource
	protected TaskService taskService;

	@Resource
	protected TaskInfoService taskInfoService;

	@Resource
	protected ZActivityService activityService;

	// 根据当前登录用户，查询其拥有访问权限的菜单
	public void menu() {
		LoginInfoVO user = currentUser();
		// modify by zhuqi
		int count = 0;
		if (!user.getUsername().equals("admin")) {
			// 不是管理员
			count = projectService.searchApprovingProjects(user.getId(), "")
					.getTotal();
			System.out.println("count=============" + count);
			MessageUtil.sendRemind(userService, messageService, user.getId(),
					count);
		}
		List<Menu> authMenus = aclService.findPermitMenus(user.getId());
		List<AuthMenuTreeVO> vos = new ArrayList<AuthMenuTreeVO>();
		if (count > 0) {
			for (Menu m : authMenus) {
				AuthMenuTreeVO vo = new AuthMenuTreeVO(m, count);
				vos.add(vo);
			}
		} else {
			for (Menu m : authMenus) {
				AuthMenuTreeVO vo = new AuthMenuTreeVO(m);
				vos.add(vo);
			}
		}

		JSONUtils.toJSON(vos);
	}

	// private void count2() throws IOException {
	// LoginInfoVO user = currentUser();
	// // modify by zhuqi
	// int count = 0;
	// List data = new ArrayList();
	// List datas = new ArrayList();
	// if (!user.getUsername().equals("admin")) {
	// // 不是管理员
	// PagerVO vo2 = projectService.searchApprovingProjects2(user.getId());
	// count = vo2.getTotal();
	// data = vo2.getDatas();
	// MessageUtil.sendRemind(userService, messageService, user.getId(),
	// count);
	// for (Object obj : data) {
	// ProjectVO projectVO = (ProjectVO) obj;
	// Integer proId = projectVO.getId();
	// List<TaskVO> vos = new ArrayList<TaskVO>();
	// Project project = projectService.findProjectById(proId);
	// List<Task> tasks = workflowService.getAllTask(
	// user.getUsername(), project.getProcessInstanceId());
	// Map<String, Object> _map = new HashMap<String, Object>();
	// for (Task task : tasks) {
	// TaskVO vo = new TaskVO();
	// String taskId = task.getId();
	// System.out.println("taskId------------------" + taskId);
	// vo.setId(taskId);
	// vo.setTaskname(task.getActivityName());
	// String username = (String) taskService.getVariable(taskId,
	// "username");
	// String time = (String) taskService.getVariable(taskId,
	// "time");
	//
	// vo.setUsername(username);
	// vo.setTime(time);
	// vos.add(vo);
	// }
	// _map.put("project", obj);
	// _map.put("data", vos);
	// datas.add(_map);
	// }
	// }
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("count", count);
	// map.put("datas", datas);
	// JSONUtils.toJSON(map);
	// }
	//
	public void count() throws IOException {
		LoginInfoVO user = currentUser();
		// modify by zhuqi
		int count = 0;
		List data = new ArrayList();
		List datas = new ArrayList();
		if (!user.getUsername().equals("admin")) {
			// 不是管理员
			PagerVO vo2 = projectService.searchApprovingProjects2(user.getId());
			count = vo2.getTotal();
			data = vo2.getDatas();
			MessageUtil.sendRemind(userService, messageService, user.getId(),
					count);
			for (Object obj : data) {
				ProjectVO projectVO = (ProjectVO) obj;
				Integer proId = projectVO.getId();
				List<TaskVO> vos = new ArrayList<TaskVO>();
				Project project = projectService.findProjectById(proId);
				List<Task> tasks = workflowService.getAllTask(
						user.getUsername(), project.getProcessInstanceId());
				Map<String, Object> _map = new HashMap<String, Object>();
				for (Task task : tasks) {
					TaskVO vo = new TaskVO();
					String taskId = task.getId();
					System.out.println("taskId------------------" + taskId);
					vo.setId(taskId);
					String activityName = task.getActivityName();
					vo.setTaskname(activityName);
					ZActivity activity = activityService
							.findActivityByName(activityName);
					TaskInfo taskInfo = taskInfoService.getPAUTaskInfo(proId,
							activity.getId());
					try {
						vo.setUsername(taskInfo.getUfr().getPerson().getName());
						vo.setTime(TimeUtil.Date2Str(taskInfo.getTime(),
								"yyyy年M月d日 E HH时mm分"));
					} catch (Exception e) {
						vo.setUsername("未知...");
						vo.setTime("未知...");
					}
					vos.add(vo);
				}
				_map.put("project", obj);
				_map.put("data", vos);
				datas.add(_map);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("datas", datas);
		JSONUtils.toJSON(map);
	}

}
