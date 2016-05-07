package cn.zhuqi.oa.test;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.service.ZActivityService;

public class ActivityTest extends TestCase {

	public void testInitActivity() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		ZActivityService activityService = (ZActivityService) context
				.getBean("zactivityService");
		activityService.initActivity();
	}
}
