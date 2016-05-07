package cn.zhuqi.oa.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.ProjectDao;
import cn.zhuqi.oa.model.ApproveInfo;
import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.model.Workflow;
import cn.zhuqi.oa.vo.PagerVO;
import cn.zhuqi.system.SystemContext;
import cn.zhuqi.system.TimeUtil;

@Repository("projectDao")
public class ProjectDaoImpl extends BaseDaoImpl implements ProjectDao {

	@Override
	public List getMyUsedFiles(int projectId) {
		String hql = "select p.files from Project p where p.id = ? order by p.id asc";
		return getSession().createQuery(hql).setParameter(0, projectId).list();
	}

	@Override
	public PagerVO findProjectByIds(List projIds) {

		String hql = "select p from Project p where p.id in (:ids)";
		String countHql = getCountHql(hql);
		int total = ((Long) getSession().createQuery(countHql)
				.setParameterList("ids", projIds).uniqueResult()).intValue();
		// TODO 自己写PagerVO的setParameterList
		List datas = getSession().createQuery(hql)
				.setParameterList("ids", projIds).list();

		PagerVO pv = new PagerVO();
		pv.setDatas(datas);
		pv.setTotal(total);

		return pv;
	}

	@Override
	public PagerVO findProjectByIds(List projIds, String sSearch) {
		int total = 0;
		List datas = new ArrayList();
		if (projIds != null && projIds.size() > 0) {
			// TODO 自己确定需要显示的项
			String hql = "select p.id, p.fid, p.proname, p.status from Project p where p.id in (:ids) and ( p.proname like :name or p.fid like :fid ) order by p.createTime ";
			String countHql = getCountHql(hql);
			total = ((Long) getSession().createQuery(countHql)
					.setParameterList("ids", projIds)
					.setParameter("name", "%" + sSearch + "%")
					.setParameter("fid", "%" + sSearch + "%").uniqueResult())
					.intValue();
			datas = getSession().createQuery(hql)
					.setParameterList("ids", projIds)
					.setParameter("name", "%" + sSearch + "%")
					.setParameter("fid", "%" + sSearch + "%")
					.setFirstResult(SystemContext.getOffset())
					.setMaxResults(SystemContext.getPagesize()).list();
		}

		PagerVO pv = new PagerVO();
		pv.setDatas(datas);
		pv.setTotal(total);

		return pv;
	}

	@Override
	public void addProject(Project project, int workflowId, int userId) {

		Workflow workflow = (Workflow) getSession().load(Workflow.class,
				workflowId);
		User creator = (User) getSession().load(User.class, userId);
		project.setWorkflow(workflow);
		project.setCreator(creator);
		project.setCreateTime(new Date());
		// project.setStatus(Project.STATUS_NEW);
		getSession().save(project);
	}

	@Override
	public PagerVO searchApproveInfos(int projectId) {
		// TODO 查看工程审批历史的时候需要显示的项按照实际要求来 by zhuqi 2013/6/27
		String hql = "select ai.approver.person.name, ai.comment, ai.rTime from ApproveInfo ai "
				+ "where ai.project.id = ? " + "order by ai.approveTime desc";
		return findPaginated(hql, projectId);
		// return getSession().createQuery(hql).setParameter(0,
		// projectId).list();
	}

	/*
	 * @Override public PagerVO searchApprovingProjects(int userId, String
	 * projectName) {// 需要使用workflowService.searchApprovingProjects无法上移到DAO层
	 * return null; }
	 */

	@Override
	public PagerVO searchApprovedProjects(int userId, String sSearch) {
		// TODO 显示看具体要求 ------------我的已审批工程
		String hql = "select ai.project.id, ai.project.fid, ai.project.proname, ai.project.status, ai.status, ai.rTime from ApproveInfo ai where ai.approver.id = ? and ( ai.project.proname like ? or ai.project.fid like ? )order by ai.approveTime desc";
		return findPaginated(hql, new Object[] { userId, "%" + sSearch + "%",
				"%" + sSearch + "%" });
	}

	@Override
	public PagerVO searchMyProjects(int userId, String sSearch) {
		// TODO 显示看具体要求 ------------我的工程
		String hql = "select p.id, p.fid, p.proname, p.status from Project p where p.creator.id = ? and ( p.proname like ? or p.fid like ? ) order by p.createTime desc";
		return findPaginated(hql, new Object[] { userId, "%" + sSearch + "%",
				"%" + sSearch + "%" });
	}

	@Override
	public PagerVO getAllProjects() {
		String hql = "select p.id, p.fid, p.proname, p.status from Project p order by p.id";
		return findPaginated(hql);
	}

	@Override
	public PagerVO getAllProjects(String sSearch) {
		String hql = "select p.id, p.fid, p.proname, p.status from Project p where p.proname like ? or p.fid like ? order by p.id";
		return findPaginated(hql, new Object[] { "%" + sSearch + "%",
				"%" + sSearch + "%" });
	}

	@Override
	public ApproveInfo getApprove(int projectId, String status) {
		String hql = "select ai from ApproveInfo ai where ai.project.id = ? and ai.status = ? order by ai.approveTime desc";
		List<ApproveInfo> infos = getSession().createQuery(hql)
				.setParameter(0, projectId).setParameter(1, status).list();
		if (infos != null && infos.size() > 0) {
			return infos.get(0);
		}
		return null;
	}

	@Override
	public List<Integer> getUserProjectByApprove(int userId) {
		String hql = "select ai.project.id from ApproveInfo ai where ai.approver.id = ?";
		return getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<ApproveInfo> getAllApproveInfos(int projectId) {
		String hql = "select ai from ApproveInfo ai where ai.project.id = ?";
		return getSession().createQuery(hql).setParameter(0, projectId).list();
	}

	@Override
	public List<Project> getAllProjectByToday() {
		Integer myYear = TimeUtil.getToday();
		String hql = "select p from Project p where p.myYear = ? order by p.fid";
		return getSession().createQuery(hql).setParameter(0, myYear).list();
	}

	@Override
	public List<ApproveInfo> getAllApproveInfosByUserId(int userId) {
		String hql = "select ai from ApproveInfo ai where ai.approver.id = ?";
		return getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public PagerVO findProjectByIds2(List projIds) {
		int total = 0;
		List datas = new ArrayList();
		if (projIds != null && projIds.size() > 0) {
			// TODO 自己确定需要显示的项
			String hql = "select new cn.zhuqi.oa.vo.ProjectVO(p.id, p.fid, p.proname, p.status) from Project p where p.id in (:ids) order by p.createTime desc";
			String countHql = getCountHql(hql);
			total = ((Long) getSession().createQuery(countHql)
					.setParameterList("ids", projIds).uniqueResult())
					.intValue();
			datas = getSession().createQuery(hql)
					.setParameterList("ids", projIds)
					.setFirstResult(SystemContext.getOffset())
					.setMaxResults(SystemContext.getPagesize()).list();
		}

		PagerVO pv = new PagerVO();
		pv.setDatas(datas);
		pv.setTotal(total);

		return pv;
	}

}
