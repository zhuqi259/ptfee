package cn.zhuqi.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zhuqi.oa.dao.RuleDao;
import cn.zhuqi.oa.model.Rule;
import cn.zhuqi.oa.service.RuleService;

@Service("ruleService")
public class RuleServiceImpl implements RuleService {

	@Resource
	private RuleDao ruleDao;

	@Override
	public Rule getSingle() {
		return ruleDao.getSingle();
	}

	@Override
	public void initRule() {
		ruleDao.initRule();
	}

	@Override
	public List<Rule> getAllRules() {
		return ruleDao.findAll(Rule.class);
	}

	@Override
	public Rule findRuleById(int ruleId) {
		return ruleDao.findById(Rule.class, ruleId);
	}

	@Override
	public void addRule(Rule rule) {
		ruleDao.save(rule);
	}

	@Override
	public void updateRule(Rule rule) {
		ruleDao.update(rule);
	}

}
