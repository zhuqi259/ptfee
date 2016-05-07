package cn.zhuqi.system;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileNameUtil {

	public static String getNewFileName(String fileName, int curIndex) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String newFileName = sf.format(Calendar.getInstance().getTime());
		int beginIndex = fileName.lastIndexOf(".");
		if (beginIndex > 0) {
			String fileExt = fileName.substring(beginIndex);
			newFileName = newFileName + "_" + curIndex + fileExt;
		}
		return newFileName;
	}
}
