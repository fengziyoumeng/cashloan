package com.rongdu.cashloan.cl.serviceNoSharding.impl;

import javax.annotation.Resource;

import com.rongdu.cashloan.cl.vo.MessageVo;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.exception.ServiceException;
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

import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.cl.mapper.MessageMapper;
import com.rongdu.cashloan.cl.domain.Message;
import com.rongdu.cashloan.cl.serviceNoSharding.MessageService;

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
 
@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl<Message, Long> implements MessageService {
	
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Resource
	private SysDictDetailService sysDictDetailService;

	@Resource
	private ShardedJedisClient redisClient;

    @Resource
    private MessageMapper messageMapper;

	@Override
	public BaseMapper<Message, Long> getMapper() {
		return messageMapper;
	}

	@Override
	public List<MessageVo> selectAllMessage(Long userId) throws Exception{
		List<Message> dataList = null;
		List<MessageVo> result = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();

		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dataList = messageMapper.getAllMessageByUserId(userId);
			List<Date> dates = messageMapper.groupByTime(userId);

			for (Date date : dates) {
				List<Message> day = new ArrayList<>();
				for (Message message : dataList) {
						if(message !=null && DateUtil.sameDate(message.getCreateTime(),date)){
							String tag = redisClient.get(AppConstant.REDIS_KEY_DIRECT+"SMS_TAG"+message.getType());
							if(StringUtil.isNotBlank(tag)){
								message.setTag(tag);
								day.add(message);
							}else {
								SysDictDetail sms_tag = sysDictDetailService.findDetail(message.getType().toString(), "SMS_TAG");
								message.setTag(sms_tag.getItemValue());
								day.add(message);
								redisClient.set(AppConstant.REDIS_KEY_DIRECT+"SMS_TAG"+message.getType(),sms_tag.getItemValue());
							}
						}
					}
					if(day.size()>0){
						MessageVo msg = new MessageVo();
						msg.setDate(sdf.format(date));
						msg.setList(day);
						result.add(msg);
					}
				}

		}catch (Exception e){
			logger.info("消息查询失败",e);
			throw e;
		}
		return result;
	}

	@Override
	public void sendMessage(String parentCode,String childenCodeTitle,String childenCodeMsg,Long receiving,Integer msgType,Long ...procId) {
		try{
			String title = sysDictDetailService.getValueFromRedis(parentCode,childenCodeTitle);
			String msg = sysDictDetailService.getValueFromRedis(parentCode,childenCodeMsg);
			Message message = new Message();
			message.setReceiving(receiving);
			message.setType(msgType);
			message.setTitle(title);
			message.setMessage(msg);
			message.setCreateTime(new Date());
			if(procId !=null && procId.length>0){
				message.setpId(procId[0]);
			}
			messageMapper.save(message);
		}catch (Exception e){
			logger.info("消息保存失败",e);
		}

	}

	@Override
	public List<Message> messageList() throws Exception {
		List<Message> messages = null;
		try{
			 messages = messageMapper.selectMessageList();
		}catch (Exception e){

		}
		return messages;
	}

	@Override
	public void saveOrUpdateMessage(String data) {
		try{
			Message message = JsonUtil.parse(data, Message.class);
			message.setReceiving(0L);
			message.setCreateTime(new Date());
			if(message.getType().equals(4)){
				message.setpId(2L);
				message.setSkipUrl(null);
			}else {
				message.setpId(null);
			}
			if(message.getId()==null){

				messageMapper.save(message);
			}else {
				messageMapper.update(message);
			}
		}catch (Exception e){
			logger.info("消息保存或更新失败",e);
			throw e;
		}
	}

	@Override
	public void deleteMessage(Long id) {
		try{
			messageMapper.deleteById(id);
		}catch (Exception e){
			logger.info("删除失败",e);
			throw e;
		}
	}

}