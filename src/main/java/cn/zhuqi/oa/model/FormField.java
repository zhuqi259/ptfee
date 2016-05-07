package cn.zhuqi.oa.model;

import java.util.List;

/**
 * 表单域
 * 
 * @author Zhuqi
 */
public class FormField {

	private int id;

	/**
	 * 表单域标签
	 */
	private String fieldLabel;

	/**
	 * 表单域名称
	 */
	private String fieldName;

	/**
	 * 表单域的输入形式
	 */
	private FieldInput fieldInput;

	/**
	 * 表单域的数据类型
	 */
	private FieldType fieldType;

	/**
	 * 额外参数，条目 比如：如果是下拉框，都有哪些值可供选择
	 */
	private List items;

	/**
	 * 对应的表单
	 */
	private FlowForm flowForm;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public FieldInput getFieldInput() {
		return fieldInput;
	}

	public void setFieldInput(FieldInput fieldInput) {
		this.fieldInput = fieldInput;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public FlowForm getFlowForm() {
		return flowForm;
	}

	public void setFlowForm(FlowForm flowForm) {
		this.flowForm = flowForm;
	}
}
