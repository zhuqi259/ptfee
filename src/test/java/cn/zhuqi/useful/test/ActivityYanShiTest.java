package cn.zhuqi.useful.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.model.Workflow;
import cn.zhuqi.oa.model.ZActivity;
import cn.zhuqi.oa.service.WorkflowService;
import cn.zhuqi.oa.service.ZActivityService;
import cn.zhuqi.system.ActivityUtil;
import cn.zhuqi.system.IOUtil;
import cn.zhuqi.system.InputStreamUtils;

public class ActivityYanShiTest {

	private Map<String, String> getXMLNode(InputStream inputStream) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(inputStream);
			Element rootElement = doc.getRootElement();
			List<Element> elements = (List<Element>) rootElement.elements();
			for (Element element : elements) {
				String tagName = element.getName();
				if (tagName.equals("task")) {
					Attribute name = element.attribute("name");
					Attribute assignee = element.attribute("assignee");
					if (null != name && null != assignee) {
						result.put(name.getValue(), assignee.getValue());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Test
	public void testDeploy_10_28_noneed() throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		System.out.println("部署-------------------------");
		RepositoryService repositoryService = (RepositoryService) context
				.getBean("repositoryService");
		ZActivityService activityService = (ZActivityService) context
				.getBean("zactivityService");
		WorkflowService workflowService = (WorkflowService) context
				.getBean("workflowService");

		ZipInputStream zis = new ZipInputStream(this.getClass()
				.getResourceAsStream(
						"/cn/zhuqi/oa/jbpm/latest/ptfeeYanshi_12_4.zip"));
		String deploymentId = repositoryService.createDeployment()
				.addResourcesFromZipInputStream(zis).deploy();
		System.out.println("发布成功，版本号为:========" + deploymentId);
		System.out.println("Success---------------------");

		ProcessDefinition pdf = repositoryService
				.createProcessDefinitionQuery().deploymentId(deploymentId)
				.uniqueResult();

		String key = pdf.getKey();
		System.out.println("key--------------------->>>" + key);
		String definitionId = pdf.getId();
		System.out.println("definitionId--------------------->>>"
				+ definitionId);

		String resourceName = pdf.getImageResourceName();
		// 获取流程定义图资源
		InputStream is = repositoryService.getResourceAsStream(deploymentId,
				resourceName);

		byte[] processImg = null;
		try {
			processImg = IOUtil.InputStreamTOByte(is);
		} catch (IOException e) {
			throw new RuntimeException("流程发布失败2，请查看定义Zip文件是否正确!");
		}

		Workflow workflow = workflowService.findWorkflowByName("配套费项目");
		if (workflow == null) {
			throw new RuntimeException("流程未定义！！");
		}
		workflow.setProcessImage(processImg);
		workflowService.update(workflow);

		String fileName = resourceName.replace(".png", ".jpdl.xml");
		InputStream inxml = repositoryService.getResourceAsStream(deploymentId,
				fileName);

		byte[] xml = InputStreamUtils.InputStreamTOByte(inxml);
		try {
			InputStream in1 = new ByteArrayInputStream(xml);
			System.out.println(InputStreamUtils.InputStreamTOString(in1,
					"UTF-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		InputStream in2 = new ByteArrayInputStream(xml);
		Map<String, String> nodeList = getXMLNode(in2);
		System.out.println(nodeList);

		// 修改activity x,y,width,height
		ProcessDefinitionImpl definitionimpl = (ProcessDefinitionImpl) pdf;
		List<? extends Activity> list = definitionimpl.getActivities();
		for (Activity activity : list) {
			String activityName = activity.getName();

			if (ActivityUtil.IsActivity(activityName)) {
				// System.out.println(activityName);
				ZActivity zz = activityService.findActivityByName(activityName);
				// System.out.println(zz.getId() + "->" + zz.getName());
				ActivityCoordinates ac = repositoryService
						.getActivityCoordinates(definitionId, activityName);
				// BeanUtils.copyProperties(zz, ac);
				try {
					PropertyUtils.copyProperties(zz, ac);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("x:" + zz.getX() + " y:" + zz.getY() + " w"
						+ zz.getWidth() + " h" + zz.getHeight());
				String owner = nodeList.get(activityName);
				zz.setOwner(owner);
				activityService.update(zz);
			}
		}
	}

	@Test
	public void testDeploy_12_4() throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		System.out.println("部署-------------------------");
		RepositoryService repositoryService = (RepositoryService) context
				.getBean("repositoryService");
		WorkflowService workflowService = (WorkflowService) context
				.getBean("workflowService");

		ZipInputStream zis = new ZipInputStream(this.getClass()
				.getResourceAsStream(
						"/cn/zhuqi/oa/jbpm/latest/ptfeeYanshi_12_4.zip"));
		String deploymentId = repositoryService.createDeployment()
				.addResourcesFromZipInputStream(zis).deploy();
		System.out.println("发布成功，版本号为:========" + deploymentId);
		System.out.println("Success---------------------");

		ProcessDefinition pdf = repositoryService
				.createProcessDefinitionQuery().deploymentId(deploymentId)
				.uniqueResult();

		String key = pdf.getKey();
		System.out.println("key--------------------->>>" + key);
		String definitionId = pdf.getId();
		System.out.println("definitionId--------------------->>>"
				+ definitionId);

		String resourceName = pdf.getImageResourceName();
		// 获取流程定义图资源
		InputStream is = repositoryService.getResourceAsStream(deploymentId,
				resourceName);

		byte[] processImg = null;
		try {
			processImg = IOUtil.InputStreamTOByte(is);
		} catch (IOException e) {
			throw new RuntimeException("流程发布失败2，请查看定义Zip文件是否正确!");
		}

		Workflow workflow = workflowService.findWorkflowByName("配套费项目");
		if (workflow == null) {
			throw new RuntimeException("流程未定义！！");
		}
		workflow.setProcessImage(processImg);
		workflowService.update(workflow);
	}
}
