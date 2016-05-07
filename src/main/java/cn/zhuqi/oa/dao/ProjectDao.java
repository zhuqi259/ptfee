package cn.zhuqi.oa.dao;

import java.util.List;
import java.util.Set;

import cn.zhuqi.oa.model.ApproveInfo;
import cn.zhuqi.oa.model.Pfile;
import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.vo.PagerVO;

public interface ProjectDao extends BaseDao {

	/**
	 * 其实就是获得Set<Pfile> files的List版本，要有序
	 * 
	 * @param projectId
	 * @return
	 */
	public List getMyUsedFiles(int projectId);

	/**
	 * 由Id列表查找工程
	 * 
	 * @param projIds
	 * @return
	 */
	public PagerVO findProjectByIds(List projIds);

	/**
	 * 由Id列表查找工程,支持模糊查找
	 * 
	 * @param projIds
	 * @return
	 */
	public PagerVO findProjectByIds(List projIds, String projectName);

	/**
	 * 保存工程信息
	 * 
	 * @param project
	 * @param workflowId
	 * @param userId
	 */
	public void addProject(Project project, int workflowId, int userId);

	/**
	 * 查询工程的审批历史信息
	 * 
	 * @param projectId
	 * @return
	 */
	public PagerVO searchApproveInfos(int projectId);

	public List<ApproveInfo> getAllApproveInfos(int projectId);
	
	public List<ApproveInfo> getAllApproveInfosByUserId(int userId);

	/**
	 * 搜索用户自身的工程列表,同时提供工程名称的模糊搜索
	 * 
	 * @param userId
	 * @param projectName
	 * @return
	 */
	public PagerVO searchMyProjects(int userId, String projectName);

	// public PagerVO searchApprovingProjects(int userId, String projectName);

	/**
	 * 查询该用户的已审核工程列表,同时提供工程名称的模糊搜索
	 * 
	 * @param userId
	 * @param sSearch
	 * @return
	 */
	public PagerVO searchApprovedProjects(int userId, String sSearch);

	/**
	 * 无条件得到所有工程,管理员和Boss的功能
	 * 
	 * @return
	 */
	public PagerVO getAllProjects();

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

	/**
	 * 通过审批记录获得该用户的工程列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> getUserProjectByApprove(int userId);

	/**
	 * 查找今年的所有工程
	 * 
	 * @return
	 */
	public List<Project> getAllProjectByToday();

	public PagerVO findProjectByIds2(List projIds);

}
