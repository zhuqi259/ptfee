package cn.zhuqi.oa.dao;

import cn.zhuqi.oa.model.Rule;

public interface RuleDao extends BaseDao {
	/**
	 * 找到标记为Y的那条规则
	 * 
	 * @return
	 */
	public Rule getSingle();

	/**
	 * 初始化一条规则
	 */
	public void initRule();

}
