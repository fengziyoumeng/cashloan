package com.rongdu.cashloan.cl.service;

import java.util.List;
import java.util.Map;

import com.rongdu.cashloan.cl.domain.ChannelApp;
import com.rongdu.cashloan.cl.model.ChannelAppModel;
import com.rongdu.cashloan.core.common.service.BaseService;

/**
 * app渠道版本表Service
 * 
 * @author dufy
 * @version 1.0.0
 * @date 2017-05-10 10:29:55
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public interface ChannelAppService extends BaseService<ChannelApp, Long>{
	/**
	 * 查询app更新版本信息
	 * @return
	 */
	public List<ChannelAppModel> listChannelAppModel();
	
	/**
	 * 查询app更新信息
	 */
	public List<ChannelApp> listChannelApp();
	
	/**
	 * 主键查询信息
	 */
	public ChannelApp findByPrimary(long id);
	
	/**
	 * 保存信息
	 */
	public int save(ChannelApp channelApp);
	
	/**
	 * 更新信息
	 */
	public int updateSelective(Map<String, Object> paramMap);
}
