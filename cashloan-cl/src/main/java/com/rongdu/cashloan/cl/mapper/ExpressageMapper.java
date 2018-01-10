package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.Expressage;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 快递信息dao
 * 
 * @author zwk
 * @version 1.0.0
 * @date 2017-11-13 19:40:01
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@RDBatisDao
public interface ExpressageMapper extends BaseMapper<Expressage, Long> {

    int deleteById(Long id);

    List<Expressage> listSelective(Map<String,Object> result);

    List<Map<String,Object>> listExpressages(Map<String,Object> map);

    int isExistByTel(String tel);
}
