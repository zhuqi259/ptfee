package cn.zhuqi.system;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil {

	public static List<String> getAllFields(Object aObject) {
		Class<?> c = aObject.getClass();
		Field[] fields = c.getDeclaredFields();
		List<String> names = new ArrayList<String>();
		Field.setAccessible(fields, true);
		try {
			for (Field field : fields) {
				System.out.println(field.getType() + " " + field.getName()
						+ "=" + field.get(aObject));
				names.add(field.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return names;
	}

	/**
	 * get the field value in aObject by aFieldName
	 * 
	 * @param aObject
	 * @param aFieldName
	 * @return
	 */
	public static Object getFieldValue(Object aObject, String aFieldName) {
		// get the field in this object
		Field field = getClassField(aObject.getClass(), aFieldName);
		if (field != null) {
			field.setAccessible(true);
			try {
				return field.get(aObject);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 这个方法，是最重要的，关键的实现在这里面
	 * 
	 * @param aClazz
	 * @param aFieldName
	 * @return
	 */
	private static Field getClassField(Class<?> aClazz, String aFieldName) {
		Field[] declaredFields = aClazz.getDeclaredFields();
		for (Field field : declaredFields) {
			// 注意：这里判断的方式，是用字符串的比较。很傻瓜，但能跑。要直接返回Field。我试验中，尝试返回Class，然后用getDeclaredField(String
			// fieldName)，但是，失败了
			if (field.getName().equals(aFieldName)) {
				return field;// define in this class
			}
		}
		Class<?> superclass = aClazz.getSuperclass();
		if (superclass != null) {// 简单的递归一下
			return getClassField(superclass, aFieldName);
		}
		return null;
	}
}
