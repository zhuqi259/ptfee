package cn.zhuqi.oa.web.actions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.annotations.Res;
import cn.zhuqi.oa.model.Department;

@Controller("departmentAction")
@Scope("prototype")
@Res(name="部门操作",sn="department",orderNumber=30,parentSn="party")
public class DepartmentAction extends PartyAction {
	public Object getModel() {
		
		if(model == null){
			model = new Department();
		}
		
		return model;
	}
}
