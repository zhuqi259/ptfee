package cn.zhuqi.oa.web.actions;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.User;
import cn.zhuqi.oa.service.UserService;
import cn.zhuqi.oa.vo.LoginInfoVO;

@Controller("loginAction")
@Scope("prototype")
public class LoginAction {

	private String username;
	private String password;

	@Resource
	private UserService userService;

	// 执行登录操作
	public String execute() {
		User user = userService.login(username, password);

		LoginInfoVO vo = new LoginInfoVO();
		vo.setId(user.getId());
		vo.setLoginTime(new Date());
		vo.setName(user.getPerson().getName());
		vo.setUsername(user.getUsername());
		try {
			vo.setPosition(user.getPerson().getParent().getName());
		} catch (Exception e) {
			vo.setPosition(user.getPerson().getName());
		}
		vo.setIp(ServletActionContext.getRequest().getRemoteHost());

		ServletActionContext.getRequest().getSession()
				.setAttribute("login", vo);
		return "back_index";
	}

	public String quit() {
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
