package cn.zhuqi.mytest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.zhuqi.system.StringUtil;

public class StringTest {

	public static String n2br(String myString) {
		String newString = myString;
		Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
		Matcher m = CRLF.matcher(myString);
		if (m.find()) {
			newString = m.replaceAll("<br>");
		}
		return newString;
	}

	public static String s2nbsp(String myString) {
		String newString = myString;
		Pattern CRLF = Pattern.compile(" ");
		Matcher m = CRLF.matcher(myString);
		if (m.find()) {
			newString = m.replaceAll("&nbsp;");
		}
		return newString;
	}

	public static String t2nbsp(String myString) {
		String newString = myString;
		Pattern CRLF = Pattern.compile("\\t");
		Matcher m = CRLF.matcher(myString);
		if (m.find()) {
			newString = m
					.replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return newString;
	}

	public static String getStringNoBlank(String str) {
		if (str != null && !"".equals(str)) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			String strNoBlank = m.replaceAll("");
			return strNoBlank;
		} else {
			return str;
		}
	}

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String aaa = "\tsdfsfdsfsdf\r\n,dsfsdfsdf\r\n \rsdfhjsdh\n";
		// aaa.replace("s", "x");
		// System.out.println(n2br(aaa));
		// System.out.println(getStringNoBlank(aaa));
		// System.out.println(s2nbsp(aaa));
		System.out.println(t2nbsp(s2nbsp(n2br(aaa))));
		System.out.println(myReplace(aaa));
		System.out.println(StringUtil.myReplace(aaa));
	}

}