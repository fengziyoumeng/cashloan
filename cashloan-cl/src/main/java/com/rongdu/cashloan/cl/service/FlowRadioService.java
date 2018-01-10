package com.rongdu.cashloan.cl.service;


import com.rongdu.cashloan.cl.domain.FlowRadio;
import com.rongdu.cashloan.core.common.service.BaseService;

import java.util.List;

/**
 * 广播数据Service
 * 
 * @author zwk
 * @version 1.0.0
 * @date 2017-11-13 20:42:52
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public interface FlowRadioService extends BaseService<FlowRadio, Long> {

    List<FlowRadio> getRadio();

}
