package cn.zhuqi.oa.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.Activity;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.RoleDao;
import cn.zhuqi.oa.dao.UserDao;
import cn.zhuqi.oa.dao.WorkflowDao;
import cn.zhuqi.oa.dao.ZActivityDao;
import cn.zhuqi.oa.model.Party;
import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.model.TaskInfo;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.model.Workflow;
import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.service.TaskInfoService;
import cn.zhuqi.oa.service.WorkflowService;
import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.system.ActivityUtil;
import cn.zhuqi.system.IOUtil;
import cn.zhuqi.system.SetStringUtil;
import cn.zhuqi.system.TimeUtil;
import cn.zhuqi.system.UserUtil;

@Service("workflowService")
public class WorkflowServiceImpl implements WorkflowService {

	@Resource
	protected ProcessEngine processEngine;
	@Resource
	private RepositoryService repositoryService;

	@Resource
	private ExecutionService executionService;

	@Resource
	private TaskService taskService;

	@Resource
	private WorkflowDao workflowDao;

	@Resource
	private ZActivityDao activityDao;

	@Resource
	private UserDao userDao;

	@Resource
	private RoleDao roleDao;

	@Resource
	private TaskInfoService taskInfoService;

	@Override
	public void deployProcess(ZipInputStream zis) {

		// 发起流程，仅仅就是预定义任务，即在系统中创建一个流程，这是全局的，与具体的登陆 用户无关。然后，在启动流程时，才与登陆用户关联起来
		String deploymentId = repositoryService.createDeployment()
				.addResourcesFromZipInputStream(zis).deploy();

		// System.out.println(processDef);

		// ZipInputStream zis = new ZipInputStream(this.getClass()
		// .getResourceAsStream("/leave2.zip"));
		// 发起流程，仅仅就是预定义任务，即在系统中创建一个流程，这是全局的，与具体的登陆 用户无关。然后，在启动流程时，才与登陆用户关联起来
		// String deploymentId = repositoryService.createDeployment()
		// .addResourcesFromZipInputStream(zis).deploy();

		// 获取InputStream得到定义文件
		// InputStream in = ServletActionContext.getServletContext()
		// .getResourceAsStream(targetFileDir + "/" + processDef);
		// System.out.println(targetFileDir + "/" + processDef);
		// 通过byte[]得到InputStream用于发布流程
		// String deploymentId = this.repositoryService.createDeployment()
		// .addResourceFromInputStream("ptfee", in).deploy();
		// String deploymentId = this.repositoryService.createDeployment()
		// .addResourceFromFile(processDef).deploy();
		System.out.println("发布成功，版本号为:========" + deploymentId);
		// 根据流程ID获取流程定义实例
		ProcessDefinition pdf = repositoryService
				.createProcessDefinitionQuery().deploymentId(deploymentId)
				.uniqueResult();

		if (pdf == null) {
			throw new RuntimeException("流程发布失败1，请查看定义Zip文件是否正确!");
		}

		String resourceName = pdf.getImageResourceName();
		// 获取流程定义图资源
		InputStream is = repositoryService.getResourceAsStream(deploymentId,
				resourceName);
		byte[] processImg = null;
		try {
			processImg = IOUtil.InputStreamTOByte(is);
		} catch (IOException e) {
			throw new RuntimeException("流程发布失败2，请查看定义Zip文件是否正确!");
		}

		String key = pdf.getKey();
		String definitionId = pdf.getId();
		System.out.println("definitionId--------------------->>>"
				+ definitionId);
		// 保存流程
		// 先查找一下该流程是否定义过..
		// name是业务主键
		Workflow workflow = workflowDao.findWorkflowByName(key);
		// 该流程尚不存在
		if (workflow == null) {
			workflow = new Workflow();
			workflow.setName(key);
			workflow.setProcessImage(processImg);
			workflowDao.save(workflow);
			System.out.println("111111111111111111==>" + workflow.getId());
			// 添加activity
			ProcessDefinitionImpl definitionimpl = (ProcessDefinitionImpl) pdf;
			List<? extends Activity> list = definitionimpl.getActivities();
			for (Activity activity : list) {
				String activityName = activity.getName();
				System.out.println(activityName);
				if (ActivityUtil.IsActivity(activityName)) {
					ZActivity zz = new ZActivity();
					zz.setName(activityName);
					activityDao.save(zz);
				}
			}
			return;
		}
		workflow.setName(key);
		// workflow.setProcessImage(processImg);
		workflowDao.update(workflow);
		System.out.println("22222222222222222222==>" + workflow.getId());
		// 更新activity
		// 先删除原先的activity关联,但是原先的activity不能删除
		// TODO @@@@@@@@@@@@@@@@@@@@

		// 根据流程ID获取流程定义实例
		/*
		 * List<ProcessDefinition> pdfs = repositoryService
		 * .createProcessDefinitionQuery().deploymentId(deploymentId) .list();
		 * for (Iterator iter = pdfs.iterator(); iter.hasNext();) {
		 * ProcessDefinition pdf = (ProcessDefinition) iter.next();
		 * System.err.println("不再联系!!---" + pdf.getId() + " " + pdf.getName() +
		 * " " + pdf.getDeploymentId() + " " + pdf.getVersion()); }
		 * 
		 * Deployment dp = repositoryService.createDeploymentQuery()
		 * .deploymentId(deploymentId).uniqueResult();
		 * System.err.println("不再联系!!222" + dp.getId() + " " + dp.getName());
		 */
		/*
		 * if (pdf == null) { throw new
		 * RuntimeException("流程发布失败，请查看定义文件或图片是否正确!"); }
		 * 
		 * // 保存流程 // 先查找一下该流程是否定义过.. =========================>//name是业务主键
		 * Workflow workflow = workflowDao.findWorkflowByName(pdf.getName()); //
		 * 该流程尚不存在 if (workflow == null) { workflow = new Workflow();
		 * workflow.setName(pdf.getName()); //
		 * workflow.setProcessDef(processDef); //
		 * workflow.setProcessImage(processImg); workflowDao.save(workflow);
		 * return; } workflow.setName(pdf.getName()); //
		 * workflow.setProcessDef(processDef); //
		 * workflow.setProcessImage(processImg); workflowDao.update(workflow);
		 */
	}

