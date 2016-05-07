package cn.zhuqi.oa.model;

import java.util.HashSet;
import java.util.Set;

public class ZActivity {

	private int id;
	private String name;
	private Workflow workflow;
	private Set<Zfile> files = new HashSet<Zfile>();

	private Integer x;
	private Integer y;
	private Integer height;
	private Integer width;

	private String owner;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

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

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Set<Zfile> getFiles() {
		return files;
	}

	public void setFiles(Set<Zfile> files) {
		this.files = files;
	}

}
