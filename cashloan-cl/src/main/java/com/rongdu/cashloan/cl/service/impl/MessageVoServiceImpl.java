package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.Message;
import com.rongdu.cashloan.cl.mapper.MessageMapper;
import com.rongdu.cashloan.cl.mapper.MessageVoMapper;
import com.rongdu.cashloan.cl.service.IMessageVoService;
import com.rongdu.cashloan.cl.serviceNoSharding.MessageService;
import com.rongdu.cashloan.cl.vo.MessageVo;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.core.common.util.DateUtil;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.system.domain.SysDictDetail;
import com.rongdu.cashloan.system.serviceNoSharding.SysDictDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 用户消息ServiceImpl
 * 
 * @author Yang
 * @version 1.0.0
 * @date 2018-01-26 13:31:57
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("messageVoService")
public class MessageVoServiceImpl implements IMessageVoService {
	
    private static final Logger logger = LoggerFactory.getLogger(MessageVoServiceImpl.class);

	@Resource
	private SysDictDetailService sysDictDetailService;

	@Resource
	private ShardedJedisClient redisClient;

    @Resource
    private MessageVoMapper messageVoMapper;

	@Resource
	protected MybatisService mybatisService;


	@Override
	public Integer hasNewMessage(Long userId) {
		try{
			List<Date> dates = messageVoMapper.hasNewMessage(userId);
			List<Date> dates2 = messageVoMapper.getMassMessage();
			List<Date> create = new ArrayList<>();
			create.addAll(dates2);
			create.addAll(dates);
			long createTime =0L;
			for (Date date : create) {
				long time = date.getTime();
				if(createTime<time){
					createTime = time;
				}
			}

			Map map = mybatisService.queryRec("usr.queryMsgReadTime", userId);
			Date readTime = null;
			if(map != null){
				readTime = (Date)map.get("msg_read_time");
				if(readTime==null){
					readTime = DateUtil.parse("2018-01-01","yyyy-MM-dd");
				}
			}else{
				readTime = DateUtil.parse("2018-01-01","yyyy-MM-dd");
			}

			if(createTime>readTime.getTime()){
				//有新消息
				return 1;
			}else{
				return 0;
			}
		}catch (Exception e){
			logger.info("查询是否有未读消息失败",e);
			throw e;
		}
	}

	@Override
	public void readMessage(Long userId) {
		try{
			Map<String, Object> param = new HashMap<>();
			param.put("userId",userId);
			param.put("msgReadTime",new Date());
			mybatisService.updateSQL("usr.updateMsgReadTime", param);
		}catch (Exception e){
			logger.info("设置读取消息失败",e);
		}
	}
}