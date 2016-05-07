package cn.zhuqi.oa.test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.mytest.MakeOrderNO;
import cn.zhuqi.oa.model.Rule;
import cn.zhuqi.oa.service.IdTableService;
import cn.zhuqi.oa.service.RuleService;
import cn.zhuqi.system.IdUtil;

public class IdTest extends TestCase {

	public void testgenerateId() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		IdTableService idTableService = (IdTableService) context
				.getBean("idTableService");
		RuleService ruleService = (RuleService) context.getBean("ruleService");
		System.out.println(IdUtil.generateId(idTableService, ruleService));
	}

	public void testgetOrderNo() throws SQLException {
		System.out.println(MakeOrderNO.getOrderNo("zhuqi", "1", "2"));
	}

	public void testDate() {
		String pattern = "hh";
		String result = new SimpleDateFormat(pattern).format(new Date());
		System.out.println(result);
	}

	public void testgenerateId2() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		RuleService ruleService = (RuleService) context.getBean("ruleService");
		Rule rule = ruleService.getSingle();
		String example = IdUtil.getSampleId(rule);
		rule.setExample(example);
		System.out.println(example);
		ruleService.updateRule(rule);
	}
}