	@Override
	public Workflow findWorkflowById(int workflowId) {
		return workflowDao.findById(Workflow.class, workflowId);
	}

	@Override
	public void delWorkflow(int workflowId) {
		System.out.println("delWorkflow===>>>>>>>>>>>>>>>>>>>" + workflowId);
		// 在OA系统中删除流程定义
		Workflow workflow = findWorkflowById(workflowId);
		System.out.println(workflow);
		workflowDao.del(workflow);

		// 在JBPM中删除流程定义
		// JBPM对流程定义是按照版本进行管理，所以在同一时刻，可能存在多个不同版本
		// 应查找所有版本，依次删除
		List<ProcessDefinition> pdfs = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey(workflow.getName()).list();
		for (Iterator<ProcessDefinition> iter = pdfs.iterator(); iter.hasNext();) {
			ProcessDefinition pdf = iter.next();
			// 级联删除流程定义及其产生的所有流程实例
			System.out.println(pdf.getDeploymentId());
			// TODO 暂时用的方法是级联删除，注意以后修改(按具体情况而定)... by zhuqi
			repositoryService.deleteDeploymentCascade(pdf.getDeploymentId());
		}
	}

	@Override
	public PagerVO getAllWorkflow() {
		return workflowDao.getAllWorkflow();
	}

	@Override
	public List<Workflow> getAllWorkflowList() {
		return workflowDao.findAll(Workflow.class);
	}

	/**
	 * 客户代表调用此方法
	 */
	@Override
	public String addProcessInstance(String workflowName, int projectId,
			int userId) {

		// 根据流程名称获取流程定义实例
		/*
		 * ProcessDefinition pdf = repositoryService
		 * .createProcessDefinitionQuery()
		 * .processDefinitionName(workflowName).uniqueResult();
		 */
		// 由Key查找最新版本
		/*
		 * ProcessDefinition pdf = repositoryService
		 * .createProcessDefinitionQuery()
		 * .processDefinitionKey(workflowName).uniqueResult();
		 * 
		 * // 将工程绑定到流程实例中 Map map = new HashMap(); // TODO important!!! User
		 * owner = userDao.findById(User.class, userId); map.put("owner",
		 * owner.getUsername()); map.put("project", projectId); ProcessInstance
		 * processInstance = executionService
		 * .startProcessInstanceById(pdf.getId(), map);
		 */

		// 将工程绑定到流程实例中
		Map map = new HashMap();
		// TODO important!!!
		User owner = userDao.findById(User.class, userId);
		map.put("owner", owner.getUsername());
		map.put("project", projectId);
		// modify by zhuqi 2013/12/3
		map.put("username", owner.getPerson().getName());
		map.put("time", TimeUtil.Date2Str(new Date(), "yyyy年M月d日 E HH时mm分"));
		// 查找 key为ICL的最新版本的流程定义， 然后在最新的流程定义里启动流程实例，当部署了一个新版本，
		// startProcessInstanceByKey方法会自动切换到 最新部署的版本）
		ProcessInstance processInstance = executionService
				.startProcessInstanceByKey(workflowName, map);
		return processInstance.getId();
	}

