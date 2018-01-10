package com.rongdu.cashloan.manage.mapper;

import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;
import com.rongdu.cashloan.manage.domain.QuartzInfo;
import com.rongdu.cashloan.manage.model.QuartzInfoModel;

import java.util.List;
import java.util.Map;

/**
 * 定时任务详情Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-15 13:30:53
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@RDBatisDao
public interface QuartzInfoMapper extends BaseMapper<QuartzInfo, Long> {

	/**
	 * 定时任务查询
	 * 
	 * @param map
	 * @return
	 */
	List<QuartzInfoModel> page(Map<String, Object> map);
}
