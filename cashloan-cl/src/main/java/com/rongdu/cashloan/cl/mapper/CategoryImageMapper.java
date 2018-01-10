package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.CategoryImage;
import com.rongdu.cashloan.cl.domain.CompanyInformation;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 企业资质信息Dao
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
public interface CategoryImageMapper extends BaseMapper<CategoryImage,Long> {
    void deleteCategoryById(Long id);

    List<CategoryImage> getCategoryImageByTypeValue(Integer typeValue);


}
