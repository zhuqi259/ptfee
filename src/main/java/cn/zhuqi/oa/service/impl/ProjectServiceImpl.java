package cn.zhuqi.oa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessInstance;
import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.FundDao;
import cn.zhuqi.oa.dao.PaymentDao;
import cn.zhuqi.oa.dao.PfileDao;
import cn.zhuqi.oa.dao.ProjectDao;
import cn.zhuqi.oa.dao.RoleDao;
import cn.zhuqi.oa.dao.TaskInfoDao;
import cn.zhuqi.oa.dao.UserDao;
import cn.zhuqi.oa.dao.ZActivityDao;
import cn.zhuqi.oa.model.ApproveInfo;
import cn.zhuqi.oa.model.Fund;
import cn.zhuqi.oa.model.Party;
import cn.zhuqi.oa.model.Payment;
import cn.zhuqi.oa.model.Pfile;
import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.model.TaskInfo;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.service.ProjectService;
import cn.zhuqi.oa.service.WorkflowService;
import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.system.SetStringUtil;
import cn.zhuqi.system.TimeUtil;
import cn.zhuqi.system.UserUtil;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

	@Resource
	private WorkflowService workflowService;

	@Resource
	private ExecutionService executionService;

	@Resource
	private UserDao userDao;

	@Resource
	private RoleDao roleDao;

	@Resource
	private ProjectDao projectDao;

	@Resource
	private PfileDao pfileDao;

	@Resource
	private PaymentDao paymentDao;

	@Resource
	private FundDao fundDao;

	@Resource
	private ZActivityDao activityDao;

	@Resource
	private TaskInfoDao taskInfoDao;

	@Override
	public List getMyUsedFiles(int projectId) {
		return projectDao.getMyUsedFiles(projectId);
	}

	// //这些都是对工程的管理 CRUD

	@Override
	public void addProject(Project project, int workflowId, int userId) {
		// TODO 2013.6.26要实现的。。 6.27 by zhuqi

		// 保存公文信息 提取到DAO层
		/*
		 * Workflow workflow = workflowDao.findById(Workflow.class, workflowId);
		 * User creator = userDao.findById(User.class, userId);
		 * project.setWorkflow(workflow); project.setCreator(creator);
		 * project.setCreateTime(new Date());
		 * project.setStatus(Project.STATUS_NEW); projectDao.save(project);
		 */
		if (workflowId == 0) {
			throw new RuntimeException("没有添加工作流，请联系管理员!!");
		}
		projectDao.addProject(project, workflowId, userId);

		System.out.println("addProject==>projectid--------------------"
				+ project.getId());

		// 添加流程实例
		String processInstanceId = workflowService.addProcessInstance(project
				.getWorkflow().getName(), project.getId(), userId);
		System.out.println("addProject==>processInstanceId--------------------"
				+ processInstanceId);

		// 更新工程的状态
		ProcessInstance processInstance = executionService
				.findProcessInstanceById(processInstanceId);
		Set<String> activityNames = processInstance.findActiveActivityNames();
		String status = SetStringUtil.Set2Str(activityNames);
		project.setStatus(status);

		String time = TimeUtil.Date2Str(new Date(), "yyyy年M月d日 E HH时mm分");
		User _from = userDao.findById(User.class, userId);
		for (String activityName : activityNames) {
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setUfr(_from);
			taskInfo.setProject(project);
			taskInfo.setTime(new Date());
			ZActivity activity = activityDao.findActivityByName(activityName);
			taskInfo.setActivity(activity);
			taskInfoDao.save(taskInfo);
		}

		// 绑定流程实例到工程
		project.setProcessInstanceId(processInstanceId);
		updateProject(project);

	}

	@Override
	public void updateProject(Project project) {
		projectDao.update(project);
	}

	@Override
	public void delProject(int projectId) {

		Project project = findProjectById(projectId);
		// 删除流程实例
		workflowService.delProcessInstance(project.getProcessInstanceId());
		// 删除approveInfo
		List<ApproveInfo> infos = getAllApproveInfos(projectId);
		for (ApproveInfo info : infos) {
			projectDao.del(info);
		}
		// 删除文件
		List<Pfile> files = getMyUsedFiles(projectId);
		for (Pfile file : files) {
			// TODO $$$$$$$$$$$$$$$$$$$$$$$$$$$$删除具体文件
			pfileDao.del(file);
		}
		// 删除工程信息
		projectDao.del(project);

	}

	// 这些是对工程审批的管理

	@Override
	public Project findProjectById(int projectId) {
		return projectDao.findById(Project.class, projectId);
	}

	@Override
	public Project getProjectById(int projectId) {
		return projectDao.getById(Project.class, projectId);
	}

	@Override
	public PagerVO searchMyProjects(int userId, String projectName) {
		User user = userDao.findById(User.class, userId);
		// 判断是否是客户代表（包括职位和角色），直接查找(当然也可以使用审批记录查找，但是在工程受理阶段的录入工程后无法找到此工程，因为此时还没有审批记录)
		if (UserUtil.IsCSR(user, roleDao)) {
			return projectDao.searchMyProjects(userId, projectName);
		}
		// 不是客户代表,按照审批记录来查找
		List<Integer> projIds = projectDao.getUserProjectByApprove(userId);
		return projectDao.findProjectByIds(projIds, projectName);
	}

	@Override
	public PagerVO searchApproveInfos(int projectId) {
		return projectDao.searchApproveInfos(projectId);
	}

	@Override
	public PagerVO searchApprovingProjects(int userId, String projectName) {

		User user = userDao.findById(User.class, userId);
		List projIds = new ArrayList();
		List<Role> roles = user.getMyRoles();
		Party party = user.getPerson().getParent();
		// 分情况讨论，是客户代表就是查找个人任务，否则就是查找角色任务和职位任务
		if (UserUtil.IsCSR(user, roleDao)) {
			projIds.addAll(workflowService.searchApprovingProjects(user
					.getUsername()));
		}

		// 搜索已流转到该用户所在角色的工程标识列表
		projIds.addAll(workflowService.searchApprovingProjectsByRoles(roles));

		// 搜索已流转到该用户所在职位的工程标识列表
		if (party != null) {
			projIds.addAll(workflowService
					.searchApprovingProjectsByPosition(party));
		}

		if (projIds == null || projIds.isEmpty()) {
			PagerVO pv = new PagerVO();
			pv.setDatas(projIds);
			pv.setTotal(0);
			return pv;
		}

		return projectDao.findProjectByIds(projIds, projectName);
	}

	@Override
	public PagerVO searchApprovedProjects(int userId, String sSearch) {
		return projectDao.searchApprovedProjects(userId, sSearch);
	}

	// 审批
	@Override
	public void addApproveInfo(String approveComment, String oldStatus,
			int projectId, int approverId) {
		ApproveInfo approveInfo = new ApproveInfo();
		// 关联工程
		Project project = projectDao.findById(Project.class, projectId);

		System.out.println("PPPPPPPPPPPPPPPPPPPPPPP===>" + project);
		approveInfo.setProject(project);
		// 关联用户
		User approver = userDao.findById(User.class, approverId);
		approveInfo.setApprover(approver);
		// 历史工程状态
		approveInfo.setStatus(oldStatus);
		// 审批意见
		approveInfo.setComment(approveComment);
		// 审批时间
		approveInfo.setApproveTime(new Date());

		projectDao.save(approveInfo);
	}

	@Override
	public PagerVO getAllProjects(String projectName) {
		return projectDao.getAllProjects(projectName);
	}

	@Override
	public ApproveInfo getApprove(int projectId, String status) {
		return projectDao.getApprove(projectId, status);
	}

	@Override
	public List<ApproveInfo> getAllApproveInfos(int projectId) {
		return projectDao.getAllApproveInfos(projectId);
	}

	@Override
	public List<Project> getAllProjectByToday() {
		return projectDao.getAllProjectByToday();
	}

	@Override
	public void delRealProject(int projectId) {

		Project project = getProjectById(projectId);
		if (project == null) {
			return;
		}
		// 删除流程实例
		if (!Project.STATUS_FINISH.equals(project.getStatus())) {
			workflowService.delProcessInstance(project.getProcessInstanceId());
		}

		// 删除approveInfo
		List<ApproveInfo> infos = getAllApproveInfos(projectId);
		for (ApproveInfo info : infos) {
			projectDao.del(info);
		}
		// 删除文件
		List<Pfile> files = getMyUsedFiles(projectId);
		for (Pfile file : files) {
			// TODO $$$$$$$$$$$$$$$$$$$$$$$$$$$$删除具体文件
			pfileDao.del(file);
		}

		// 删除t_payment
		List<Payment> payments = paymentDao.getAllProjectPayment(projectId);
		for (Payment payment : payments) {
			paymentDao.del(payment);
		}

		// 删除fund
		List<Fund> funds = fundDao.getAllProjectFund(projectId);
		for (Fund fund : funds) {
			fundDao.del(fund);
		}

		// 删除taskInfo
		List<TaskInfo> taskInfos = taskInfoDao
				.findAllProjectTaskInfos(projectId);
		for (TaskInfo taskInfo : taskInfos) {
			taskInfoDao.del(taskInfo);
		}

		// 删除工程信息
		projectDao.del(project);
	}

	@Override
	public List<Project> getAllProjectByTest() {
		return projectDao.findAll(Project.class);
	}

	@Override
	public PagerVO searchApprovingProjects2(int userId) {
		User user = userDao.findById(User.class, userId);
		List projIds = new ArrayList();
		List<Role> roles = user.getMyRoles();
		Party party = user.getPerson().getParent();
		// 分情况讨论，是客户代表就是查找个人任务，否则就是查找角色任务和职位任务
		if (UserUtil.IsCSR(user, roleDao)) {
			projIds.addAll(workflowService.searchApprovingProjects(user
					.getUsername()));
		}

		// 搜索已流转到该用户所在角色的工程标识列表
		projIds.addAll(workflowService.searchApprovingProjectsByRoles(roles));

		// 搜索已流转到该用户所在职位的工程标识列表
		if (party != null) {
			projIds.addAll(workflowService
					.searchApprovingProjectsByPosition(party));
		}

		if (projIds == null || projIds.isEmpty()) {
			PagerVO pv = new PagerVO();
			pv.setDatas(projIds);
			pv.setTotal(0);
			return pv;
		}

		return projectDao.findProjectByIds2(projIds);
	}

}
