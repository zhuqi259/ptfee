package cn.zhuqi.oa.dao;

import cn.zhuqi.oa.model.Workflow;
import cn.zhuqi.oa.vo.PagerVO;

public interface WorkflowDao extends BaseDao {

	public Workflow findWorkflowByName(String name);

	/**
	 * 得到所有的工作流
	 * 
	 * @return 返回工作流ID和名称
	 */
	public PagerVO getAllWorkflow();

}
