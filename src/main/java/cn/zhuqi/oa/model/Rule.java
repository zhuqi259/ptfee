package cn.zhuqi.oa.model;

public class Rule {

	private int id;
	private String irule;
	private String example;
	private char inUse;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIrule() {
		return irule;
	}

	public void setIrule(String irule) {
		this.irule = irule;
	}

	public char getInUse() {
		return inUse;
	}

	public void setInUse(char inUse) {
		this.inUse = inUse;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

}