	@Override
	public void delProcessInstance(String processInstanceId) {
		// TODO 暂时是级联删除 -- by zhuqi
		// modify by zhuqi
		ProcessInstance instance = executionService
				.findProcessInstanceById(processInstanceId);
		if (instance != null)
			this.executionService
					.deleteProcessInstanceCascade(processInstanceId);
	}

	/**
	 * List<Task> taskList = new ArrayList<Task>(); TaskQuery taskQuery =
	 * this.taskService.createTaskQuery(); taskQuery.assignee(userId);//单个用户调此方法
	 * // taskQuery.candidate(userId); // 用户组的话调此方法
	 * taskQuery.orderDesc(TaskQuery.PROPERTY_PRIORITY); String total =
	 * taskQuery.count(); // 可以在这里返回总记录数，此方法要在page()方法前调用。
	 * taskQuery.page(firstResult, maxResults); // 分页查询 taskList =
	 * taskQuery.list();
	 */
	@Override
	public List searchApprovingProjects(String username) {
		// TODO 因为要使用JBPM相关的service,因此无法上移到DAO层 ====
		// TODO 由于要使用待办任务的分页显示，故使用JBPM的分页技术，需要和PagerVO相结合==>success
		/*
		 * List<Task> tasks = new ArrayList<Task>(); TaskQuery taskQuery =
		 * this.taskService.createTaskQuery();
		 * taskQuery.assignee(role.getName());// 单个用户调此方法 //
		 * taskQuery.candidate(userId); // 用户组的话调此方法
		 * taskQuery.orderDesc(TaskQuery.PROPERTY_PRIORITY); // int total =
		 * (int) taskQuery.count(); // 可以在这里返回总记录数，此方法要在page()方法前调用。 //
		 * taskQuery.page(SystemContext.getOffset(), //
		 * SystemContext.getPagesize()); // 分页查询 tasks = taskQuery.list();
		 */

		List projIds = new ArrayList();
		// 获得流转到该用户的任务列表.
		List<Task> tasks = taskService.findPersonalTasks(username);
		for (Iterator<Task> it = tasks.iterator(); it.hasNext();) {
			Task task = it.next();
			String taskId = task.getId();
			Set<String> variableNames = taskService.getVariableNames(taskId);
			Map variables = taskService.getVariables(taskId, variableNames);
			Integer projId = (Integer) variables.get("project");
			projIds.add(projId);
		}
		return projIds;

		/*
		 * List<Task> tasks = taskService.findPersonalTasks(username); //
		 * 就是用的userId，username是业务主键 for (Iterator<Task> it = tasks.iterator();
		 * it.hasNext();) { Task task = it.next(); String taskId = task.getId();
		 * Set<String> variableNames = taskService.getVariableNames(taskId); Map
		 * variables = taskService.getVariables(taskId, variableNames); Integer
		 * proId = (Integer) variables.get("project"); }
		 */
	}

	@Override
	public List searchApprovingProjectsByRole(Role role) {
		return searchApprovingProjects(role.getName());

	}

	@Override
	public List searchApprovingProjectsByPosition(Party party) {
		return searchApprovingProjects(party.getName());
	}

	@Override
	public List searchApprovingProjectsByRoles(List<Role> roles) {

		List projIds = new ArrayList();
		for (Iterator<Role> it = roles.iterator(); it.hasNext();) {
			Role role = it.next();
			List _projIds = searchApprovingProjectsByRole(role);
			projIds.addAll(_projIds);
		}
		return projIds;
	}

