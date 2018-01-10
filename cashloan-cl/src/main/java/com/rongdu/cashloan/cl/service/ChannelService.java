package com.rongdu.cashloan.cl.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.Channel;
import com.rongdu.cashloan.cl.model.ChannelCountModel;
import com.rongdu.cashloan.cl.model.ChannelModel;
import com.rongdu.cashloan.cl.vo.ChannelForH5;
import com.rongdu.cashloan.core.common.service.BaseService;

/**
 * 渠道信息Service
 *
 */
public interface ChannelService extends BaseService<Channel, Long>{

	/**
	 * 保存渠道信息
	 * 
	 * @param channel
	 * @return
	 */
	boolean save(Channel channel);
	
	/**
	 * 更新渠道信息
	 * 
	 * @param channel
	 * @return
	 */
	boolean update(Map<String, Object> paramMap);

	/**
	 * 根据code查询主键
	 * @param code
	 * @return
	 */
	Long findIdByCode(String code);
	
	/**
	 * 根据code查询对象
	 * @param code
	 * @return
	 */
	Channel findByCode(String code);

	/**
	 * 列表查询渠道信息
	 * 
	 * @param current
	 * @param pageSize
	 * @param searchMap
	 * @return
	 */
	Page<ChannelModel> page(int current, int pageSize,
			Map<String, Object> searchMap);
	/**
	 * 渠道用户统计
	 * 
	 * @param current
	 * @param pageSize
	 * @param searchMap
	 * @return
	 */
	Page<ChannelCountModel> channelUserList(int current, int pageSize,
			Map<String, Object> searchMap);

	/**
	 * 查出所有渠道信息
	 */
	List<Channel> listChannel();

	/**
	 * 查出所有渠道信息注册统计信息
	 */
	List<ChannelForH5> registerCountList(Date beginTime,Date endTime);

	/**
	 * 2017/12/28日之后的点击数按80%计算
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<ChannelForH5> registerCountFor80(Date beginTime,Date endTime);

	/**
	 * 查询没有版本信息的渠道id和名称
	 */
	List<Channel> listChannelWithoutApp();
}
