package cn.zhuqi.oa.test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.model.Menu;
import cn.zhuqi.oa.service.MenuService;

public class InitMenuTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		MenuService menuService = (MenuService) context.getBean("menuService");

		Menu menu11 = new Menu();
		menu11.setName("创建新的流程定义");
		menu11.setOrderNumber(211);
		menu11.setHref("system/workflow!addInput.action");
		menuService.addMenu(menu11);

		Menu menu12 = new Menu();
		menu12.setName("查询已有流程定义");
		menu12.setOrderNumber(212);
		menu11.setHref("system/workflow.action");
		menuService.addMenu(menu12);

		Set<Menu> child1 = new HashSet<Menu>();
		child1.add(menu11);
		child1.add(menu12);

		Menu menu1 = new Menu();
		menu1.setName("流程定义");
		menu1.setOrderNumber(21);
		menu1.setChildren(child1);
		menuService.addMenu(menu1);

		Set<Menu> child = new HashSet<Menu>();
		child.add(menu1);

		Menu menu = new Menu();
		menu.setName("工作流");
		menu.setSn("workflow");
		menu.setOrderNumber(2);

		menu.setChildren(child);

		menuService.addMenu(menu);

		List<Menu> mlist = menuService.findAllTopMenus();

		for (Iterator it = mlist.iterator(); it.hasNext();) {
			Menu mtmp = (Menu) it.next();
			System.out.println(mtmp.getId() + "-----" + mtmp.getName());
		}

	}
}
