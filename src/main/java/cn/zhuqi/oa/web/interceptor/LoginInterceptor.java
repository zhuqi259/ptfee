package cn.zhuqi.oa.web.interceptor;

import org.apache.struts2.ServletActionContext;

import cn.zhuqi.oa.vo.LoginInfoVO;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class LoginInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		LoginInfoVO currentUser = (LoginInfoVO) ServletActionContext
				.getRequest().getSession().getAttribute("login");

		if (currentUser == null) {
			return "login";
		}

		// 继续向下执行
		return invocation.invoke();
	}

}
