package cn.zhuqi.ext.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import cn.zhuqi.system.PropertyConfigurer;

public class BugXmlTimerTask extends TimerTask {

	private String driver;
	private String url;
	private String username;
	private String password;

	// 即每隔一段时间要做的事情
	@Override
	public void run() {
		System.out.println("[task---begin]" + new Date());
		PropertyConfigurer propertyConfigurer = new PropertyConfigurer();
		// 历史系统
		driver = propertyConfigurer.getString("jdbc.driverClassName2");
		url = propertyConfigurer.getString("jdbc.url2");
		username = propertyConfigurer.getString("jdbc.username2");
		password = propertyConfigurer.getString("jdbc.password2");
		DBUtil dbu = new DBUtil(driver, url, username, password);
		String sql = "select * from t_eproject";
		List<Map<String, Object>> list = dbu.queryToList(sql);

		// 现在的系统
		driver = propertyConfigurer.getString("jdbc.driverClassName");
		url = propertyConfigurer.getString("jdbc.url");
		username = propertyConfigurer.getString("jdbc.username");
		password = propertyConfigurer.getString("jdbc.password");
		DBUtil dbu2 = new DBUtil(driver, url, username, password);
		String sql2 = "update t_project set createTime = ? where fid = ?";
		// for (Map<String, Object> map : list) {
		// Date kgtime = (Date) map.get("kgtime");
		// String fid = (String) map.get("fid");
		// System.out.println(dbu2.update(sql2, kgtime, fid));
		// }
		System.out.println(dbu2.updateBatch(sql2, list, "kgtime", "fid"));
		System.out.println("[task---end]" + new Date());
	}

}
