package cn.zhuqi.mytest;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.excel.GenerateExcel;
import cn.zhuqi.oa.model.FieldMap;
import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.model.ZPayment;
import cn.zhuqi.oa.model.ZSequence;
import cn.zhuqi.oa.model.Zfile;
import cn.zhuqi.oa.service.FieldMapService;
import cn.zhuqi.oa.service.ProjectService;
import cn.zhuqi.oa.service.ZPaymentService;
import cn.zhuqi.oa.service.ZSequenceService;
import cn.zhuqi.oa.service.ZfileService;
import cn.zhuqi.system.ClassUtil;
import cn.zhuqi.system.FileNameUtil;
import cn.zhuqi.system.MulUtil;

public class ExcelTest extends TestCase {

	public void testgenerateExcel2003() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		ProjectService projectService = (ProjectService) context
				.getBean("projectService");
		Project project = new Project();
		project.setId(11);
		project = projectService.findProjectById(project.getId());
		System.out.println(project);
		long start = System.currentTimeMillis();
		GenerateExcel.generateExcel2003_2(project,
				FileNameUtil.getNewFileName("*.xls", 0));
		long end = System.currentTimeMillis();
		System.out.println((end - start) + " ms done!");

	}

	public void testaddZSequence() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		// ProjectService projectService = (ProjectService) context
		// .getBean("projectService");
		FieldMapService fieldMapService = (FieldMapService) context
				.getBean("fieldMapService");
		ZSequenceService zSequenceService = (ZSequenceService) context
				.getBean("zSequenceService");
		Project project = new Project();
		List<String> names = ClassUtil.getAllFields(project);
		System.out.println(names);
		for (String name : names) {
			FieldMap fieldMap = new FieldMap();
			fieldMap.setName(name);
			fieldMapService.addFieldMap(fieldMap);
			if (!name.contains("STATUS")) {
				ZSequence zSequence = new ZSequence();
				zSequence.setFieldMap(fieldMap);
				zSequence.setDelable('N');
				zSequenceService.addZSequence(zSequence);
			}
		}

		FieldMap fieldMap = new FieldMap();
		fieldMap.setDisplayName("序号");
		fieldMapService.addFieldMap(fieldMap);
		fieldMap.setName(String.valueOf(-fieldMap.getId()));
		fieldMapService.updateFieldMap(fieldMap);
		ZSequence zSequence = new ZSequence();
		zSequence.setFieldMap(fieldMap);
		zSequence.setDelable('N');
		zSequence.setEditable('N');
		zSequenceService.addZSequence(zSequence);
	}

	public void testListZSequence() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		// ProjectService projectService = (ProjectService) context
		// .getBean("projectService");
		ZSequenceService zSequenceService = (ZSequenceService) context
				.getBean("zSequenceService");
		ZfileService zfileService = (ZfileService) context
				.getBean("zfileService");
		ZPaymentService zPaymentService = (ZPaymentService) context
				.getBean("zpaymentService");
		List<FieldMap> fieldMaps = zSequenceService.getCustomSequenceFields();
		// List<ZSequence> sequences =
		// zSequenceService.getCustomSequenceFields();
		Project project = new Project();
		project.setFid("JL2013001");
		project.setProname("江南壹諕一期");
		project.setCreateTime(new Date());
		project.setDeveloper("吉林市怡恒伟业房地产开发有限公司");
		project.setArea("1253");
		project.setFee("1234543");
		project.setSfee(MulUtil.mul(project.getArea(), project.getFee()));

		for (FieldMap fieldMap : fieldMaps) {
			System.out.println(fieldMap.getId() + " -- " + fieldMap.getName());
			Object value = ClassUtil.getFieldValue(project, fieldMap.getName());
			if (value == null) {
				try {
					int zPaymentId = Integer.parseInt(fieldMap.getName());
					if (zPaymentId > 0) {
						ZPayment zPayment = zPaymentService
								.findZPaymentById(zPaymentId);
						// Zfile zfile = zfileService.findZfileById(zfileId);
						System.out.println(zPayment.getName());
					} else {
						System.out.println(value);
					}
				} catch (NumberFormatException e) {
					System.out.println(value);
				}
			} else {
				System.out.println(value);
			} //
			System.out.println(ClassUtil.getFieldValue(project, //
					fieldMap.getName()));
		}

	}

	public void testAddZPayment_ZSequence() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		// ProjectService projectService = (ProjectService) context
		// .getBean("projectService");
		FieldMapService fieldMapService = (FieldMapService) context
				.getBean("fieldMapService");
		ZSequenceService zSequenceService = (ZSequenceService) context
				.getBean("zSequenceService");

		ZPaymentService zPaymentService = (ZPaymentService) context
				.getBean("zpaymentService");

		/*
		 * ZfileService zfileService = (ZfileService) context
		 * .getBean("zfileService"); List<Zfile> zfiles =
		 * zfileService.findAllZfiles(); for (Zfile zfile : zfiles) {
		 * System.out.println(zfile.getName()); FieldMap fieldMap = new
		 * FieldMap(); String name = String.valueOf(zfile.getId());
		 * fieldMap.setName(name); fieldMapService.addFieldMap(fieldMap);
		 * 
		 * ZSequence zSequence = new ZSequence();
		 * zSequence.setFieldMap(fieldMap);
		 * zSequenceService.addZSequence(zSequence); }
		 */

		List<ZPayment> zPayments = zPaymentService.findAllZPayments();
		for (ZPayment zPayment : zPayments) {
			FieldMap fieldMap = new FieldMap();
			String name = String.valueOf(zPayment.getId());
			fieldMap.setName(name);
			fieldMap.setDisplayName(zPayment.getName());
			fieldMapService.addFieldMap(fieldMap);
			ZSequence zSequence = new ZSequence();
			zSequence.setFieldMap(fieldMap);
			zSequence.setDelable('N');
			zSequenceService.addZSequence(zSequence);
		}
	}

	public void testInitMyZSequence() {
		testaddZSequence();
		testAddZPayment_ZSequence();
	}
}
