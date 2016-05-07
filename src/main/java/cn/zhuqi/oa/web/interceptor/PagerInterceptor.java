package cn.zhuqi.oa.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.zhuqi.system.SystemContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class PagerInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			// 取出分页参数，设置到ThreadLocal中
			SystemContext.setOffset(getOffset(request));
			SystemContext.setPagesize(getPagesize(request));

			return invocation.invoke();

		} finally {
			SystemContext.removeOffset();
			SystemContext.removePagesize();
		}
	}

	protected int getOffset(HttpServletRequest request) {
		int offset = 0;
		// 希望从request中获得offset参数
		try {
			offset = Integer.parseInt(request.getParameter("iDisplayStart"));
		} catch (Exception ignore) {
		}
		return offset;
	}

	protected int getPagesize(HttpServletRequest request) {
		int pagesize = 5;

		// 如果从request传递过来了pagesize参数，那么就需要更新http session中的pagesize的值
		if (request.getParameter("iDisplayLength") != null) {
			request.getSession().setAttribute("iDisplayLength",
					Integer.parseInt(request.getParameter("iDisplayLength")));
		}

		// 希望从http session中获得pagesize的值，如果没有，则设置缺省值为5
		Integer ps = (Integer) request.getSession().getAttribute(
				"iDisplayLength");
		if (ps == null) {
			request.getSession().setAttribute("iDisplayLength", pagesize);
		} else {
			pagesize = ps;
		}
		return pagesize;
	}

}
