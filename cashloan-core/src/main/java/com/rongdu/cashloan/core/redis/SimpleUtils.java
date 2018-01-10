package com.rongdu.cashloan.core.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class SimpleUtils {
	private static final String EMPTY = "";

	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	public static String join(Object[] array, String separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}

		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * 如果目标IMap里面没有该键值或为null或为"",则抛出异常
	 * 
	 * @param srcData
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getStr(Map<String, Object> srcData, String key) {

		return getStr(srcData, key, "");
	}

	/**
	 * 如果目标IMap里面没有该键值或为null或为"",则返回默认值
	 * 
	 * @param srcData
	 * @param key
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	public static String getStr(Map<String, Object> srcData, String key, String defaultValue) {

		if (!srcData.containsKey(key) || srcData.get(key) == null || "".equals(srcData.get(key).toString().trim())) {
			return defaultValue;
		} else {
			return srcData.get(key).toString();
		}
	}

	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object unserialize(byte[] bytes) {
		if(bytes != null) {
			ByteArrayInputStream bais = null;
			try {
				// 反序列化
				bais = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				return ois.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