	/*
	 * 
	 * TODO 可以直接查看图片，不用这么做了吧， 有需求再看吧。。 ==>直接查看图片success
	 * 
	 * @Override public List searchNextTransitions(String processInstanceId) {
	 * //下一步都有哪些流向？ // 获取流程实例 ProcessInstance instance = executionService
	 * .createProcessInstanceQuery()
	 * .processInstanceId(processInstanceId).uniqueResult();
	 * 
	 * // 获取当前节点 Set<String> activityNames = instance.findActiveActivityNames();
	 * 
	 * // 这里不会影响事务 EnvironmentImpl envImpl = ((EnvironmentFactory)
	 * processEngine) .openEnvironment(); try {
	 * 
	 * ExecutionImpl e = (ExecutionImpl) executionService
	 * .findExecutionById(processInstanceId);
	 * 
	 * ActivityImpl clerkOpinionActivityImpl = e.getActivity();
	 * 
	 * List listadd = new ArrayList(); List list =
	 * clerkOpinionActivityImpl.getOutgoingTransitions(); for (Iterator iterator
	 * = list.iterator(); iterator.hasNext();) { Transition ts = (Transition)
	 * iterator.next(); listadd.add(ts.getName()); }
	 * 
	 * return listadd; } catch (Exception e) { e.printStackTrace(); } finally {
	 * envImpl.close(); }
	 * 
	 * return null; }
	 */

	/*
	 * @Override public String flowToNextStep(String username, String
	 * processInstanceId, String result) { User user =
	 * userDao.findByUsername(username); List<Role> roles = user.getMyRoles();
	 * Task task = null; if (UserUtil.IsCSR(user, roleDao)) { // 是客户代表 TODO
	 * 
	 * @@@@@@@@@@@@@@@@@@@@出错了...这里出错了！！！！！！！！！！！！！ task =
	 * taskService.createTaskQuery().assignee(username)
	 * .processInstanceId(processInstanceId).uniqueResult();
	 * taskService.completeTask(task.getId()); } else { // 非客户代表 for
	 * (Iterator<Role> it = roles.iterator(); it.hasNext();) { Role role =
	 * it.next(); task = taskService.createTaskQuery().assignee(role.getName())
	 * .processInstanceId(processInstanceId).uniqueResult(); if (task != null) {
	 * break; } } if (task == null) { task = taskService.createTaskQuery()
	 * .assignee(user.getPerson().getParent().getName())
	 * .processInstanceId(processInstanceId).uniqueResult(); } // TODO
	 * 按照流向确定流程方向，待完成
	 * System.out.println("<<<<<<<<<<<<<<<<<<<<<taskId------------" +
	 * task.getId()); if (result == null) {
	 * taskService.completeTask(task.getId()); } else {
	 * taskService.completeTask(task.getId(), result); }
	 * 
	 * }
	 * 
	 * // 满足实际需求 // task = taskService.createTaskQuery().assignee(username) //
	 * .processInstanceId(processInstanceId).uniqueResult(); /* List<Task> tasks
	 * = taskService.findPersonalTasks("zhuanze"); for (Iterator<Task> it =
	 * tasks.iterator(); it.hasNext();) { Task atask = it.next();
	 * System.out.println(atask.getId());
	 * System.out.println(atask.getActivityName() + "===" + atask.getAssignee()
	 * + "===" + atask.getName());
	 * System.out.println("---------------------------------"); }
	 */
	/*
	 * System.out.println(task); System.out.println(task.getActivityName() +
	 * "===" + task.getAssignee() + "===" + task.getName());
	 * System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTT=>" + task.getId());
	 */

	// 查找流程的下一节点名称
	/*
	 * ProcessInstance processInstance = executionService
	 * .findProcessInstanceById(processInstanceId); if (processInstance == null)
	 * { return Project.STATUS_FINISH; } Set<String> activityNames =
	 * processInstance.findActiveActivityNames();
	 * 
	 * return SetStringUtil.Set2Str(activityNames); }
	 */

