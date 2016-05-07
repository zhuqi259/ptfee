package cn.zhuqi.oa.dao.impl;

import org.springframework.stereotype.Repository;

import cn.zhuqi.oa.dao.RuleDao;
import cn.zhuqi.oa.model.Rule;

@Repository("ruleDao")
public class RuleDaoImpl extends BaseDaoImpl implements RuleDao {

	@Override
	public Rule getSingle() {
		return (Rule) getSession().createQuery(
				"select r from Rule r where r.inUse='Y'").uniqueResult();
	}

	@Override
	public void initRule() {
		getSession().createQuery("delete from Rule r").executeUpdate();
		Rule rule = new Rule();
		rule.setIrule("JL,yyyy,$3");
		rule.setInUse('Y');
		getSession().save(rule);
	}

}
