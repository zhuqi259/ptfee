package cn.zhuqi.useful.test;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RejectTest extends TestCase {

	protected ProcessEngine processEngine;
	protected ExecutionService executionService;
	protected TaskService taskService;

	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		processEngine = (ProcessEngine) context.getBean("processEngine");
		executionService = (ExecutionService) context
				.getBean("executionService");
		taskService = (TaskService) context.getBean("taskService");
	}

	/**
	 * 驳回
	 * 
	 * @param task
	 *            当前执行的任务
	 * @param destActivityName
	 *            需要流转到的目的节点
	 * @param createTransitionName
	 *            动态生成的transition的名称
	 */
	protected void reject(Task task, String destActivityName,
			String createTransitionName) {
		// 这里不会影响事物
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		EnvironmentImpl envImpl = environmentFactory.openEnvironment();
		try {
		//	envImpl = environmentFactory.openEnvironment();
			
			Session session = envImpl.getFromCurrent(Session.class);
			session.beginTransaction();			
			// 动态回退到“窗口收件”
			ExecutionImpl e = (ExecutionImpl) executionService
					.findExecutionById(task.getExecutionId());
			ActivityImpl clerkOpinionActivityImpl = e.getActivity();

			ProcessDefinitionImpl processDefinitionImpl = clerkOpinionActivityImpl
					.getProcessDefinition();
			// 生成一个"经办人意见"——>"窗口收件"的transition
			ActivityImpl applyActivityImpl = processDefinitionImpl
					.findActivity(destActivityName);
			TransitionImpl toApply = clerkOpinionActivityImpl
					.createOutgoingTransition();
			toApply.setSource(clerkOpinionActivityImpl);
			toApply.setDestination(applyActivityImpl);
			toApply.setName(createTransitionName);
			this.taskService.completeTask(task.getId(), createTransitionName);
			
			//提交事务  org.hibernate.Session  
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (envImpl != null)
				envImpl.close();
		}
	}

	public void testReject() {
		init();
		Task myTask = taskService.getTask("1170037");
		System.out.println(myTask.getActivityName());
		String destActivityName = "工程受理";
		String createTransitionName = "驳回原籍";
		reject(myTask, destActivityName, createTransitionName);
	}
}
