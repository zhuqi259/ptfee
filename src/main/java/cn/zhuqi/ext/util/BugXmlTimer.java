package cn.zhuqi.ext.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class BugXmlTimer {
	public Timer timer;

	public void timerStart() {
		timer = new Timer();
		Date datetime = new Date();
		Date midnightDate = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			midnightDate = sdf2.parse(sdf1.format(datetime) + " 23:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long in = midnightDate.getTime() - datetime.getTime();
		System.out.println("before task------------");
		// 定时执行，然后每隔一天执行一次
		timer.schedule(new BugXmlTimerTask(), in, 1000 * 60 * 60 * 24);
	}

	public void timerStart(long delay, long period) {
		timer = new Timer();
		timer.schedule(new BugXmlTimerTask(), delay, period);
	}

	public void timerStop() {
		if (timer != null)
			timer.cancel();
	}

	public static void main(String[] args) {
		BugXmlTimer myTimer = new BugXmlTimer();
		myTimer.timerStart();
	}
}
