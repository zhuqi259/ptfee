package cn.zhuqi.useful.test;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.service.ProjectService;

public class ProjectTest extends TestCase {

	public void testDelProject() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		ProjectService projectService = (ProjectService) context
				.getBean("projectService");
		List<Project> projects = projectService.getAllProjectByTest();
		System.out.println(projects);
		for (Project project : projects) {
			if (!Project.STATUS_FINISH.equals(project.getStatus())) {
				System.out.println(project.getId());
				projectService.delRealProject(project.getId());
			}
		}
	}

	public void testDelAllProject() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		ProjectService projectService = (ProjectService) context
				.getBean("projectService");
		List<Project> projects = projectService.getAllProjectByTest();
		System.out.println(projects);
		for (Project project : projects) {
			System.out.println(project.getId());
			projectService.delRealProject(project.getId());
		}
	}
}
