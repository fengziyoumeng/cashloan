package com.rongdu.cashloan.cl.service;

import com.rongdu.cashloan.cl.domain.FlowPic;
import com.rongdu.cashloan.core.common.service.BaseService;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;

import java.util.List;


/**
 * 首页轮播图片Service
 * 
 * @author zwk
 * @version 1.0.0
 * @date 2017-11-13 19:40:01
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public interface FlowPicService extends BaseService<FlowPic, Long> {

    List<FlowPic> getPic(Integer type);

    List<FlowPic> getAllBanner();

    int saveOrUpdate(FlowPic flowPic);

    int deleteById(Long id);

}
