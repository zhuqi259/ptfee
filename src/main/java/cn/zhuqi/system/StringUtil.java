package cn.zhuqi.system;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String myReplace(String str) {
		if (str != null && !"".equals(str)) {
			Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
			Matcher m = CRLF.matcher(str);
			if (m.find()) {
				str = m.replaceAll("<br>");
			}
			return str.replaceAll(" ", "&nbsp;");
		} else {
			return str;
		}
	}

	public static String getType(String path) {
		if (path != null) {
			int extPos = path.lastIndexOf(".");
			if (extPos > 0) {
				return path.substring(extPos);
			}
		}
		return null;
	}

	public static boolean IsInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