	@Override
	public List<Task> getAllTask(String username, String processInstanceId) {
		User user = userDao.findByUsername(username);
		List<Role> roles = user.getMyRoles();
		List<Task> tasks = new ArrayList<Task>();
		// 是客户代表则要增加个人任务
		if (UserUtil.testUser(user, "客户代表")) {
			tasks = taskService.createTaskQuery().assignee(username)
					.processInstanceId(processInstanceId).list();
		}

		for (Iterator<Role> it = roles.iterator(); it.hasNext();) {
			Role role = it.next();
			List<Task> list = taskService.createTaskQuery()
					.assignee(role.getName())
					.processInstanceId(processInstanceId).list();
			if (list.size() != 0) {
				for (Iterator<Task> itt = list.iterator(); itt.hasNext();) {
					Task task = itt.next();
					if (!tasks.contains(task)) {
						tasks.add(task);
					}
				}
			}
		}
		List<Task> list = taskService.createTaskQuery()
				.assignee(user.getPerson().getParent().getName())
				.processInstanceId(processInstanceId).list();
		if (list.size() != 0) {
			for (Iterator<Task> itt = list.iterator(); itt.hasNext();) {
				Task task = itt.next();
				if (!tasks.contains(task)) {
					tasks.add(task);
				}
			}
		}

		return tasks;
	}

	/**
	 * TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!modify by zhuqi --------------2013/12/3
	 * 待实现
	 */
	@Override
	public String flowToNextStep(String taskId, String processInstanceId,
			String result) {
		// 查找流程的下一节点名称 , TODO 不能先执行...！！！！！！！！！！！！！
		// System.out.println("taskId-------------------->" + taskId);
		// Task task = taskService.getTask(taskId);
		// System.out.println(task);
		// System.out.println(task.getExecutionId());
		// String executionId = task.getExecutionId();
		// ProcessInstance processInstance = (ProcessInstance) executionService
		// .findExecutionById(executionId).getProcessInstance();

		if ("驳回".equals(result)) {
			String destActivityName = "工程受理";
			String createTransitionName = "驳回原籍";
			Task task = taskService.getTask(taskId);
			reject(task, destActivityName, createTransitionName);
			return destActivityName;
		}

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("prestatus", ""); // 清空

		if (result == null) {
			// 这里就是所有没有驳回选项的。。。
			// 1.判断参数有无prestatus
			String destActivityName = (String) taskService.getVariable(taskId,
					"prestatus");
			// System.out.println("prestatus=========================---------"
			// + destActivityName);
			// 有，驳回后的提交流程
			if (destActivityName != null && !"".equals(destActivityName)) {
				String createTransitionName = "提交至驳回处";
				Task task = taskService.getTask(taskId);
				createNextTransition(task, destActivityName,
						createTransitionName);
				return destActivityName;
			}
			// 无，正常流程
			taskService.completeTask(taskId, parameters);
		} else {
			taskService.completeTask(taskId, result, parameters);
		}
		// TODO 首先执行必须使用processInstanceId查找
		ProcessInstance processInstance = executionService
				.findProcessInstanceById(processInstanceId);
		if (processInstance == null) {
			return Project.STATUS_FINISH;
		}
		Set<String> activityNames = processInstance.findActiveActivityNames();

		return SetStringUtil.Set2Str(activityNames);
	}

	// edit by zhuqi 2013/10/28/23:36
	/**
	 * 驳回
	 * 
	 * @param task
	 *            当前执行的任务
	 * @param destActivityName
	 *            需要流转到的目的节点
	 * @param createTransitionName
	 *            动态生成的transition的名称
	 */
	protected void createNextTransition(Task task, String destActivityName,
			String createTransitionName) {
		// 这里不会影响事物
		EnvironmentImpl envImpl = ((EnvironmentFactory) processEngine)
				.openEnvironment();
		try {
			ExecutionImpl e = (ExecutionImpl) executionService
					.findExecutionById(task.getExecutionId());

			ActivityImpl clerkOpinionActivityImpl = e.getActivity();

			ProcessDefinitionImpl processDefinitionImpl = clerkOpinionActivityImpl
					.getProcessDefinition();
			ActivityImpl applyActivityImpl = processDefinitionImpl
					.findActivity(destActivityName);
			TransitionImpl toApply = clerkOpinionActivityImpl
					.createOutgoingTransition();
			toApply.setSource(clerkOpinionActivityImpl);
			toApply.setDestination(applyActivityImpl);
			toApply.setName(createTransitionName);
			// TODO 注意与驳回reject的区别
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("prestatus", ""); // 清空
			this.taskService.completeTask(task.getId(), createTransitionName);
			clerkOpinionActivityImpl.removeOutgoingTransition(toApply);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			envImpl.close();
		}
	}

