package cn.zhuqi.system;

import java.math.BigDecimal;

public class MulUtil {

	public static String mul(String a, String b) {
		BigDecimal aa = new BigDecimal(0);
		BigDecimal bb = new BigDecimal(0);
		try {
			aa = new BigDecimal(a);
			bb = new BigDecimal(b);
		} catch (Exception e) {
			return "0";
		}		
		return aa.multiply(bb).toString();
	}

	public static void main(String[] args) {
		System.out.println(mul(null, ""));
	}
}
