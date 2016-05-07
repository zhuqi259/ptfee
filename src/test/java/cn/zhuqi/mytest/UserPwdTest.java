package cn.zhuqi.mytest;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.model.Person;
import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.service.UserService;
import cn.zhuqi.system.MD5Util;

public class UserPwdTest extends TestCase {

	public void testListUser() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		UserService userService = (UserService) context.getBean("userService");
		List<User> users = userService.findAllUsers();
		for (User user : users) {
			System.out.println(user.getUsername() + " : " + user.getPassword());
		}
	}

	public void testMd5UserPwd() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		UserService userService = (UserService) context.getBean("userService");
		List<User> users = userService.findAllUsers();
		for (User user : users) {
			if (user.getPassword() != null) {
				user.setPassword(MD5Util.Str2Md5_32(user.getPassword()));
				userService.updateUser(user);
			}
			System.out.println(user.getUsername() + " : " + user.getPassword());
		}
	}

	public void testMd5() {
		System.out.println(MD5Util.Str2Md5_32("1"));
		System.out.println(MD5Util.Str2Md5_32("2"));
		System.out.println(MD5Util.Str2Md5_32("3"));
		System.out.println(MD5Util.Str2Md5_32("4"));
		System.out.println(MD5Util.Str2Md5_32("5"));
		System.out.println(MD5Util.Str2Md5_32("6"));
		System.out.println(MD5Util.Str2Md5_32("7"));
		System.out.println(MD5Util.Str2Md5_32("admin"));
		System.out.println(MD5Util.Str2Md5_32("boss"));
		System.out.println(MD5Util.Str2Md5_32(""));
	}
}
