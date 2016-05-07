package cn.zhuqi.oa.service;

import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.task.Task;

import cn.zhuqi.oa.model.Party;
import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.model.Role;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.model.Workflow;
import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.vo.PagerVO;

/**
 * 工作流管理
 * 
 * @author Zhuqi
 * 
 */
public interface WorkflowService {

	/**
	 * 部署流程,同时包括OA系统中的和JBPM中的add操作
	 */
	public void deployProcess(ZipInputStream zis);

	/**
	 * 查询特定流程定义信息
	 * 
	 * @param workflowId
	 *            (工作流标识)
	 * @return
	 */
	public Workflow findWorkflowById(int workflowId);

	/**
	 * 查询特定流程定义信息
	 * 
	 * @param workflowId
	 *            (工作流标识)
	 * @return
	 */
	public Workflow findWorkflowByName(String workflowName);

	/**
	 * 删除流程
	 * 
	 * @param workflowId
	 *            (工作流标识)
	 */

	public void delWorkflow(int workflowId);

	/**
	 * 查询当前已有流程分页
	 * 
	 * @return 分页中元素是Workflow对象
	 */
	public PagerVO getAllWorkflow();

	/**
	 * 查询当前已有流程列表
	 * 
	 * @return
	 */
	public List<Workflow> getAllWorkflowList();

	/**
	 * 开始一个流程实例
	 * 
	 * @param workflowName工作流的名称
	 * @param projectId
	 *            工程ID
	 * @param userId
	 *            用户ID <==拥有者
	 * @return 返回一个流程实例的ID
	 */
	public String addProcessInstance(String workflowName, int projectId,
									 int userId);

	/**
	 * 删除一个流程实例
	 * 
	 * @param processInstanceId
	 */
	public void delProcessInstance(String processInstanceId);

	/**
	 * 查找流转到某个用户的所有待审核工程列表,用于searchApprovingProjectsByRoles
	 * 
	 * @param username
	 *            用户名称
	 * @return
	 */
	public List searchApprovingProjects(String username);

	/**
	 * 查找流转到某个用户角色的所有待审核工程列表,用于searchApprovingProjectsByRoles
	 * 
	 * @param role
	 *            用户角色
	 * @return 工程ID列表
	 */
	public List searchApprovingProjectsByPosition(Party party);

	/**
	 * 查找流转到某个用户角色的所有待审核工程列表,用于searchApprovingProjectsByRoles
	 * 
	 * @param role
	 *            用户角色
	 * @return 工程ID列表
	 */
	public List searchApprovingProjectsByRole(Role role);

	/**
	 * 查找流转到某个用户角色的所有待审核工程列表,用于projectService
	 * 
	 * @param roles
	 *            用户角色列表
	 * @return 工程ID列表
	 */
	public List searchApprovingProjectsByRoles(List<Role> roles);

	/**
	 * 搜索下一步的流向,及下一步可以流向的路径
	 * 
	 * @param processInstanceId
	 * @return 流向的名称列表
	 */
	// public List searchNextTransitions(String processInstanceId);

	/**
	 * 流向下一步,就是提交,完成一个任务
	 * 
	 * @param username
	 *            用户账号
	 * @param processInstanceId
	 *            流程实例标识
	 * @param result
	 *            提交路径
	 * @return 该流程的状态
	 */
	// public String flowToNextStep(String username, String processInstanceId,
	// String result);

	/**
	 * 查找该用户的所有任务
	 * 
	 * @param username
	 * @param processInstanceId
	 * @return
	 */
	public List<Task> getAllTask(String username, String processInstanceId);

	/**
	 * 完成任务的改进版
	 * 
	 * @param taskId
	 * @param result
	 * @return
	 */
	public String flowToNextStep(String taskId, String processInstanceId,
								 String result);

	/**
	 * 完成任务的改进版 ---终极版本
	 * 
	 * @param username
	 * @param taskId
	 * @param processInstanceId
	 * @param result
	 * @return
	 */
	public String flowToNextStep_final(String username, String taskId,
									   String processInstanceId, String result);

	public String flowToNextStep_final_f(String taskId,
										 String processInstanceId, String result, User _from, Project project);

	public void update(Workflow workflow);

}
