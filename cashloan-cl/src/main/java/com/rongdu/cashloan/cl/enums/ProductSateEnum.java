package com.rongdu.cashloan.cl.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API接口，对应编号
 * 
 * @author Administrator
 * 
 */
public enum ProductSateEnum {

	ON_USED("启用", 10),

	OFF_SALE("不启用", 20);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	/** 构造函数 */
	private ProductSateEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ProductSateEnum getEnum(int value) {
		ProductSateEnum resultEnum = null;
		ProductSateEnum[] enumAry = ProductSateEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		ProductSateEnum[] ary = ProductSateEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		ProductSateEnum[] ary = ProductSateEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

}
