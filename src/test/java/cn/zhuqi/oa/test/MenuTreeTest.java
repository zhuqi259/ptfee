package cn.zhuqi.oa.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhuqi.oa.model.Menu;
import cn.zhuqi.oa.service.AclService;
import cn.zhuqi.oa.vo.AuthMenuTreeVO;

public class MenuTreeTest extends TestCase {

	public void testMenuTree() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:config/applicationContext-*.xml");
		AclService aclService = (AclService) context.getBean("aclService");
		List<Menu> authMenus = aclService.findPermitMenus(63);
		List<AuthMenuTreeVO> vos = new ArrayList<AuthMenuTreeVO>();
		for (Menu m : authMenus) {
			AuthMenuTreeVO vo = new AuthMenuTreeVO(m);
			System.out.println(vo.getData().get("title"));
		}
	}
}
