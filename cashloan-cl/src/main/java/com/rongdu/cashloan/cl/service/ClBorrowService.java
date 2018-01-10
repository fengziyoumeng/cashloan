package com.rongdu.cashloan.cl.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.model.ClBorrowModel;
import com.rongdu.cashloan.cl.model.IndexModel;
import com.rongdu.cashloan.cl.model.ManageBorrowModel;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.service.BaseService;
import com.rongdu.cashloan.core.domain.Borrow;

/**
 * 借款信息表Service
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 10:13:31
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public interface ClBorrowService extends BaseService<Borrow, Long>{


	/**
	 * 保存借款申请
	 * @param borrow
	 * @return
	 */
	Borrow saveBorrow(Borrow borrow);
	
	/**
	 * 修改借款状态
	 * @param id
	 * @param state
	 * @return
	 */
	int modifyState(long id, String state) ;


	/**
	 * 信用额度修改
	 * @param userId
	 * @param amount
	 * @param type
	 * @return
	 */
	int modifyCredit(Long userId, double amount, String type);
	
	/**
	 * 选择借款金额和期限
	 * app里选择借款金额和期限，返回实际到账金额、服务费、服务费明细
	 * @param amount
	 * @param timeLimit
	 * @return
	 */
	Map<String, Object> choice(double amount, String timeLimit);


	/**
	 * 查询
	 * @param searchMap
	 * @return
	 */
	List<Borrow> findBorrowByMap(Map<String, Object> searchMap);

	/**
	 * 查询最新10条借款信息
	 * @return
	 */
	List<IndexModel> listIndex();


	/**
	 * 分页查询
	 * @param searchMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<ClBorrowModel> page(Map<String, Object> searchMap, int current,
			int pageSize);
	
	/**
	 * 关联用户的借款分页查询后台列表显示
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<ManageBorrowModel> listModel(Map<String, Object> params,
			int currentPage, int pageSize);


	/**
	 * 导出查询用
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listByMap(Map<String, Object> params);

	/**
	 * 修改数据
	 * @param data
	 * @return
	 */
	int updateSelective(Map<String, Object> data);

	
	/**
	 * 借款部分还款信息
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<ManageBorrowModel> listBorrowModel(Map<String, Object> params,
			int currentPage, int pageSize);

	/**
	 * 查询一条失败记录
	 * @param searchMap
	 * @return
	 */
	Borrow findLast(Map<String, Object> searchMap);

	
	/**
	 * 支付时更新状态
	 * @return 
	 * @return
	 */
	void updatePayState(Map<String, Object> paramMap);

	/**
	 * 主键查询借款
	 * @param borrowId
	 * @return
	 */
	Borrow findByPrimary(Long borrowId);

	/**
	 * 查询借款
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List listBorrow(Map<String, Object> params);

	
	/**
	 * 复审通过查询
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<ManageBorrowModel> listReview(Map<String, Object> params,int currentPage, int pageSize);


	/**
	 * 查询未还款的借款订单
	 * @param id
	 * @return
	 */
	Borrow selectUnpayBorrowById(long id);

}
