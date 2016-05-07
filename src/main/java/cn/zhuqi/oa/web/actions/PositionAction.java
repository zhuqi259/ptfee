package cn.zhuqi.oa.web.actions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.annotations.Res;
import cn.zhuqi.oa.model.Position;

@Controller("positionAction")
@Scope("prototype")
@Res(name = "岗位操作", sn = "position", orderNumber = 40, parentSn = "party")
public class PositionAction extends PartyAction {
	public Object getModel() {

		if (model == null) {
			model = new Position();
		}

		return model;
	}
}
