package cn.zhuqi.oa.web.actions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.annotations.Oper;
import cn.zhuqi.oa.annotations.Res;
import cn.zhuqi.oa.model.Menu;
import cn.zhuqi.oa.service.MenuService;
import cn.zhuqi.oa.vo.MenuTreeVO;
import cn.zhuqi.oa.web.JSONUtils;

import com.opensymphony.xwork2.ModelDriven;

@Controller("menuAction")
@Scope("prototype")
@Res(name = "菜单操作", sn = "menu", orderNumber = 90, parentSn = "security")
public class MenuAction extends BaseAction implements ModelDriven {

	@Resource
	private MenuService menuService;

	private Menu menu;

	@Override
	public Object getModel() {
		if (menu == null) {
			menu = new Menu();
		}
		return menu;
	}

	/**
	 * 打开菜单管理的主界面
	 * 
	 * @return
	 */
	@Oper
	public String execute() {
		return "index";
	}

	/**
	 * jsTree请求菜单树
	 */
	public void tree() {
		List<Menu> menus = menuService.findAllTopMenus();
		List<MenuTreeVO> vos = new ArrayList<MenuTreeVO>();
		for (Menu topMenu : menus) {
			vos.add(new MenuTreeVO(topMenu));
		}
		JSONUtils.toJSON(vos);
	}

	@Oper
	public String addInput() {
		return "add_input";
	}

	@Oper
	public String add() {
		menuService.addMenu(menu);
		return "add_success";
	}

	@Oper
	public String updateInput() {
		menu = menuService.findMenuById(menu.getId());
		if (menu.getName().equals("待办任务")) {
			throw new RuntimeException("[待办任务]菜单禁止修改!!");
		}
		return "update_input";
	}

	@Oper
	public String update() {
		menuService.updateMenu(menu);
		return "update_success";
	}

	@Oper
	public String del() {
		menu = menuService.findMenuById(menu.getId());
		if (menu.getName().equals("待办任务")) {
			throw new RuntimeException("[待办任务]菜单禁止删除!!");
		}
		menuService.delMenu(menu.getId());
		return "del_success";
	}
}
