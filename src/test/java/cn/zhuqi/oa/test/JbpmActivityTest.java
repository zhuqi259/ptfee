package cn.zhuqi.oa.test;

import java.util.List;

import junit.framework.TestCase;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.model.Activity;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JbpmActivityTest extends TestCase {

	public void testJbpmActivity() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");

		RepositoryService repositoryService = (RepositoryService) context
				.getBean("repositoryService");
		String definitionId = "配套费项目-2";
		ProcessDefinition definition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(definitionId).uniqueResult();
		ProcessDefinitionImpl definitionimpl = (ProcessDefinitionImpl) definition;

		List<? extends Activity> list = definitionimpl.getActivities();
		
		for (Activity activity : list) {

			System.out.println(activity.getName());

		}
	}

}
