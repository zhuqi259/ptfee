package cn.zhuqi.oa.test;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.service.ResourceService;

public class ResourceServiceTest extends TestCase {
	BeanFactory factory = new ClassPathXmlApplicationContext(
			"classpath*:config/applicationContext-*.xml");

	// 测试重构所有的类资源
	public void testRebuildActionResources() {
		ResourceService rs = (ResourceService) factory
				.getBean("resourceService");
		rs.rebuildActionResources();
	}
}
