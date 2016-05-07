package cn.zhuqi.oa.test;

import java.util.Random;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.model.Company;
import cn.zhuqi.oa.model.Department;
import cn.zhuqi.oa.model.Party;
import cn.zhuqi.oa.model.Person;
import cn.zhuqi.oa.model.Position;
import cn.zhuqi.oa.service.PartyService;

public class PartyServiceTest extends TestCase {

	BeanFactory factory = new ClassPathXmlApplicationContext(
			"classpath*:config/applicationContext-*.xml");

	public void testAddParty() {
		PartyService ps = (PartyService) factory.getBean("partyService");

		Company c = new Company();
		c.setName("北京领航致远科技有限公司");
		ps.addParty(c);

		Department d = new Department();
		d.setName("技术部");
		d.setParent(c);
		ps.addParty(d);

		Position p = new Position();
		p.setName("高级工程师");
		p.setParent(d);
		ps.addParty(p);

		Person person = new Person();
		person.setName("张三");
		person.setParent(p);
		ps.addParty(person);

	}

	public void testAddSomePersons() {
		PartyService ps = (PartyService) factory.getBean("partyService");

		Party parent = new Party();
		parent.setId(2);

		for (int i = 0; i < 100; i++) {
			Person p = new Person();
			p.setName("测试人员" + new Random().nextInt(9999));
			p.setSex("男");
			p.setPhone("8347837438");
			p.setDescription("kdjfdkjf");

			p.setParent(parent);

			ps.addParty(p);
		}

	}

}
