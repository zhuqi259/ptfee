package cn.zhuqi.oa.web.actions;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.IdTable;
import cn.zhuqi.oa.model.Project;
import cn.zhuqi.oa.service.IdTableService;
import cn.zhuqi.oa.service.ProjectService;

import com.opensymphony.xwork2.ModelDriven;

@Controller("idtableAction")
@Scope("prototype")
public class IdtableAction extends BaseAction implements ModelDriven {

	private IdTable idTable;

	@Resource
	private IdTableService idTableService;

	@Resource
	private ProjectService projectService;

	@Override
	public Object getModel() {
		if (idTable == null) {
			idTable = new IdTable();
		}
		return idTable;
	}

	public String execute() {
		idTable = idTableService.zgetSingle();
		return "index";
	}

	public List<Project> getAllProjectByToday() {
		return projectService.getAllProjectByToday();
	}

	public String update() {
		idTableService.updateIdTable(idTable);
		return "update_success";
	}
}
