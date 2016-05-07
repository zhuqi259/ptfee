package cn.zhuqi.useful.test;

import java.util.Iterator;
import java.util.List;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.RepositoryService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.service.WorkflowService;
import cn.zhuqi.oa.service.ZActivityService;

public class FlowTest {

	@Test
	public void testdelFlow2() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		RepositoryService repositoryService = (RepositoryService) context
				.getBean("repositoryService");

		List<ProcessDefinition> pdfs = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey("配套费项目演示版本").list();

		System.out.println("------------------------");
		System.out.println("------------------------");
		System.out.println("------------------------");
		for (Iterator<ProcessDefinition> iter = pdfs.iterator(); iter.hasNext();) {
			ProcessDefinition pdf = iter.next();
			// 级联删除流程定义及其产生的所有流程实例
			System.out.println(pdf.getDeploymentId());
			// TODO 暂时用的方法是级联删除，注意以后修改(按具体情况而定)... by zhuqi
			repositoryService.deleteDeploymentCascade(pdf.getDeploymentId());
		}

	}
	
	@Test
	public void testdelFlow() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		RepositoryService repositoryService = (RepositoryService) context
				.getBean("repositoryService");

		List<ProcessDefinition> pdfs = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey("配套费项目").list();

		System.out.println("------------------------");
		System.out.println("------------------------");
		System.out.println("------------------------");
		for (Iterator<ProcessDefinition> iter = pdfs.iterator(); iter.hasNext();) {
			ProcessDefinition pdf = iter.next();
			// 级联删除流程定义及其产生的所有流程实例
			System.out.println(pdf.getDeploymentId());
			// TODO 暂时用的方法是级联删除，注意以后修改(按具体情况而定)... by zhuqi
			repositoryService.deleteDeploymentCascade(pdf.getDeploymentId());
		}

	}

}
