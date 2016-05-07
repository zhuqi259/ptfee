package cn.zhuqi.oa.test;

import org.jbpm.api.TaskService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class TaskTest extends TestCase {

	public void testCompleteTask(){
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		TaskService taskService = (TaskService) context.getBean("taskService");
	//	taskService.completeTask("11");
		taskService.completeTask("10009");
	}

}
