package com.rongdu.cashloan.core.enums;
/**
 * 区域统计类型使用
 */
public enum AreaStatisticsEnum {

	FINANCING_AMOUNT("1", "当月融资金额"),
	BORROWING_TIMES("2", "当月借款次数"),
	REPAYMENT_AMOUNT("3", "还款金额"),
	NEW_USERS("4", "新增用户");

	private String value;

	private String desc;


	private AreaStatisticsEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	public static AreaStatisticsEnum getValue(String value) {
		for (AreaStatisticsEnum statusEnum : AreaStatisticsEnum.values()) {
			if (statusEnum.getValue().equals(value)) {
				return statusEnum;
			}
		}
		return null;
	}

}
