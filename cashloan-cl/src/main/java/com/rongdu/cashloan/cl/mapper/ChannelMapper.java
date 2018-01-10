package com.rongdu.cashloan.cl.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.Channel;
import com.rongdu.cashloan.cl.model.ChannelCountModel;
import com.rongdu.cashloan.cl.model.ChannelModel;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

/**
 * 渠道信息Dao
 * 
 * @author gc
 * @version 1.0.0
 * @date 2017-03-03 10:52:07
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@RDBatisDao
public interface ChannelMapper extends BaseMapper<Channel,Long> {
	
	/**
	 * 根据条件查询主键
	 */
	Long findIdSelective(Map<String, Object> paramMap);
	Long findIdByCode(Map<String, Object> paramMap);

	/**
	 * 根据条件查询对象
	 */
	Channel findSelective(Map<String, Object> paramMap);
	Channel selectById(Long id);

	/**
	 * 列表查询
	 * @param paramMap
	 * @return
	 */
	List<ChannelModel> page(Map<String, Object> paramMap);
	/**
	 * 渠道用户信息统计
	 * @return
	 */
	Page<ChannelCountModel> channelUserList(Map<String, Object> searchMap);

	
	/**
	 * 查出所有渠道信息
	 */
	List<Channel> listChannel();
	
	/**
	 * 查询没有版本信息的渠道id和名称
	 */
	List<Channel> listChannelWithoutApp();

	String findName(Long channelId);

	Long findIdByName(String name);
}
