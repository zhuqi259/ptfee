package cn.zhuqi.oa.web.actions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhuqi.oa.annotations.Oper;
import cn.zhuqi.oa.annotations.Res;
import cn.zhuqi.oa.model.Company;

@Controller("companyAction")
@Scope("prototype")
@Res(name="公司操作",sn="company",orderNumber=20,parentSn="party")
public class CompanyAction extends PartyAction {

	@Override
	public Object getModel() {
		if(model == null){
			model = new Company();
		}
		
		return model;
	}
	
	/**
	 * 打开公司信息设置界面
	 * @return
	 */
	@Oper(name="公司信息维护",index=4,sn="saveCompany")
	public String saveInput() {
		
		//查询当前公司的信息
		model = partyService.currentCompany();
		
		return "company_input";
	}

	/**
	 * 保存公司信息，添加或更新
	 * @return
	 */
	@Oper(name="公司信息维护",index=4,sn="saveCompany")
	public String save(){
		partyService.saveCompany((Company)model);
		return "add_success";
	}
}
