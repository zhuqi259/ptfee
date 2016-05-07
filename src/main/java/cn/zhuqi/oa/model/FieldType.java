package cn.zhuqi.oa.model;

/**
 * 表单的数据类型
 * 
 * @author Zhuqi
 */
public class FieldType {

	private int id;

	/**
	 * 数据类型的名称，如：字符串、整型、文件等等
	 */
	private String name;

	/**
	 * 对应的Java类型，比如String、Integer、File等等
	 */
	private String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
