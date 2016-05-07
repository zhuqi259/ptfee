package cn.zhuqi.oa.web;

import org.apache.struts2.ServletActionContext;

import com.sdicons.json.mapper.JSONMapper;

public class JSONUtils {
	/**
	 * 将某个对象转换为JSON格式的字符串，并将其直接写入HttpResponse对象
	 * 
	 * @param obj
	 */
	public static void toJSON(Object obj) {
		try {
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
			ServletActionContext.getResponse().getWriter()
					.println(JSONMapper.toJSON(obj).render(false));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("在将对象" + obj + "转换为JSON格式字符串的时候发生异常！");
		}
	}
}
