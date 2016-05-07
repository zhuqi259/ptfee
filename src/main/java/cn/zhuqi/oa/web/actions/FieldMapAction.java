package cn.zhuqi.oa.web.actions;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.model.FieldMap;
import cn.zhuqi.oa.model.ZSequence;
import cn.zhuqi.oa.service.FieldMapService;
import cn.zhuqi.oa.service.ZSequenceService;

import com.opensymphony.xwork2.ModelDriven;

@Controller("fieldMapAction")
@Scope("prototype")
public class FieldMapAction extends BaseAction implements ModelDriven {

	private FieldMap fieldMap;

	@Resource
	private FieldMapService fieldMapService;

	@Resource
	private ZSequenceService zSequenceService;

	@Override
	public Object getModel() {
		if (fieldMap == null) {
			fieldMap = new FieldMap();
		}
		return fieldMap;
	}

	public String updateInput() {
		fieldMap = fieldMapService.findFieldMapById(fieldMap.getId());
		return "update_input";
	}

	public String update() {
		fieldMapService.updateFieldMap(fieldMap);
		return "update_success";
	}

	public String addInput() {
		return "add_input";
	}

	public String add() {
		fieldMapService.addFieldMap(fieldMap);
		fieldMap.setName(String.valueOf(-fieldMap.getId()));
		fieldMapService.updateFieldMap(fieldMap);
		ZSequence zSequence = new ZSequence();
		zSequence.setFieldMap(fieldMap);
		zSequence.setCopyable('N');
		zSequenceService.addZSequence(zSequence);
		return "add_success";
	}

	public String copy() {
		fieldMap = fieldMapService.findFieldMapById(fieldMap.getId());
		ZSequence zSequence = new ZSequence();
		zSequence.setFieldMap(fieldMap);
		zSequence.setDelable('Y');
		zSequence.setEditable('N');
		zSequenceService.addZSequence(zSequence);
		return "update_success";
	}

}
