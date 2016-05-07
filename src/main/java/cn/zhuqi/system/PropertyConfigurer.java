package cn.zhuqi.system;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertyConfigurer {

	private String propertyFileName;
	private ResourceBundle resourceBundle;

	public PropertyConfigurer() {
		propertyFileName = "config/jdbc";
		resourceBundle = ResourceBundle.getBundle(propertyFileName);
	}

	public PropertyConfigurer(String propertyFileName) {
		super();
		this.propertyFileName = propertyFileName;
		this.resourceBundle = ResourceBundle.getBundle(propertyFileName);
	}

	public String getString(String key) {
		if (key == null || key.equals("") || key.equals("null")) {
			return "";
		}
		String result = "";
		try {
			result = resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		PropertyConfigurer propertyConfigurer = new PropertyConfigurer();
		String url = propertyConfigurer.getString("jdbc.url");
		String username = propertyConfigurer.getString("jdbc.username");
		String password = propertyConfigurer.getString("jdbc.password");
		System.out.println(url + " " + username + " " + password);
	}

}
