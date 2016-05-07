package cn.zhuqi.oa.web.actions;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.annotations.Oper;
import cn.zhuqi.oa.annotations.Res;
import cn.zhuqi.oa.model.Company;
import cn.zhuqi.oa.model.Party;
import cn.zhuqi.oa.service.PartyService;
import cn.zhuqi.oa.vo.PartyTreeVO;
import cn.zhuqi.oa.web.JSONUtils;

import com.opensymphony.xwork2.ModelDriven;

@Controller("partyAction")
@Scope("prototype")
@Res(name="组织机构操作",sn="party",orderNumber=10)
public class PartyAction extends BaseAction implements ModelDriven{
	
	@Resource
	protected PartyService partyService;

	protected Party model;
	
	@Override
	public Object getModel() {
		
		if(model == null){
			model = new Party();
		}
		
		return model;
	}

	//打开机构管理主界面
	@Oper
	public String execute(){
		Company current = partyService.currentCompany();
		if(current == null){
			throw new RuntimeException("在维护组织机构之前，请先设置本公司的基本信息！");
		}		
		return "index";
	}
	
	//返回当前组织架构树的JSON串
	public void tree(){
		Company current = partyService.currentCompany();
		PartyTreeVO ptv = new PartyTreeVO(current);
		JSONUtils.toJSON(ptv);
	}
	
	//打开添加页面
	@Oper
	public String addInput(){
		
		//页面上必须传过来parent.id参数，以便表明是在哪一个party下面创建子party
		int parentId = model.getParent().getId();
		if(parentId == 0){
			throw new RuntimeException("未知父节点，无法创建子节点！");
		}
		
		return "add_input";
	}
	
	//执行添加操作
	@Oper
	public String add(){
		
		//将party信息存储到数据库中
		partyService.addParty(model);
		
		return "add_success";
	}
	
	//打开更新页面
	@Oper
	public String updateInput(){
		
		model = partyService.findPartyById(model.getId());

		return "update_input";
	}
	
	//执行更新操作
	@Oper
	public String update(){
		
		partyService.updateParty(model);
		
		return "update_success";
	}
	
	//执行删除操作
	@Oper
	public String del(){
		partyService.delParty(model.getId());
		return "del_success";
	}
}
