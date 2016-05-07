package cn.zhuqi.system;

public class ActivityUtil {

	public static boolean IsActivity(String activityName) {
		return !(activityName.startsWith("start")
				|| activityName.startsWith("end")
				|| activityName.startsWith("fork")
				|| activityName.startsWith("join") || activityName
					.startsWith("state"));
	}
}
