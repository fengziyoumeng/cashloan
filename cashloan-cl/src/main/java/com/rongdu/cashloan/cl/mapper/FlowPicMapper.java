package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.FlowPic;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 首页轮播图片Dao
 * 
 * @author zwk
 * @version 1.0.0
 * @date 2017-11-13 19:40:01
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@RDBatisDao
public interface FlowPicMapper extends BaseMapper<FlowPic, Long> {

    List<FlowPic> getPic(Integer type);

    List<FlowPic> getAllBanner();

    int deleteById(Long id);

}