	/**
	 * 驳回
	 * 
	 * @param task
	 *            当前执行的任务
	 * @param destActivityName
	 *            需要流转到的目的节点
	 * @param createTransitionName
	 *            动态生成的transition的名称
	 */
	protected void reject(Task task, String destActivityName,
			String createTransitionName) {
		// 这里不会影响事物
		EnvironmentImpl envImpl = ((EnvironmentFactory) processEngine)
				.openEnvironment();
		try {
			// 动态回退到“窗口收件”
			ExecutionImpl e = (ExecutionImpl) executionService
					.findExecutionById(task.getExecutionId());

			ActivityImpl clerkOpinionActivityImpl = e.getActivity();

			ProcessDefinitionImpl processDefinitionImpl = clerkOpinionActivityImpl
					.getProcessDefinition();
			// 生成一个"经办人意见"——>"窗口收件"的transition
			ActivityImpl applyActivityImpl = processDefinitionImpl
					.findActivity(destActivityName);
			TransitionImpl toApply = clerkOpinionActivityImpl
					.createOutgoingTransition();
			toApply.setSource(clerkOpinionActivityImpl);
			toApply.setDestination(applyActivityImpl);
			toApply.setName(createTransitionName);
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("prestatus", task.getActivityName());
			// modify bu zhuqi 2013/12/3
			parameters.put("", "");
			this.taskService.completeTask(task.getId(), createTransitionName,
					parameters);
			clerkOpinionActivityImpl.removeOutgoingTransition(toApply);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			envImpl.close();
		}
	}

	@Override
	public Workflow findWorkflowByName(String workflowName) {
		return workflowDao.findWorkflowByName(workflowName);
	}

	@Override
	public void update(Workflow workflow) {
		workflowDao.update(workflow);
	}

	@Override
	public String flowToNextStep_final(String username, String taskId,
			String processInstanceId, String result) {
		if ("驳回".equals(result)) {
			String destActivityName = "工程受理";
			String createTransitionName = "驳回原籍";
			Task task = taskService.getTask(taskId);
			createNextTransition3(username, task.getActivityName(), task,
					destActivityName, createTransitionName);
			return destActivityName;
		}

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("prestatus", ""); // 清空
		// modify by zhuqi 2013/12/3
		parameters.put("username", username);
		parameters.put("time",
				TimeUtil.Date2Str(new Date(), "yyyy年M月d日 E HH时mm分"));

		if (result == null) {
			// 这里就是所有没有驳回选项的。。。
			// 1.判断参数有无prestatus
			String destActivityName = (String) taskService.getVariable(taskId,
					"prestatus");
			// 有，驳回后的提交流程
			if (destActivityName != null && !"".equals(destActivityName)) {
				String createTransitionName = "提交至驳回处";
				Task task = taskService.getTask(taskId);
				createNextTransition3(username, "", task, destActivityName,
						createTransitionName);
				return destActivityName;
			}
			// 无，正常流程
			taskService.setVariables(taskId, parameters);
			taskService.completeTask(taskId);
		} else {
			taskService.setVariables(taskId, parameters);
			taskService.completeTask(taskId, result);
		}
		// TODO 首先执行必须使用processInstanceId查找
		ProcessInstance processInstance = executionService
				.findProcessInstanceById(processInstanceId);
		if (processInstance == null) {
			return Project.STATUS_FINISH;
		}
		Set<String> activityNames = processInstance.findActiveActivityNames();
		return SetStringUtil.Set2Str(activityNames);
	}

