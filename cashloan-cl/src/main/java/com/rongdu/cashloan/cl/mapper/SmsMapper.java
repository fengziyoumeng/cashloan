package com.rongdu.cashloan.cl.mapper;

import java.util.Map;

import com.rongdu.cashloan.cl.domain.Sms;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

/**
 * 短信记录Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-09 14:48:24
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@RDBatisDao
public interface SmsMapper extends BaseMapper<Sms,Long> {

	/**
	 * 查询最新一条短信记录
	 * @param data
	 * @return
	 */
	Sms findTimeMsg(Map<String, Object> data);

    /**
     * 查询某号码某种类型当天已发送次数
     * @param data
     * @return
     */
    int countDayTime(Map<String, Object> data);

}
