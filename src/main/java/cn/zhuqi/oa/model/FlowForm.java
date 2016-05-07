package cn.zhuqi.oa.model;

import java.util.Set;

/**
 * 流程表单
 * 
 * @author Zhuqi
 */
public class FlowForm {

	private int id;

	/**
	 * 对应的流程
	 */
	private Workflow workflow;

	/**
	 * 表单呈现模版
	 */
	private String template;

	/**
	 * 表单所包含的表单域
	 */
	private Set fields;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Set getFields() {
		return fields;
	}

	public void setFields(Set fields) {
		this.fields = fields;
	}
}
