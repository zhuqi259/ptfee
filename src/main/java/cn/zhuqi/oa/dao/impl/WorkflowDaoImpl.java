package cn.zhuqi.oa.dao.impl;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.WorkflowDao;
import cn.zhuqi.oa.model.Workflow;
import cn.zhuqi.oa.vo.PagerVO;

@Repository("workflowDao")
public class WorkflowDaoImpl extends BaseDaoImpl implements WorkflowDao {

	@Override
	public Workflow findWorkflowByName(String name) {
		return (Workflow) getSession()
				.createQuery("select w from Workflow w where w.name = ?")
				.setParameter(0, name).uniqueResult();
	}

	@Override
	public PagerVO getAllWorkflow() {
		return findPaginated("select w.id, w.name from Workflow w");
	}



}
