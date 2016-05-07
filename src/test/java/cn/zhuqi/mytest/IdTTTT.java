package cn.zhuqi.mytest;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.service.IdTableService;
import cn.zhuqi.oa.service.RuleService;

public class IdTTTT extends TestCase {

	public void testID() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		IdTableService idTableService = (IdTableService) context
				.getBean("idTableService");
		idTableService.initId();
		RuleService ruleService = (RuleService) context.getBean("ruleService");
		ruleService.initRule();
	}
}
