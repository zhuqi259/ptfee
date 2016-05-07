package cn.zhuqi.system;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.zhuqi.oa.model.Rule;
import cn.zhuqi.oa.service.IdTableService;
import cn.zhuqi.oa.service.RuleService;

public final class IdUtil {

	private IdUtil() {
	}

	private static String format(int num) {
		String str = "";
		for (int i = 0; i < num; i++) {
			str += "0";
		}
		return str;
	}

	private static String getZId(String generateId, int num) {
		Integer intHao = Integer.parseInt(generateId);
		String STR_FORMAT = format(num);
		DecimalFormat df = new DecimalFormat(STR_FORMAT);
		return df.format(intHao);
	}

	private static String decode(String pattern, IdTableService idTableService) {
		String result = "";
		if (pattern.equals("yy") || pattern.equals("yyyy")
				|| pattern.equals("MM") || pattern.equals("dd")
				|| pattern.equals("HH") || pattern.equals("hh")
				|| pattern.equals("mm") || pattern.equals("ss")) {
			result = new SimpleDateFormat(pattern).format(new Date());
		} else {
			for (int i = 0; i < pattern.length();) {
				try {
					char ch = pattern.charAt(i);
					if (ch == '$') {
						i++;
						ch = pattern.charAt(i);
						if (ch == '$') {
							result += ch;
							i++;
						} else if (Character.isDigit(ch)) {
							// int num = Integer.parseInt(String.valueOf(ch));
							int num = ch - '0';
							i++;
							while (i < pattern.length()
									&& Character
											.isDigit(ch = pattern.charAt(i))) {

								num = num * 10 + ch - '0';
								i++;
							}
							// 根据num生成几位数字
							result += getZId(idTableService.generateId(), num);
							if (i < pattern.length())
								i--;
						}

					} else {
						result += ch;
						i++;
					}
				} catch (Exception e) {
					throw new RuntimeException("编号生成规则错误:$");
				}
			}
		}
		return result;
	}

	private static String decode(String pattern, String number) {
		String result = "";
		if (pattern.equals("yy") || pattern.equals("yyyy")
				|| pattern.equals("MM") || pattern.equals("dd")
				|| pattern.equals("HH") || pattern.equals("hh")
				|| pattern.equals("mm") || pattern.equals("ss")) {
			result = new SimpleDateFormat(pattern).format(new Date());
		} else {
			for (int i = 0; i < pattern.length();) {
				try {
					char ch = pattern.charAt(i);
					if (ch == '$') {
						i++;
						ch = pattern.charAt(i);
						if (ch == '$') {
							result += ch;
							i++;
						} else if (Character.isDigit(ch)) {
							// int num = Integer.parseInt(String.valueOf(ch));
							int num = ch - '0';
							i++;
							while (i < pattern.length()
									&& Character
											.isDigit(ch = pattern.charAt(i))) {

								num = num * 10 + ch - '0';
								i++;
							}
							// 根据num生成几位数字
							result += getZId(number, num);
							if (i < pattern.length())
								i--;
						}

					} else {
						result += ch;
						i++;
					}
				} catch (Exception e) {
					throw new RuntimeException("编号生成规则错误:$");
				}
			}
		}
		return result;
	}

	public static synchronized String generateId(IdTableService idTableService,
			RuleService ruleService) {
		String pattern = ruleService.getSingle().getIrule();
		String result = "";
		String s[] = pattern.split(",");
		for (String str : s) {
			result += decode(str, idTableService);
		}
		return result;
	}

	public static String getSampleId(Rule rule, String number) {
		String pattern = rule.getIrule();
		String result = "";
		String s[] = pattern.split(",");
		for (String str : s) {
			result += decode(str, number);
		}
		return result;
	}

	public static String getSampleId(Rule rule) {
		return getSampleId(rule, "1");
	}
}
