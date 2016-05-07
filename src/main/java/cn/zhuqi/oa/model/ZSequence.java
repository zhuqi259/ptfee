package cn.zhuqi.oa.model;

public class ZSequence {
	private int id;
	private FieldMap fieldMap;
	private char used = 'Y';
	private char delable = 'Y';
	private char editable = 'Y';
	private char copyable = 'Y';
	private Integer index = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FieldMap getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(FieldMap fieldMap) {
		this.fieldMap = fieldMap;
	}

	public char getUsed() {
		return used;
	}

	public void setUsed(char used) {
		this.used = used;
	}

	public char getDelable() {
		return delable;
	}

	public void setDelable(char delable) {
		this.delable = delable;
	}

	public char getEditable() {
		return editable;
	}

	public void setEditable(char editable) {
		this.editable = editable;
	}

	public char getCopyable() {
		return copyable;
	}

	public void setCopyable(char copyable) {
		this.copyable = copyable;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
