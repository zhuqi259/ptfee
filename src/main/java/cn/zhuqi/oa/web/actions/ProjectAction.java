package cn.zhuqi.oa.web.actions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ActivityCoordinatesImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.excel.GenerateExcel;
import cn.zhuqi.oa.model.ApproveInfo;
import cn.zhuqi.oa.model.Database;
import cn.zhuqi.oa.model.Fund;
import cn.zhuqi.oa.model.Payment;
import cn.zhuqi.oa.model.Pfile;
import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.model.TaskInfo;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.model.Workflow;
import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.model.ZFKpercentum;
import cn.zhuqi.oa.model.ZPayment;
import cn.zhuqi.oa.model.Zfile;
import cn.zhuqi.oa.model.Zfund;
import cn.zhuqi.oa.service.DatabaseService;
import cn.zhuqi.oa.service.FundService;
import cn.zhuqi.oa.service.IdTableService;
import cn.zhuqi.oa.service.PaymentService;
import cn.zhuqi.oa.service.PfileService;
import cn.zhuqi.oa.service.ProjectService;
import cn.zhuqi.oa.service.RuleService;
import cn.zhuqi.oa.service.UserService;
import cn.zhuqi.oa.service.WorkflowService;
import cn.zhuqi.oa.service.ZActivityService;
import cn.zhuqi.oa.service.ZFKpercentumService;
import cn.zhuqi.oa.service.ZSequenceService;
import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.oa.web.JSONUtils;
import cn.zhuqi.system.EActivity;
import cn.zhuqi.system.FileNameUtil;
import cn.zhuqi.system.FileUtil;
import cn.zhuqi.system.IdUtil;
import cn.zhuqi.system.MulUtil;
import cn.zhuqi.system.SetStringUtil;
import cn.zhuqi.system.TimeUtil;
import cn.zhuqi.system.UserUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("projectAction")
@Scope("prototype")
public class ProjectAction extends BaseAction implements ModelDriven {

	@Resource
	protected ProjectService projectService;

	@Resource
	protected WorkflowService workflowService;

	@Resource
	protected IdTableService idTableService;

	@Resource
	protected RuleService ruleService;

	@Resource
	protected UserService userService;

	@Resource
	protected ZActivityService activityService;

	@Resource
	protected DatabaseService databaseService;

	@Resource
	protected PfileService pfileService;

	@Resource
	protected PaymentService paymentService;

	// @Resource
	// protected ZPaymentService zPaymentService;

	@Resource
	protected RepositoryService repositoryService;

	@Resource
	protected ExecutionService executionService;

	@Resource
	protected TaskService taskService;

	private Project project;

	private List<ActivityCoordinates> acList;

	private String currentStatus;

	private Set<String> myStatus;

	private List<Task> myTask;

	private int userId;

	private int workflowId;

	private String comment;

	private String result;

	private String tid;

	protected int activityId;

	private List<Zfile> zfiles;

	private List<ZPayment> zpayments;

	public List<ZPayment> getZpayments() {
		return zpayments;
	}