	@Override
	public String flowToNextStep_final_f(String taskId,
			String processInstanceId, String result, User _from, Project project) {
		String time = TimeUtil.Date2Str(new Date(), "yyyy年M月d日 E HH时mm分");
		if ("驳回".equals(result)) {
			String destActivityName = "工程受理";
			String createTransitionName = "驳回原籍";
			Task task = taskService.getTask(taskId);
			createNextTransition3(_from.getUsername(), task.getActivityName(),
					task, destActivityName, createTransitionName);
			// 添加TaskInfo
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setUfr(_from);
			taskInfo.setProject(project);
			taskInfo.setTime(new Date());
			ZActivity activity = activityDao
					.findActivityByName(destActivityName);
			taskInfo.setActivity(activity);
			taskInfoService.addTaskInfo(taskInfo);

			return destActivityName;
		}

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("prestatus", ""); // 清空
		// modify by zhuqi 2013/12/3
		parameters.put("username", _from.getUsername());
		parameters.put("time",
				TimeUtil.Date2Str(new Date(), "yyyy年M月d日 E HH时mm分"));

		// 在未完成任务之前先查一下当前活动任务...

		ProcessInstance originalProcessInstance = executionService
				.findProcessInstanceById(processInstanceId);
		Set<String> originalActivities = originalProcessInstance
				.findActiveActivityNames();

		if (result == null) {
			// 这里就是所有没有驳回选项的。。。
			// 1.判断参数有无prestatus
			String destActivityName = (String) taskService.getVariable(taskId,
					"prestatus");
			// 有，驳回后的提交流程
			if (destActivityName != null && !"".equals(destActivityName)) {
				String createTransitionName = "提交至驳回处";
				Task task = taskService.getTask(taskId);
				createNextTransition3(_from.getUsername(), "", task,
						destActivityName, createTransitionName);

				// 添加TaskInfo
				TaskInfo taskInfo = new TaskInfo();
				taskInfo.setUfr(_from);
				taskInfo.setProject(project);
				taskInfo.setTime(new Date());
				ZActivity activity = activityDao
						.findActivityByName(destActivityName);
				taskInfo.setActivity(activity);
				taskInfoService.addTaskInfo(taskInfo);

				return destActivityName;
			}
			// 无，正常流程
			taskService.setVariables(taskId, parameters);
			taskService.completeTask(taskId);
		} else {
			taskService.setVariables(taskId, parameters);
			taskService.completeTask(taskId, result);
		}
		// TODO 首先执行必须使用processInstanceId查找
		ProcessInstance processInstance = executionService
				.findProcessInstanceById(processInstanceId);
		if (processInstance == null) {
			return Project.STATUS_FINISH;
		}
		Set<String> activityNames = processInstance.findActiveActivityNames();

		for (String activityName : activityNames) {
			// 只有这里可能多添加TaskInfo
			// 只有原先没有的Activity才可能是出现的新任务。。。。
			if (!originalActivities.contains(activityName)) {
				TaskInfo taskInfo = new TaskInfo();
				taskInfo.setUfr(_from);
				taskInfo.setProject(project);
				taskInfo.setTime(new Date());
				ZActivity activity = activityDao
						.findActivityByName(activityName);
				taskInfo.setActivity(activity);
				taskInfoService.addTaskInfo(taskInfo);
			}

		}
		return SetStringUtil.Set2Str(activityNames);
	}

	// modify by zhuqi 2013/12/3
	/**
	 * 建立新路径
	 * 
	 * @param username
	 * @param prestatus
	 * @param task
	 * @param destActivityName
	 * @param createTransitionName
	 */
	protected void createNextTransition3(String username, String prestatus,
			Task task, String destActivityName, String createTransitionName) {
		EnvironmentImpl envImpl = ((EnvironmentFactory) processEngine)
				.openEnvironment();
		try {
			ExecutionImpl e = (ExecutionImpl) executionService
					.findExecutionById(task.getExecutionId());
			ActivityImpl clerkOpinionActivityImpl = e.getActivity();
			ProcessDefinitionImpl processDefinitionImpl = clerkOpinionActivityImpl
					.getProcessDefinition();
			ActivityImpl applyActivityImpl = processDefinitionImpl
					.findActivity(destActivityName);
			TransitionImpl toApply = clerkOpinionActivityImpl
					.createOutgoingTransition();
			toApply.setSource(clerkOpinionActivityImpl);
			toApply.setDestination(applyActivityImpl);
			toApply.setName(createTransitionName);
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("prestatus", prestatus);
			parameters.put("username", username);
			parameters.put("time",
					TimeUtil.Date2Str(new Date(), "yyyy年M月d日 E HH时mm分"));
			this.taskService.completeTask(task.getId(), createTransitionName,
					parameters);
			clerkOpinionActivityImpl.removeOutgoingTransition(toApply);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			envImpl.close();
		}
	}
}
