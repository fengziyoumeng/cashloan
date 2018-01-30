package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.cl.domain.Message;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.Date;
import java.util.List;

/**
 * 用户消息Dao
 * 
 * @author Yang
 * @version 1.0.0
 * @date 2018-01-26 13:31:57
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@RDBatisDao
public interface MessageMapper extends BaseMapper<Message, Long> {
    List<Message> getAllMessageByUserId(Long userId);

    List<Message> selectMessageList();

    List<Date> groupByTime(Long userId);

    void deleteById(Long id);
}
