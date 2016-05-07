package cn.zhuqi.useful.test;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.service.IdTableService;
import cn.zhuqi.oa.service.InitService;
import cn.zhuqi.oa.service.RuleService;
import cn.zhuqi.oa.service.ZActivityService;

public class InitServiceTest extends TestCase {

	public void testaddInitDatas() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		InitService initService = (InitService) context.getBean("initService");
		initService.addInitDatas();
		initService.addInitUser();
		// ID生成器
		IdTableService idTableService = (IdTableService) context
				.getBean("idTableService");
		idTableService.initId();
		RuleService ruleService = (RuleService) context.getBean("ruleService");
		ruleService.initRule();

		// 节点初始化
//		 ZActivityService activityService = (ZActivityService) context
//		 .getBean("zactivityService");
//		 activityService.initActivity();
	}
}
