package cn.zhuqi.oa.service;

import java.util.List;

import cn.zhuqi.oa.model.Rule;

public interface RuleService {
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

	/**
	 * 得到所有规则，便于管理
	 * 
	 * @return
	 */
	public List<Rule> getAllRules();

	public Rule findRuleById(int ruleId);
	
	public void addRule(Rule rule);
	
	public void updateRule(Rule rule);
	
}
