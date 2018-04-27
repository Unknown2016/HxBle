package com.hexin.apicloud.ble.util;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
/**
 * 对象属性拷贝
 * 解释：相同属性的两个对象拷贝赋值
 * 工具类
 * @author jundao
 */
public class BeanUtil {
	public static void copyProperty(Object orig, Object dest) throws IllegalAccessException, IllegalArgumentException {
		// 得到两个Class的所有成员变量
		Field[] destFields = dest.getClass().getDeclaredFields();
		Field[] origFields = orig.getClass().getDeclaredFields();
		// 设置访问权限
		AccessibleObject.setAccessible(destFields, true);
		AccessibleObject.setAccessible(origFields, true);
		Object value = null;
		String name = "";
		String returnType = "";
		for (int i = 0; i < origFields.length; i++) {
			name = origFields[i].getName();
			if("serialVersionUID".equals(name)){
				continue;
			}
			returnType = origFields[i].getType().getName();
			for (int j = 0; j < destFields.length; j++) {
				if (name.equals(destFields[j].getName()) && returnType.equals(destFields[j].getType().getName())) {
					value = origFields[i].get(orig);
					if (value != null) {
						destFields[j].set(dest, value);
					}
					break;
				}
			}
		}
	}
}