	public void setZpayments(List<ZPayment> zpayments) {
		this.zpayments = zpayments;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public List<Zfile> getZfiles() {
		return zfiles;
	}

	public void setZfiles(List<Zfile> zfiles) {
		this.zfiles = zfiles;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	// /////////////////////////////////////////////////////////////////////////
	// TODO 文件管理部分
	protected File[] uploadFiles;
	protected String[] uploadFilesFileName;
	protected String[] uploadFilesContentType;
	protected String[] targetFileNames;
	protected String targetFileDir;
	protected int filesCount;
	protected String[] fileUsed;

	public File[] getUploadFiles() {
		return uploadFiles;
	}

	public void setUploadFiles(File[] uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	public String[] getUploadFilesFileName() {
		return uploadFilesFileName;
	}

	public void setUploadFilesFileName(String[] uploadFilesFileName) {
		this.uploadFilesFileName = uploadFilesFileName;
	}

	public String[] getUploadFilesContentType() {
		return uploadFilesContentType;
	}

	public void setUploadFilesContentType(String[] uploadFilesContentType) {
		this.uploadFilesContentType = uploadFilesContentType;
	}

	public String[] getTargetFileNames() {
		return targetFileNames;
	}

	public void setTargetFileNames(String[] targetFileNames) {
		this.targetFileNames = targetFileNames;
	}

	public String getTargetFileDir() {
		return targetFileDir;
	}

	public void setTargetFileDir(String targetFileDir) {
		this.targetFileDir = targetFileDir;
	}

	public int getFilesCount() {
		return filesCount;
	}

	public void setFilesCount(int filesCount) {
		this.filesCount = filesCount;
	}

	public String[] getFileUsed() {
		return fileUsed;
	}

	public void setFileUsed(String[] fileUsed) {
		this.fileUsed = fileUsed;
	}

	// /////////////////////////////////////////////////////////////////////////

	@Override
	public Object getModel() {
		if (project == null) {
			project = new Project();
		}
		userId = currentUser().getId();
		return project;
	}

	public int getUserId() {
		return userId;
	}

	public List<ActivityCoordinates> getAcList() {
		return acList;
	}

	public void setAcList(List<ActivityCoordinates> acList) {
		this.acList = acList;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Set<String> getMyStatus() {
		return myStatus;
	}

	public void setMyStatus(Set<String> myStatus) {
		this.myStatus = myStatus;
	}

	public List<Task> getMyTask() {
		return myTask;
	}

	public void setMyTask(List<Task> myTask) {
		this.myTask = myTask;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String execute() {
		return "index";
	}

	/**
	 * 主页面上用于显示工程列表
	 */
	public void list() {

		Map map = new HashMap();

		PagerVO pv = projectService.searchMyProjects(userId, sSearch);

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);

	}

	/**
	 * 已审核工程列表
	 */
	public void approvedList() {
		Map map = new HashMap();
		// System.out.println("-----------offset------------"
		// + SystemContext.getOffset());
		// System.out.println("-----sSearch-----------" + sSearch);
		PagerVO pv = projectService.searchApprovedProjects(userId, sSearch);

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	/**
	 * 待审核工程列表
	 */
	public void approvingList() {
		Map map = new HashMap();

		PagerVO pv = projectService.searchApprovingProjects(userId, sSearch);

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	public void allProjects() {
		Map map = new HashMap();

		PagerVO pv = projectService.getAllProjects(sSearch);

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	/**
	 * 对于待审核工程，打开审核界面
	 */
	public String approveInput() {
		// 首先找到该工程的详细信息
		project = projectService.findProjectById(project.getId());
		myStatus = SetStringUtil.Str2Set(project.getStatus());
		String username = currentUser().getUsername();
		myTask = workflowService.getAllTask(username,
				project.getProcessInstanceId());
		// System.out.println("----------size:" + myTask.size());
		// for (Iterator<Task> it = myTask.iterator(); it.hasNext();) {
		// Task task = it.next();
		// System.out.println(task.getActivityName() + "----" + task.getId());
		// }
		ActionContext.getContext().put("projectId", project.getId());
		return "approve_input";
	}

	public String approveInput2() {
		// 首先找到该工程的详细信息
		project = projectService.findProjectById(project.getId());
		Task task = taskService.getTask(tid);
		currentStatus = task.getActivityName();
		return "approve_input2";
	}

	/**
	 * 审核工程,此时只是添加了审批信息，没有点提交按钮，就不会流入下一个节点 TODO
	 * 有可能要实现多个流向,暂时不考虑。但要注意点击提交按钮完成审批，流向下一个节点.
	 */
	public String approve() {
		// 审批该工程
		p_approve();
		return "approve_success";
	}

	public String history() {
		project = projectService.findProjectById(project.getId());
		return "history";
	}

	/**
	 * 查看工程的审批历史
	 */
	public void approvedHistory() {

		Map map = new HashMap();

		// System.out.println("approvedHistory>>>>>>>>>>>>>>>" +
		// project.getId());

		PagerVO pv = projectService.searchApproveInfos(project.getId());

		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);
	}

	/*
	 * public String submit() { String username = currentUser().getUsername();
	 * String processInstanceId = project.getProcessInstanceId();
	 * workflowService.flowToNextStep(username, processInstanceId); return
	 * "submit_success"; }
	 */

	/**
	 * 可能有很多的流程定义在那，在客户代表录入工程信息前应该可以选择执行定义的哪种流程
	 */
	public void selectFlow() {

		PagerVO pv = workflowService.getAllWorkflow();
		Map map = new HashMap();
		map.put("iTotalRecords", pv.getTotal());
		map.put("iTotalDisplayRecords", pv.getTotal());
		map.put("aaData", pv.getDatas());
		JSONUtils.toJSON(map);

	}

	public String addInput() {
		// TODO 注意添加必要的操作
		// 查找出所有的工作流出来，以便选择
		List<Workflow> workflows = workflowService.getAllWorkflowList();
		ActionContext.getContext().put("workflows", workflows);

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);

		return "add_input";
	}

	// 判断workflowId是否在所属的selectedworkflowIds列表中
	public String hasSelected(int workflowId, List<Integer> selectedworkflowIds) {
		if (selectedworkflowIds == null) {
			return "";
		}
		for (Integer i : selectedworkflowIds) {
			if (i.equals(workflowId)) {
				return "selected";
			}
		}
		return "";
	}

	public boolean IsZhuanze(int userId) {
		User user = userService.findUserById(userId);
		// TODO ~~~~~~~~~~这里应该还得添加角色的判断--------------2013-7-6!!!!未解决
		/*
		 * String name = null; try { name =
		 * user.getPerson().getParent().getName(); } catch (Exception e) {
		 * return false; } // TODO modify by zhuqi 2013-8-20 if
		 * ("市场及大客户部专责".equals(name)) { return true; } else { List<Role> roles =
		 * user.getMyRoles(); for (Role role : roles) { if
		 * ("市场及大客户部专责".equals(role.getName())) { return true; } } } return
		 * false;
		 */
		return UserUtil.testUser(user, "市场及大客户服务部专责");
	}

	public boolean IsCSR(int userId) {
		User user = userService.findUserById(userId);
		// TODO ~~~~~~~~~~这里应该还得添加角色的判断--------------2013-7-6!!!!未解决
		// return user.getPerson().getParent().getName().equals("客户代表");
		return UserUtil.testUser(user, "客户代表");
	}

	public boolean IsCW(int userId) {
		User user = userService.findUserById(userId);
		// TODO ~~~~~~~~~~这里应该还得添加角色的判断--------------2013-7-6!!!!未解决
		// return user.getPerson().getParent().getName().equals("财务代表");
		// System.out.println(user);
		return UserUtil.testUser(user, "财务代表");
	}

	/**
	 * 判断审核
	 * 
	 * @return
	 */
	public boolean IsShenHe(String str) {
		// System.out.println("status--------------------------------" + str);
		return str.equals(EActivity.SZZSH.getName())
				|| str.equals(EActivity.SZRSH.getName())
				|| str.equals(EActivity.YZZSH.getName())
				|| str.equals(EActivity.YZRSH.getName())
				|| str.equals(EActivity.FZSH.getName());
	}

	private void p_approve() {
		try {
			int projectId = project.getId();
			// TODO 这里有问题，更改的信息没有保存下来。。
			project = projectService.findProjectById(projectId);
			String oldStatus = currentStatus;
			// 提交到下一步
			String processInstanceId = project.getProcessInstanceId();
			String username = currentUser().getUsername();
			User _from = userService.findUserByUsername(username);
			String status = workflowService.flowToNextStep_final_f(tid,
					processInstanceId, result, _from, project);
			project.setStatus(status);
			projectService.updateProject(project);

			// 添加审批记录
			int approverId = userId;
			if (comment == null) {
				comment = "【" + currentStatus + "】工作完成";
			}
			projectService.addApproveInfo(comment, oldStatus, projectId,
					approverId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("审批出错~~");
		}
	}

	private void upload() throws IOException {
		// 实现文件上传 TODO 2013/7/9 实现文件上传 by zhuqi
		if (uploadFiles != null) {
			int projId = project.getId();
			project = projectService.findProjectById(projId);
			// String proname = project.getProname();
			String prefix = targetFileDir + projId;
			String serverRealPath = ServletActionContext.getServletContext()
					.getRealPath(prefix);
			File dir = new File(serverRealPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// String mypath = new File(ServletActionContext.getServletContext()
			// .getRealPath("/")).getParent();
			// File ttt = new File(mypath + "/ttt");
			// if (!ttt.exists()) {
			// ttt.mkdirs();
			// }
			filesCount = uploadFiles.length;
			targetFileNames = new String[filesCount];
			List<Zfile> zfiles = getMyFilesByStatus();
			int i = 0;
			for (int j = 0; j < fileUsed.length; j++) {
				// System.out.println("fileUsed-->" + fileUsed[j]);
				if (fileUsed[j].equals("1")) {
					targetFileNames[i] = FileNameUtil.getNewFileName(
							uploadFilesFileName[i], i);
					// 实际上传
					File targetFile = new File(serverRealPath,
							targetFileNames[i]);
					FileUtils.copyFile(uploadFiles[i], targetFile);
					// 创建文件和工程的关联
					Pfile file = new Pfile();
					file.setZfile(zfiles.get(j));
					file.setProject(project);
					file.setPath(prefix + "/" + targetFileNames[i]);
					pfileService.addPfile(file);
					i++;
				}
			}
			if (i != filesCount) {
				System.out
						.println("------->>>>>>>>>>>>>>>>>>>>>>>>>>>有文件上传失败!!");
			}
			// addActionMessage("共上传了" + filesCount + "个文件");

		} else {
			// addActionError("请选择上传文件");
		}
	}

	/**
	 * 添加工程信息
	 * 
	 * @throws IOException
	 */
	public String add() throws IOException {
		// TODO 2013/6/28 实现文件上传 暂时不做了，首先实现工作流!!!!!!!!!!!!
		// System.out.println("Area------------>>>>>>>>>>" + project.getArea());
		projectService.addProject(project, workflowId, userId);

		// System.out.println("add==>projectid--------------------"
		// + project.getId());
		Set<String> set = SetStringUtil.Str2Set(project.getStatus());
		// assert set.size() == 1;
		currentStatus = set.iterator().next();
		upload();
		return "add_success";
	}

	private String getNewFileName(String fileName, int curIndex) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String newFileName = sf.format(Calendar.getInstance().getTime());
		int beginIndex = fileName.lastIndexOf(".");
		if (beginIndex > 0) {
			String fileExt = fileName.substring(beginIndex);
			newFileName = newFileName + "_" + curIndex + fileExt;
		}
		return newFileName;
	}

	public String updateFid() {
		int projectId = project.getId();
		Project project1 = projectService.findProjectById(projectId);
		project1.setFid(project.getFid());
		project1.setMyYear(TimeUtil.getToday());
		projectService.updateProject(project1);
		// 提交到下一步
		p_approve();
		return "update_success";
	}

	/**
	 * 查看该工程
	 * 
	 * @return
	 */

	public String view() {
		project = projectService.findProjectById(project.getId());

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);

		ActionContext.getContext().put("projectId", project.getId());

		return "view";
	}

	public String viewActivity() {
		project = projectService.findProjectById(project.getId());
		return "view_activity";
	}

	public String viewPic() {
		// 通过ProcessInstanceId查到流程实例
		project = projectService.findProjectById(project.getId());
		ProcessInstance processInstance = executionService
				.findProcessInstanceById(project.getProcessInstanceId());
		if (processInstance != null) {
			Set<String> activityNames = processInstance
					.findActiveActivityNames();
			// Coordinates为相依衣物
			List<ActivityCoordinates> list = new ArrayList<ActivityCoordinates>();
			for (Iterator<String> it = activityNames.iterator(); it.hasNext();) {
				String activityName = it.next();
				// ActivityCoordinates ac =
				// repositoryService.getActivityCoordinates(
				// processInstance.getProcessDefinitionId(), activityName);
				ZActivity activity = activityService
						.findActivityByName(activityName);
				// System.out.println(activityName);
				// System.out
				// .println("-------------->>>>>>>>>>>>>>>>>" + activity);
				ActivityCoordinates ac = new ActivityCoordinatesImpl(
						activity.getX(), activity.getY(), activity.getWidth(),
						activity.getHeight());
				list.add(ac);
			}
			setAcList(list);
		}
		// Coordinates为相依衣物
		// ac = repositoryService.getActivityCoordinates(processInstance
		// .getProcessDefinitionId(), activityNames.iterator().next());

		// 显示#{owner}
		User owner = project.getCreator();
		String oname;
		try {
			oname = owner.getPerson().getName();
		} catch (Exception e) {
			oname = owner.getUsername();
		}
		ActionContext.getContext().put("owner", oname);
		return "viewPic";
	}

	public String getMyOwner(String owner) {
		if ("#{owner}".equals(owner)) {
			return "客户代表[" + (String) ActionContext.getContext().get("owner")
					+ "]";
		}
		return owner;
	}

	/**
	 * 使用的是get方法，用于显示工作流程图片
	 * 
	 * @throws IOException
	 */
	public void pic() throws IOException {
		project = projectService.findProjectById(project.getId());
		Workflow workflow = workflowService.findWorkflowById(project
				.getWorkflow().getId());
		byte[] processImage = workflow.getProcessImage();
		ServletOutputStream sos = ServletActionContext.getResponse()
				.getOutputStream();
		sos.write(processImage);
		sos.close();
	}

	public String generateId() {
		project = projectService.findProjectById(project.getId());
		Task task = taskService.getTask(tid);
		currentStatus = task.getActivityName();
		return "generate_id";
	}

	public void getZid() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		out.print(IdUtil.generateId(idTableService, ruleService));
	}

	public void getMySfee() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		HttpServletRequest request = ServletActionContext.getRequest();
		String aa = request.getParameter("aa");
		String bb = request.getParameter("bb");
		out.print(MulUtil.mul(aa, bb));
	}

	// /////////////////////////
	// TODO 2013-7-9 目标：完成工程的文件关联上传下载
	// /////////////////////////

	public List<Zfile> getMyFilesByGCSL() {
		if (zfiles == null) {
			ZActivity activity = activityService
					.findActivityByName(EActivity.GCSL.getName());
			zfiles = activityService.findAllFileList(activity.getId());
		}
		return zfiles;
	}

	public List<Zfile> getMyFilesByStatus() {
		// System.out.println("currentStatus----------->" + currentStatus);
		if (zfiles == null) {
			ZActivity activity = activityService
					.findActivityByName(currentStatus);
			zfiles = activityService.findAllFileList(activity.getId());
		}
		return zfiles;
	}

	/**
	 * 查看getMyFilesByStatus()是否为空
	 * 
	 * @return
	 */
	public boolean IsNotNull() {
		List<Zfile> files = getMyFilesByStatus();
		return files != null && files.size() > 0;
	}

	/**
	 * 获得已上传文件
	 * 
	 * @return
	 */
	public List getMyUsedFiles() {
		// System.out.println("MyUsedFiles->>>>>>>>>>>>>>>>" + project.getId());
		return projectService.getMyUsedFiles(project.getId());
	}

	/**
	 * 获得待上传文件
	 * 
	 * @return
	 */
	public List getMyUsingFiles() {
		// TODO ---------------获得待上传文件
		return null;
	}

	public String five_input() {
		project = projectService.findProjectById(project.getId());
		// currentStatus = ServletActionContext.getRequest().getParameter(
		// "currentStatus");
		// currentStatus = new String(currentStatus.getBytes("ISO-8859-1"),
		// "UTF-8");
		Task task = taskService.getTask(tid);
		currentStatus = task.getActivityName();

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);

		return "five_input";
	}

	public String five() {
		try {
			upload();
			p_approve();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "update_success";
	}

	// ///////////////////////////////////////////////////////

	// 用于财务代表的信息操作。
	// private String[] payname;
	private String[] money;
	private String[] payer;
	private String[] payTime;

	// public String[] getPayname() {
	// return payname;
	// }

	// public void setPayname(String[] payname) {
	// this.payname = payname;
	// }

	public String[] getMoney() {
		return money;
	}

	public String[] getPayer() {
		return payer;
	}

	public void setPayer(String[] payer) {
		this.payer = payer;
	}

	public void setMoney(String[] money) {
		this.money = money;
	}

	public String[] getPayTime() {
		return payTime;
	}

	public void setPayTime(String[] payTime) {
		this.payTime = payTime;
	}

	public String pay_input() {
		project = projectService.findProjectById(project.getId());

		Task task = taskService.getTask(tid);
		currentStatus = task.getActivityName();

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);
		return "pay_input";
	}

	public String pay() {
		try {
			upload();
			p_pay();
			p_approve();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "update_success";
	}

	private void p_pay() {
		List<ZPayment> zpayments = getMyZPaymentsByStatus();
		if (zpayments != null && zpayments.size() > 0) {
			int len = zpayments.size();
			User operator = userService.findUserById(userId);
			project = projectService.findProjectById(project.getId());
			for (int i = 0; i < len; i++) {
				ZPayment zpayment = zpayments.get(i);
				Payment payment = new Payment();
				payment.setZpayment(zpayment);
				payment.setMoney(money[i]);
				payment.setPayer(payer[i]);
				payment.setOperTime(new Date());
				payment.setPayTime(payTime[i]);
				payment.setOperator(operator);
				payment.setProject(project);
				paymentService.addPayment(payment);
			}
		}
	}

	// //////////////////////////////////////////////////
	// 财务的管理——update by zhuqi

	public List<ZPayment> getMyZPaymentsByStatus() {
		if (zpayments == null) {
			ZActivity activity = activityService
					.findActivityByName(currentStatus);
			zpayments = activityService.findAllPaymentList(activity.getId());
		}
		return zpayments;
	}

	public boolean IsZPaymentNotNull() {
		List<ZPayment> zpayments = getMyZPaymentsByStatus();
		return zpayments != null && zpayments.size() > 0;
	}

	// //////////////////////////////////////////////////

	public boolean IsRelatedGCSL(Integer databaseId) {
		List<Zfile> zfiles = getMyFilesByGCSL();
		for (Iterator<Zfile> it = zfiles.iterator(); it.hasNext();) {
			Zfile zfile = it.next();
			// System.out.println("-------------------------"
			// + zfile.getBase().getId());
			if (databaseId.equals(zfile.getBase().getId())) {
				return true;
			}
		}
		return false;
	}

	public boolean IsRelated(Integer databaseId) {
		List<Zfile> zfiles = getMyFilesByStatus();
		for (Iterator<Zfile> it = zfiles.iterator(); it.hasNext();) {
			Zfile zfile = it.next();
			// System.out.println("-------------------------"
			// + zfile.getBase().getId());
			if (databaseId.equals(zfile.getBase().getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * activity =>model演示
	 * 
	 * @return
	 */
	public String activity() {
		ZActivity activity = activityService.findActivityById(activityId);
		System.out.println("activityId-=======================" + activityId);
		currentStatus = activity.getName();
		System.out.println("currentStatus-======================="
				+ currentStatus);
		int projectId = project.getId();
		ApproveInfo info = projectService.getApprove(projectId, currentStatus);
		if (info == null) {
			throw new RuntimeException("该工程还没有进行到这个步骤!");
		}
		ActionContext.getContext().put("info", info);
		ActionContext.getContext().put("projectId", projectId);
		if (currentStatus.equals(EActivity.GCSL.getName())) {

			List<Database> databases = databaseService.getAllDatabaseList();
			ActionContext.getContext().put("databases", databases);

			List<Workflow> workflows = workflowService.getAllWorkflowList();
			ActionContext.getContext().put("workflows", workflows);

			return "GCSL";
		}
		if (IsShenHe(currentStatus)) {
			return "ShenHe";
		}
		if (currentStatus.equals(EActivity.SZZFID.getName())) {
			return "FPID";
		}
		// // 财务代表
		// if (IsCW(info.getApprover().getId())) {
		//
		// // List<Payment> payments = paymentService.getAllPayment(projectId,
		// // activityId);
		// // ActionContext.getContext().put("payments", payments);
		//
		// return "CW";
		// }
		// // TODO 待修改
		// return "file";
		return "other";
	}

	public int getPFileByZFile(int projectId, int zfileId) {
		// System.out.println("projectId====" + projectId);
		// System.out.println("zfileId====" + zfileId);
		int pfileId = pfileService.getPFileByZFile(projectId, zfileId).getId();
		// System.out.println("pfileId====" + pfileId);
		return pfileId;
	}

	public boolean IsExisted(int projectId, int zfileId) {
		// System.out.println("##############IsExisted##############");
		// System.out.println("projectId=" + projectId + ", zfileId=" +
		// zfileId);
		// System.out.println("#####################################");
		Pfile pfile = pfileService.getPFileByZFile(projectId, zfileId);
		if (pfile == null) {
			return false;
		}
		String path = pfile.getPath();
		if (path != null && !path.equals("")) {
			return !IsLost(path);
		}
		return false;
	}

	public boolean IsLost(String path) {
		System.out.println("path-------" + path);
		return FileUtil.IsLost(path);
	}

	// //////////////////////////
	// TODO 改善界面 approve_input

	public List<ZActivity> getAllZActivity() {
		return activityService.getAllZActivity();
	}

	public boolean IsAc(int projectId, String status) {
		return status.equals("工程受理")
				|| projectService.getApprove(projectId, status) != null;
	}

	public boolean IsCUser(int userId, int projectId, String status) {
		// System.out.println("userId--!!-->>>>>>>>>>>>>>" + userId);
		if (status.equals("工程受理")) {
			return projectService.findProjectById(projectId).getCreator()
					.getId() == userId;
		}
		return projectService.getApprove(projectId, status).getApprover()
				.getId() == userId;
	}

	public List<ZActivity> getUsedActivity() {
		List<ZActivity> activities = activityService.getAllZActivity();
		int projectId = project.getId();
		/*
		 * for (Iterator<ZActivity> it = activities.iterator(); it.hasNext();) {
		 * ZActivity activity = it.next(); ApproveInfo info =
		 * projectService.getApprove(projectId, activity.getName()); if (info ==
		 * null) { activities.remove(activity); } }
		 */
		for (int i = activities.size() - 1; i >= 0; i--) {
			ZActivity activity = activities.get(i);
			if (!activity.getName().equals("工程受理")
					&& projectService.getApprove(projectId, activity.getName()) == null) {
				activities.remove(i);
			}
		}
		return activities;
	}

	public List<Zfile> getZfile(int activityId) {
		return activityService.findAllFileList(activityId);
	}

	// ///////////////////////////

	// //////////////////////////////
	// update Project

	public String updateInput() {
		project = projectService.findProjectById(project.getId());

		// 一定不会更改 Workflow
		// List<Workflow> workflows = workflowService.getAllWorkflowList();
		// ActionContext.getContext().put("workflows", workflows);

		ActionContext.getContext().put("projectId", project.getId());

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);

		return "update_input";
	}

	public String updateIdInput() {
		project = projectService.findProjectById(project.getId());

		return "update_id_input";
	}

	public String updateCWInput() {
		ZActivity activity = activityService.findActivityById(activityId);
		currentStatus = activity.getName();

		int projectId = project.getId();
		ActionContext.getContext().put("projectId", projectId);

		project = projectService.findProjectById(projectId);

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);

		// List<Payment> payments = paymentService.getAllPayment(projectId,
		// activityId);
		// ActionContext.getContext().put("payments", payments);

		return "update_CW_input";
	}

	// public boolean IsPaymentExist(int projectId, int zpaymentId) {
	// return paymentService.getPayment(projectId, zpaymentId) != null;
	// }

	public int getPaymentId(int projectId, int zpaymentId) {
		// System.out.println(projectId + "-------" + zpaymentId);
		Payment payment = paymentService.getPayment(projectId, zpaymentId);
		if (payment != null) {
			return payment.getId();
		}
		return 0;
	}

	public String getPaymentMoney(int projectId, int zpaymentId) {
		// System.out.println(projectId + "-------" + zpaymentId);
		Payment payment = paymentService.getPayment(projectId, zpaymentId);
		if (payment != null) {
			return payment.getMoney();
		}
		return null;
	}

	public String getPaymentPayer(int projectId, int zpaymentId) {
		// System.out.println(projectId + "-------" + zpaymentId);
		Payment payment = paymentService.getPayment(projectId, zpaymentId);
		if (payment != null) {
			return payment.getPayer();
		}
		return null;
	}

	public String getPaymentTime(int projectId, int zpaymentId) {
		// System.out.println(projectId + "-------" + zpaymentId);
		Payment payment = paymentService.getPayment(projectId, zpaymentId);
		if (payment != null) {
			return payment.getPayTime();
		}
		return null;
	}

	private int[] payId;

	public int[] getPayId() {
		return payId;
	}

	public void setPayId(int[] payId) {
		this.payId = payId;
	}

	public String updatePay() throws IOException {
		project = projectService.findProjectById(project.getId());
		ZActivity activity = activityService.findActivityById(activityId);

		// TODO **********************更改上传文件
		update_upload(project.getId());
		// 更改payment信息
		List<ZPayment> zpayments = getMyZPaymentsByStatus();
		if (zpayments != null && zpayments.size() > 0) {
			User operator = userService.findUserById(userId);
			int len = zpayments.size();
			for (int i = 0; i < len; i++) {
				if (payId[i] != 0) {
					Payment payment = paymentService.findPaymentById(payId[i]);
					payment.setMoney(money[i]);
					payment.setPayer(payer[i]);
					payment.setOperTime(new Date());
					payment.setPayTime(payTime[i]);
					paymentService.updatePayment(payment);
				} else {
					ZPayment zpayment = zpayments.get(i);
					Payment payment = new Payment();
					payment.setZpayment(zpayment);
					payment.setMoney(money[i]);
					payment.setPayer(payer[i]);
					payment.setOperTime(new Date());
					payment.setOperator(operator);
					payment.setPayTime(payTime[i]);
					payment.setProject(project);
					paymentService.addPayment(payment);
				}

			}
		}

		/*
		 * 
		 * int i = 0; // 历史 if (payId != null && payId.length > 0) { for (; i <
		 * payId.length; i++) { Payment payment =
		 * paymentService.findPaymentById(payId[i]);
		 * payment.setName(payname[i]); payment.setMoney(money[i]);
		 * payment.setPayer(payer[i]); payment.setOperTime(new Date());
		 * paymentService.updatePayment(payment); } } // 新增的 if (payname !=
		 * null) { int len = payname.length; for (; i < len; i++) { Payment
		 * payment = new Payment(); payment.setName(payname[i]);
		 * payment.setMoney(money[i]); payment.setPayer(payer[i]);
		 * payment.setOperTime(new Date()); payment.setOperator(operator);
		 * payment.setActivity(activity); payment.setProject(project);
		 * paymentService.addPayment(payment); } }
		 */
		// 增加操作信息
		int approverId = userId;
		projectService.addApproveInfo("修改【" + activity.getName() + "】->修改财务信息",
				activity.getName(), project.getId(), approverId);
		return "update_success";
	}

	private int pid;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public void delPay() {
		int approverId = userId;
		ZActivity activity = activityService.findActivityById(activityId);
		paymentService.delPayment(pid);
		projectService.addApproveInfo("修改【" + activity.getName() + "】->删除财务信息",
				activity.getName(), project.getId(), approverId);
	}

	public String updatefileInput() {

		ZActivity activity = activityService.findActivityById(activityId);
		currentStatus = activity.getName();

		int projectId = project.getId();
		ActionContext.getContext().put("projectId", projectId);

		project = projectService.findProjectById(projectId);

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);

		return "update_file_input";
	}

	public String updateFile() throws IOException {
		// project = projectService.findProjectById(project.getId());
		ZActivity activity = activityService.findActivityById(activityId);
		update_upload(project.getId());

		int approverId = userId;
		projectService.addApproveInfo("修改【" + activity.getName() + "】",
				activity.getName(), project.getId(), approverId);
		return "update_success";
	}

	protected void update_upload(int projId) throws IOException {
		if (uploadFiles != null) {
			// int projId = projectVO.getId();
			Project project = projectService.findProjectById(projId);
			// String proname = project.getProname();
			String prefix = targetFileDir + projId;
			String serverRealPath = ServletActionContext.getServletContext()
					.getRealPath(prefix);
			File dir = new File(serverRealPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			filesCount = uploadFiles.length;
			targetFileNames = new String[filesCount];
			List<Zfile> zfiles = activityService.findAllFileList(activityId);
			int i = 0;
			for (int j = 0; j < fileUsed.length; j++) {
				// System.out.println("fileUsed-->" + fileUsed[j]);
				if (fileUsed[j].equals("1")) {
					targetFileNames[i] = FileNameUtil.getNewFileName(
							uploadFilesFileName[i], i);
					// 实际上传
					File targetFile = new File(serverRealPath,
							targetFileNames[i]);
					FileUtils.copyFile(uploadFiles[i], targetFile);

					// TODO 2013-7-21 by zhuqi 创建文件和工程的关联,同时修改之前已经上传过的。
					Zfile zfile = zfiles.get(j);
					int zfileId = zfile.getId();
					Pfile pfile = pfileService.getPFileByZFile(projId, zfileId);
					String update_path = prefix + "/" + targetFileNames[i];
					if (pfile == null) {
						// 不存在, 创建文件和工程的关联
						Pfile file = new Pfile();
						file.setZfile(zfile);
						file.setProject(project);
						file.setPath(update_path);
						pfileService.addPfile(file);
					} else {
						// TODO ------实际删除文件 。。。。。存在，首先删除原来的文件
						String path = pfile.getPath();
						if (path != null) {
							String realPath = ServletActionContext
									.getServletContext().getRealPath(path);
							File file = new File(realPath);
							if (file.isFile() && file.exists()) {
								// 原来的文件存在，删除
								file.delete();
							}
						}
						pfile.setPath(update_path);
						pfileService.updatePfile(pfile);
					}
					i++;
				}
			}
			if (i != filesCount) {
				System.out
						.println("------->>>>>>>>>>>>>>>>>>>>>>>>>>>有文件上传失败!!");
			}
			// addActionMessage("共上传了" + filesCount + "个文件");

		} else {
			// addActionError("请选择上传文件");
		}
	}

	// //////////////////////////////

	// ////////////////////////////////
	// 销户操作
	private boolean IsAfterQDXY() {
		List<ApproveInfo> infos = projectService.getAllApproveInfos(project
				.getId());
		for (ApproveInfo info : infos) {
			if (info.getStatus().equals("签订协议")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 能销户么?
	 * 
	 * @return
	 */
	public boolean IsXiaohuable() {
		if (IsCSR(userId) && !IsAfterQDXY())
			return true;
		else
			return false;
	}

	/**
	 * 删除工程，其实就是销户操作了。。
	 */
	public String del() {
		projectService.delRealProject(project.getId());
		return "del_success";
	}

	// /////////////////////////////////

	// view

	// getAllZActivity

	// //////////////////////////////////////

	@Resource
	private ZSequenceService zSequenceService;

	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	// 生成报表
	public String generateExcel() {
		String serverRealPath = ServletActionContext.getServletContext()
				.getRealPath(targetFileDir);
		File dir = new File(serverRealPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		path = FileNameUtil.getNewFileName("*.xls", 0);
		List<Project> projects = new ArrayList<Project>();
		String[] projIds = ids.trim().split(",");
		for (String projId : projIds) {
			int id = Integer.parseInt(projId);
			projects.add(projectService.getProjectById(id));
		}
		// project = projectService.findProjectById(project.getId());
		GenerateExcel.generateExcel2003_final(projects, zSequenceService,
				paymentService, fundService, serverRealPath, path);
		// 真实的相对地址...
		path = targetFileDir + path;
		return "excel_success";
	}

	// //////////////////////////////

	// //////////////////////////////
	// 审核意见主界面显示
	public ApproveInfo getMyApprove(int projectId, String status) {
		return projectService.getApprove(projectId, status);
	}

	// ////////////////////////////////////
	// modify by zhuqi 2013/12/1
	// 附加信息管理
	// ////////////////////////////////////

	private List<Zfund> zfunds;

	public List<Zfund> getMyZFundsByStatus() {
		if (zfunds == null) {
			ZActivity activity = activityService
					.findActivityByName(currentStatus);
			zfunds = activityService.findAllFundList(activity.getId());
		}
		return zfunds;
	}

	public boolean IsZfundNotNull() {
		List<Zfund> zfunds = getMyZFundsByStatus();
		return zfunds != null && zfunds.size() > 0;
	}

	public String otherInput() {
		project = projectService.findProjectById(project.getId());

		Task task = taskService.getTask(tid);
		currentStatus = task.getActivityName();

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);
		return "other_input";
	}

	public String other() {
		try {
			upload();
			p_pay();
			p_other();
			p_approve();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "update_success";
	}

	// modify by zhuqi 2013/12/2
	private Integer[] fundId;
	private String[] fmoney;
	private String[] ftime;
	private String[] foper;

	public Integer[] getFundId() {
		return fundId;
	}

	public void setFundId(Integer[] fundId) {
		this.fundId = fundId;
	}

	public String[] getFmoney() {
		return fmoney;
	}

	public void setFmoney(String[] fmoney) {
		this.fmoney = fmoney;
	}

	public String[] getFtime() {
		return ftime;
	}

	public void setFtime(String[] ftime) {
		this.ftime = ftime;
	}

	public String[] getFoper() {
		return foper;
	}

	public void setFoper(String[] foper) {
		this.foper = foper;
	}

	@Resource
	private FundService fundService;

	private void p_other() {
		// TODO --------------------------待修改............................
		List<Zfund> zfunds = getMyZFundsByStatus();
		if (zfunds != null && zfunds.size() > 0) {
			int len = zfunds.size();
			project = projectService.findProjectById(project.getId());
			for (int i = 0; i < len; i++) {
				Zfund zfund = zfunds.get(i);
				Fund fund = new Fund();
				fund.setZfund(zfund);
				if (zfund.getFn_use() == 1) {
					fund.setMoney(fmoney[i]);
				}
				if (zfund.getFt_use() == 1) {
					fund.setTime(ftime[i]);
				}
				if (zfund.getFo_use() == 1) {
					fund.setOper(foper[i]);
				}
				fund.setProject(project);
				fundService.addFund(fund);
			}
		}
	}

	public String updateOtherInput() {

		ZActivity activity = activityService.findActivityById(activityId);
		currentStatus = activity.getName();

		int projectId = project.getId();
		ActionContext.getContext().put("projectId", projectId);

		project = projectService.findProjectById(projectId);

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);

		return "update_other_input";
	}

	private List<Fund> funds;

	public List<Fund> getMyFundsByStatus() {
		int projectId = project.getId();
		if (funds == null) {
			funds = new ArrayList<Fund>();
			List<Zfund> zfunds = getMyZFundsByStatus();
			for (Zfund zfund : zfunds) {
				Fund fund = fundService.getFund(projectId, zfund.getId());
				if (fund == null) {
					fund = new Fund();
					fund.setId(0);
					fund.setZfund(zfund);
				}
				funds.add(fund);
			}
		}
		return funds;
	}

	public String updateOther() throws IOException {
		ZActivity activity = activityService.findActivityById(activityId);
		project = projectService.findProjectById(project.getId());
		StringBuffer buffer = new StringBuffer();
		// 更改上传文件
		update_upload(project.getId());
		buffer.append("->修改上传文件");

		// 更改payment信息
		List<ZPayment> zpayments = getMyZPaymentsByStatus();
		if (zpayments != null && zpayments.size() > 0) {
			User operator = userService.findUserById(userId);
			int len = zpayments.size();
			for (int i = 0; i < len; i++) {
				if (payId[i] != 0) {
					Payment payment = paymentService.findPaymentById(payId[i]);
					payment.setMoney(money[i]);
					payment.setPayer(payer[i]);
					payment.setOperTime(new Date());
					payment.setPayTime(payTime[i]);
					paymentService.updatePayment(payment);
				} else {
					ZPayment zpayment = zpayments.get(i);
					Payment payment = new Payment();
					payment.setZpayment(zpayment);
					payment.setMoney(money[i]);
					payment.setPayer(payer[i]);
					payment.setOperTime(new Date());
					payment.setOperator(operator);
					payment.setPayTime(payTime[i]);
					payment.setProject(project);
					paymentService.addPayment(payment);
				}
			}
			buffer.append("->修改财务信息");
		}

		// // TODO ..................待修改..............
		// 更改附加信息
		List<Zfund> zfunds = getMyZFundsByStatus();
		if (zfunds != null && zfunds.size() > 0) {
			int len = zfunds.size();
			for (int i = 0; i < len; i++) {
				if (fundId[i] != 0) {
					Fund fund = fundService.findFundById(fundId[i]);
					fund.setMoney(fmoney[i]);
					fund.setTime(ftime[i]);
					fund.setOper(foper[i]);
					fundService.updateFund(fund);
				} else {
					Zfund zfund = zfunds.get(i);
					Fund fund = new Fund();
					fund.setZfund(zfund);
					fund.setMoney(fmoney[i]);
					fund.setTime(ftime[i]);
					fund.setOper(foper[i]);
					fund.setProject(project);
					fundService.addFund(fund);
				}
			}
			buffer.append("->修改附加信息");
		}

		// 增加操作信息
		int approverId = userId;
		projectService.addApproveInfo(
				"修改【" + activity.getName() + "】" + buffer.toString(),
				activity.getName(), project.getId(), approverId);
		return "update_success";
	}

	public String taskInput() {
		project = projectService.findProjectById(project.getId());
		Task task = taskService.getTask(tid);
		currentStatus = task.getActivityName();
		if ("分配工程编号".equals(currentStatus)) {
			return "generate_id";
		} else if ("工程受理".equals(currentStatus) || IsShenHe(currentStatus)) {
			return "approve_input2";
		} else {
			List<Database> databases = databaseService.getAllDatabaseList();
			ActionContext.getContext().put("databases", databases);
			return "other_input";
		}
	}

	@Resource
	private ZFKpercentumService zfKpercentumService;

	public Integer getCurrPercentum() {
		ZActivity activity = activityService.findActivityByName(currentStatus);
		ZFKpercentum kpercentum = zfKpercentumService
				.findZFKpercentumByActivity(activity.getId());
		return kpercentum.getNum();
	}

	/**
	 * 判断是否是正式工程付款
	 * 
	 * @param str
	 * @return
	 */
	public boolean IsZFK() {
		String str = currentStatus;
		return str.equals(EActivity.JK30.getName())
				|| str.equals(EActivity.JK60.getName())
				|| str.equals(EActivity.JK80.getName())
				|| str.equals(EActivity.JK15.getName())
				|| str.equals(EActivity.SGJK5.getName());
	}

}
