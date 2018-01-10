package com.rongdu.cashloan.core.common.util;

/**
 * 工具类-分表
 * @author xx
 * @version 1.0.0
 * @date 2017年6月5日 上午9:54:09
 * Copyright 杭州民华金融信息服务有限公司 金融创新事业部 cashloan  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public class ShardTableUtil {
	
	/**
	 * 根据主键Id生成分表名称
	 * @param shardId 拆分id段
	 * @return
	 */
	public static String generateTableNameById(String tableName, long id, long shardId){
		long num = id/shardId + 1;
		return tableName + "_" + num;
	}

}
