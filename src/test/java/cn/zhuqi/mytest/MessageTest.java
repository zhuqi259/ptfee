package cn.zhuqi.mytest;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.model.Message;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.service.MessageService;
import cn.zhuqi.oa.service.UserService;
import cn.zhuqi.oa.vo.PagerVO;

public class MessageTest extends TestCase {

	public void testAddMessage() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		MessageService messageService = (MessageService) context
				.getBean("messageService");
		UserService userService = (UserService) context.getBean("userService");

		Message message = new Message();

		message.setTitle("财务提醒");
		message.setContent("【财务提醒】要交钱了。。");

		User from = userService.findUserByUsername("system");
		message.setUfr(from);
		
		User to = userService.findUserByUsername("1");
		message.setUto(to);
		message.setTime(new Date());
		messageService.addMessage(message);

	}

	public void testfindAllReadingMessage() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		MessageService messageService = (MessageService) context
				.getBean("messageService");
		UserService userService = (UserService) context.getBean("userService");
		User to = userService.findUserByUsername("1");
		PagerVO vo = messageService.findAllReadingMessage(to.getId());
		System.out.println("total------" + vo.getTotal());
	}

	public void testfindAllReceiveMessage() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		MessageService messageService = (MessageService) context
				.getBean("messageService");
		UserService userService = (UserService) context.getBean("userService");
		User to = userService.findUserByUsername("1");
		PagerVO vo = messageService.findAllReceiveMessage(to.getId());
		System.out.println("total------" + vo.getTotal());
	}

	public void testfindAllSendMessage() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		MessageService messageService = (MessageService) context
				.getBean("messageService");
		UserService userService = (UserService) context.getBean("userService");
		User to = userService.findUserByUsername("1");
		PagerVO vo = messageService.findAllSendMessage(to.getId());
		System.out.println("total------" + vo.getTotal());
	}

	public void testdelMessage() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		MessageService messageService = (MessageService) context
				.getBean("messageService");
		messageService.delMessage(1, 2);
	}

}
