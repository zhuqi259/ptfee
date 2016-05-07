package cn.zhuqi.oa.web.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.Rule;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.model.ZSequence;
import cn.zhuqi.oa.service.RuleService;
import cn.zhuqi.system.IdUtil;
import cn.zhuqi.system.MD5Util;

import com.opensymphony.xwork2.ModelDriven;

@Controller("ruleAction")
@Scope("prototype")
public class RuleAction extends BaseAction implements ModelDriven {

	@Resource
	private RuleService ruleService;

	private Rule rule;

	@Override
	public Object getModel() {
		if (rule == null) {
			rule = new Rule();
		}
		return rule;
	}

	public String execute() {
		return "index";
	}

	public List<Rule> getAllRules() {
		return ruleService.getAllRules();
	}

	/**
	 * 判断是否选中
	 * 
	 * @return
	 */
	public String hasChecked(int ruleId) {
		Rule rule = ruleService.findRuleById(ruleId);
		if (rule.getInUse() == 'Y') {
			return "checked";
		}
		return "";
	}

	public void updateUsed() {
		List<Rule> rules = getAllRules();
		for (Rule aRule : rules) {
			aRule.setInUse('N');
			ruleService.updateRule(aRule);
		}
		rule = ruleService.findRuleById(rule.getId());
		rule.setInUse('Y');
		ruleService.updateRule(rule);
	}

	public String addInput() {
		return "add_input";
	}

	public String add() {
		String example;
		try {
			example = IdUtil.getSampleId(rule);
			rule.setExample(example);
			ruleService.addRule(rule);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return "index";
	}

	public void check() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String example = IdUtil.getSampleId(rule);
			if (example == null || example.equals("")) {
				out.print("<font color='red'>规则无效 - 不能为空</font>");
			} else {
				out.print("<font color='blue'>规则有效，示例：" + example + "</font>");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			out.print("<font color='red'>规则无效 - " + e.getMessage() + "</font>");
		}
	}
}
