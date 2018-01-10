package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.Credit;
import com.rongdu.cashloan.cl.domain.CreditModel;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;


import java.util.List;
import java.util.Map;

/**
 * 授信额度管理Dao
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2016-12-15 15:47:24
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@RDBatisDao
public interface CreditMapper extends BaseMapper<Credit,Long> {
	
	/**
	 * 更新额度
	 * @param map
	 * @return
	 */
	int updateAmount(Map<String, Object> map);

	/**
	 * 列表查询返回CreditModel
	 * @param searchMap
	 * @return
	 */
	List<CreditModel> page(Map<String, Object> searchMap);

	/**
	 * 根据consumerNo查询
	 * @param consumerNo
	 * @return
	 */
	Credit findByConsumerNo(String consumerNo);
	
	/**
	 * 查询用户所有额度类型
	 * @param searchMap
	 * @return
	 */
	List<CreditModel> listAll(Map<String, Object> searchMap);
	
	
	/**
	 * 用户信用额度查询
	 * @param searchMap
	 * @return
	 */
	List<CreditModel> creditList(Map<String, Object> searchMap);
}
