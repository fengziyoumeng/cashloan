//package com.rongdu.cashloan.cl.serviceNoSharding;
//
//import java.util.List;
//import java.util.Map;
//
//import com.github.pagehelper.Page;
//import com.rongdu.cashloan.cl.domain.UrgeRepayOrder;
//import com.rongdu.cashloan.cl.domain.UrgeRepayOrderLog;
//import com.rongdu.cashloan.core.common.service.BaseService;
//
///**
// * 催款记录表Service
// *
// */
//public interface UrgeRepayOrderLogService extends BaseService<UrgeRepayOrderLog, Long>{
//	/**
//	 * 催款记录信息
//	 * @param params
//	 * @param current
//	 * @param pageSize
//	 * @return
//	 */
//	Page<UrgeRepayOrderLog> list(Map<String, Object> params, int current,
//			int pageSize);
//	/**
//	 * 根据条件查询催款记录信息
//	 * @param id
//	 * @return
//	 */
//	List<UrgeRepayOrderLog> getLogByParam(Map<String, Object> params);
//
//	/**
//	 * 保存催款记录信息
//	 * @param params
//	 * @return
//	 */
//	int saveOrderInfo(UrgeRepayOrderLog  orderLog,UrgeRepayOrder order);
//
//	/**
//	 * 删除催收记录
//	 *
//	 * @param dueId
//	 * @return
//	 */
//	boolean deleteByOrderId(Long dueId);
//}
