package cn.zhuqi.oa.model;

import java.util.HashSet;
import java.util.Set;

public class Database {

	private int id;
	private String name;

	private Set<Zfile> files = new HashSet<Zfile>();

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

	public Set<Zfile> getFiles() {
		return files;
	}

	public void setFiles(Set<Zfile> files) {
		this.files = files;
	}

}
