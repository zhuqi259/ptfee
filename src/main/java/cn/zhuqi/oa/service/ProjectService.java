package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.ApproveInfo;
import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.vo.PagerVO;

/**
 * 工程管理
 * 
 * @author Zhuqi
 * 
 */
public interface ProjectService {

	public List getMyUsedFiles(int projectId);

	/**
	 * 添加工程信息 <===客户代表的任务
	 * 
	 * @param project
	 * @param workflowId
	 * @param userId
	 */
	public void addProject(Project project, int workflowId, int userId);

	/**
	 * 查找某个工程的信息
	 * 
	 * @param projectId
	 * @return
	 */
	public Project findProjectById(int projectId);
	
	public Project getProjectById(int projectId);

	/**
	 * 更新工程信息
	 * 
	 * @param project
	 */
	public void updateProject(Project project);

	/**
	 * 删除工程信息
	 * 
	 * @param projectId
	 */
	public void delProject(int projectId);

	/**
	 * 搜索用户自身的工程列表,同时提供工程名称的模糊搜索
	 * 
	 * @param userId
	 * @param projectName
	 * @return
	 */
	public PagerVO searchMyProjects(int userId, String projectName);

	/**
	 * 查询工程的审批历史信息
	 * 
	 * @param projectId
	 * @return
	 */
	public PagerVO searchApproveInfos(int projectId);

	/**
	 * 查询该用户的待审核工程列表,同时提供工程名称的模糊搜索
	 * 
	 * @param userId
	 * @param projectName
	 * @return
	 */
	public PagerVO searchApprovingProjects(int userId, String projectName);

	/**
	 * 查询该用户的已审核工程列表,同时提供工程名称的模糊搜索
	 * 
	 * @param userId
	 * @param sSearch
	 * @return
	 */
	public PagerVO searchApprovedProjects(int userId, String sSearch);

	/**
	 * 增加工程的审核信息
	 * 
	 * @param approveComment
	 *            审核信息
	 * @param oldStatus
	 *            工程历史状态
	 * @param projectId
	 *            工程ID
	 * @param approverId
	 *            审核者ID
	 */
	public void addApproveInfo(String approveComment, String oldStatus,
							   int projectId, int approverId);

	// TODO ############################# 管理员和Boss的功能

	/**
	 * 无条件得到所有工程,管理员和Boss的功能
	 * 
	 * @return
	 */
	public PagerVO getAllProjects(String projectName);

	/**
	 * 获得历史记录
	 * 
	 * @param projectId
	 * @param status
	 * @return
	 */
	public ApproveInfo getApprove(int projectId, String status);

	public List<ApproveInfo> getAllApproveInfos(int projectId);

	/**
	 * 查找今年的所有工程
	 * 
	 * @return
	 */
	public List<Project> getAllProjectByToday();
	
	/**
	 * 删除工程信息(测试使用~~)
	 * 
	 * @param projectId
	 */
	public void delRealProject(int projectId);
	
	
	public List<Project> getAllProjectByTest();

	public PagerVO searchApprovingProjects2(int userId);

}
