package com.rongdu.cashloan.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;
import com.rongdu.cashloan.core.domain.Borrow;
import com.rongdu.cashloan.core.model.BorrowModel;

/**
 * 借款申请管理Dao
 * 
 * @author ctt
 * @version 1.0.0
 * @date 2016-12-08 15:27:00
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@RDBatisDao
public interface BorrowMapper extends BaseMapper<Borrow,Long> {

    /**
     * 查询借款申请
     * @param map
     * @return
     */
    List<BorrowModel> selectByConditions(Map<String,Object> map);
    
    /**
     * 根据用户标识和标的标识查询借款申请
     */
    List<BorrowModel> findByConsumerAndBorrow(String consumerNo,String borrowNo);
    
    /**
     * 自动审批查找需要比对的值
     * @param sql
     * @return
     */
    String findValidValue(@Param("statement")String statement);
  
}
