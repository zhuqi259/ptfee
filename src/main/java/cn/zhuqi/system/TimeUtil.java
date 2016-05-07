package cn.zhuqi.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {

	public static String Date2Str(Date date) {
		String pattern = "yyyy年M月d日 E HH时mm分ss秒";
		return Date2Str(date, pattern);
	}

	public static String Date2Str(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Integer getToday() {
		return new GregorianCalendar().get(Calendar.YEAR);
	}

	public static long getDelay2Now(String delay, long period) {
		Date datetime = new Date();
		Date midnightDate = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			midnightDate = sdf2.parse(sdf1.format(datetime) + " " + delay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long in = midnightDate.getTime() - datetime.getTime();
		while (in < 0) {
			in += period;
		}
		return in;
	}

	public static long getPeriod(String period) {
		long ret = 0;
		try {
			ret = Long.parseLong(period);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static final long ONEDAY = 1000 * 60 * 60 * 24;
	public static final long ONESECOND = 1000;
	public static final long ONEMINUTE = 1000 * 60;
	public static final long ONEHOUR = 1000 * 60 * 60;

}
