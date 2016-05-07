package cn.zhuqi.oa.web.actions;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.Database;
import cn.zhuqi.oa.model.Zfile;
import cn.zhuqi.oa.service.DatabaseService;
import cn.zhuqi.oa.service.ZfileService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller("zfileAction")
@Scope("prototype")
public class ZfileAction extends BaseAction implements ModelDriven {

	@Resource
	private ZfileService zfileService;

	@Resource
	private DatabaseService databaseService;

	@Override
	public Object getModel() {
		if (zfile == null) {
			zfile = new Zfile();
		}
		return zfile;
	}

	private Zfile zfile;

	public String addInput() {

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);

		return "add_input";
	}

	// 判断databaseId是否在所属的selecteddatabaseIds列表中
	public String hasSelected(int databaseId, Integer selecteddatabaseId) {
		if (selecteddatabaseId == null) {
			return "";
		}
		if (selecteddatabaseId.equals(databaseId)) {
			return "selected";
		}
		return "";
	}

	public String add() {
		zfileService.addZfile(zfile);
		return "add_success";
	}

	public String updateInput() {
		zfile = zfileService.findZfileById(zfile.getId());

		List<Database> databases = databaseService.getAllDatabaseList();
		ActionContext.getContext().put("databases", databases);

		Integer selecteddatabaseId = zfile.getBase().getId();
		ActionContext.getContext()
				.put("selecteddatabaseId", selecteddatabaseId);
		return "update_input";
	}

	public String update() {
		zfileService.updateZfile(zfile);
		return "update_success";
	}

	public void del() {		
		zfileService.delZfile(zfile.getId());
	}

}
