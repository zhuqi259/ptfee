package cn.zhuqi.useful.test;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.apache.struts2.ServletActionContext;

public class FileTest extends TestCase {

	public void testReadBase() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String serverRealPath = request.getSession().getServletContext()
				.getRealPath("/");
		System.out.println(serverRealPath);
	}
}
