/* 
 * To change this template, choose Tools | Templates * and open the template in the editor. */
package cn.zhuqi.mytest;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * *
 * 
 * @author sunnywfg
 */
public class MakeOrderNO { // QX100731000008
	public static String getOrderNo(String getUserId, String Tbname,
			String Filedname) throws SQLException {
		long No = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowdate = sdf.format(new Date());
		No = Long.parseLong(nowdate); // * 1000;//这里如果一天订单多的话可以用一万或更大
		int usedqty = getNo(Tbname, Filedname, getUserId + No);
		String getNo = "";
		if (String.valueOf(usedqty + 1).length() == 1) {
			getNo = "00" + String.valueOf(usedqty + 1);
		}
		if (String.valueOf(usedqty + 1).length() == 2) {
			getNo = "0" + String.valueOf(usedqty + 1);
		}
		if (String.valueOf(usedqty + 1).length() == 3) {
			getNo = String.valueOf(usedqty + 1);
		}
		String getOrder = getUserId + No + getNo;
		return getOrder;
	}

	public static int getNo(String Tbname, String Filedname, String NewNo)
			throws SQLException {// 返回当天的订单数+1
		return 1;
	}
}