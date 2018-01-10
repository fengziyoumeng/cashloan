//package com.rongdu.cashloan.cl.serviceNoSharding;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.github.pagehelper.Page;
//import com.rongdu.cashloan.cl.domain.UrgeRepayOrder;
//import com.rongdu.cashloan.cl.model.UrgeRepayCountModel;
//import com.rongdu.cashloan.cl.model.UrgeRepayOrderModel;
//import com.rongdu.cashloan.core.common.service.BaseService;
//
///**
// * 催款计划表Service
// *
// * @author jdd
// * @version 1.0.0
// * @date 2017-03-07 14:21:58
// * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
// * 官方网站：www.yongqianbei.com
// *
// * 未经授权不得进行修改、复制、出售及商业使用
// */
//public interface UrgeRepayOrderService extends BaseService<UrgeRepayOrder, Long>{
//	/**
//	 * 催款记录信息
//	 * @param params
//	 * @param current
//	 * @param pageSize
//	 * @return
//	 */
//	Page<UrgeRepayOrder> list(Map<String, Object> params, int current,
//                              int pageSize);
//
//	/**
//	 * 催款记录详细信息
//	 * @param params
//	 * @param current
//	 * @param pageSize
//	 * @return
//	 */
//	Page<UrgeRepayOrderModel> listModel(Map<String, Object> params, int current,
//                                        int pageSize);
//	/**
//	 * 指派催收人员
//	 * @param params
//	 * @return
//	 */
//	int orderAllotUser(Map<String, Object> params);
//	/**
//	 * 指派催收人员
//	 * @param params
//	 * @return
//	 */
//	//int findByPrimary(String id);
//
//	/**
//	 * 根据借款id添加催收信息
//	 * @param id
//	 * @return
//	 */
//	Map<String, Object> saveOrder(Long id);
//	/**
//	 * 查询所有催收信息
//	 * @param hashMap
//	 * @return
//	 */
//	List<UrgeRepayOrder> listAll(HashMap<String, Object> hashMap);
//	/**
//	 *催收人员统计列表
//	 * @param params
//	 * @param current
//	 * @param pageSize
//	 * @return
//	 */
//	Page<UrgeRepayCountModel> memberCount(Map<String, Object> params, int current,
//                                          int pageSize);
//
//	/**
//	 * 催收订单统计
//	 * @param params
//	 * @param current
//	 * @param pageSize
//	 * @return
//	 */
//	Page<UrgeRepayCountModel> orderCount(Map<String, Object> params, int current,
//                                         int pageSize);
//	/**
//	 * 催回率统计
//	 * @param params
//	 * @param current
//	 * @param pageSize
//	 * @return
//	 */
//	Page<UrgeRepayCountModel> urgeCount(Map<String, Object> params, int current,
//                                        int pageSize);
//	/**
//	 * 催收员每日统计
//	 * @param params
//	 * @param current
//	 * @param pageSize
//	 * @return
//	 */
//	Page<UrgeRepayCountModel> memberDayCount(Map<String, Object> params,
//                                             int current, int pageSize);
//
//	/**
//	 * 查询催收订单
//	 * @param borrowId
//	 * @return
//	 */
//	UrgeRepayOrder findByBorrowId(long borrowId);
//	/**
//	 * 查询催收订单信息
//	 * @param id
//	 * @return
//	 */
//	UrgeRepayOrder findByPrimary(long id);
//	/**
//	 * 修改逾期信息
//	 * @param uroMap
//	 */
//	int updateLate(Map<String, Object> uroMap);
//
//	UrgeRepayOrder findOrderByMap(Map<String, Object> orderMap);
//
//	/**
//	 * 导出查询
//	 * @param params
//	 * @return
//	 */
//	List<?> listUrgeRepayOrder(Map<String, Object> params);
//
//	/**
//	 * 催收导出查询
//	 * @param params
//	 * @return
//	 */
//	List<?> listUrgeLog(Map<String, Object> params);
//
//	/**
//	 * 删除催收订单
//	 *
//	 * @param borrowId
//	 * @return
//	 */
//	boolean deleteByBorrowId(Long borrowId);
//
//
//}
