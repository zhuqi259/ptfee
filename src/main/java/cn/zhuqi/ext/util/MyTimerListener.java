package cn.zhuqi.ext.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.zhuqi.system.PropertyConfigurer;
import cn.zhuqi.system.TimeUtil;

public class MyTimerListener implements ServletContextListener {
	private BugXmlTimer mytimer = new BugXmlTimer();

	public void contextInitialized(ServletContextEvent event) {
		PropertyConfigurer propertyConfigurer = new PropertyConfigurer("time");
		long period = TimeUtil
				.getPeriod(propertyConfigurer.getString("period"));
		mytimer.timerStart(TimeUtil.getDelay2Now(
				propertyConfigurer.getString("delay"), period), period);
	}

	public void contextDestroyed(ServletContextEvent event) {
		mytimer.timerStop();
	}
}