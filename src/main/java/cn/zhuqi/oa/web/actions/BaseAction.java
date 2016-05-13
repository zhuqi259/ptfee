package cn.zhuqi.oa.web.actions;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import cn.zhuqi.oa.service.AclService;
import cn.zhuqi.oa.vo.LoginInfoVO;

public class BaseAction {

	@Resource
	private AclService aclService;

	protected String sSearch;

	/**
	 * 从HTTP SESSION中取出已登录的User对象信息
	 * 
	 * @return
	 */
	protected LoginInfoVO currentUser() {
		return (LoginInfoVO) ServletActionContext.getRequest().getSession()
				.getAttribute("login");
	}

	public boolean permit(String resourceSn, String operSn) {
		try {
			return aclService.hasPermission(currentUser().getId(), resourceSn,
					operSn);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String search) throws UnsupportedEncodingException {
		sSearch = new String(search.getBytes("ISO-8859-1"), "UTF-8");
	}

}
