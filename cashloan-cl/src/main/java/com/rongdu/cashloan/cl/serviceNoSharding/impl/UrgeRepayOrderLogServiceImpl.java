//package com.rongdu.cashloan.cl.serviceNoSharding.impl;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.rongdu.cashloan.cl.domain.BorrowProgress;
//import com.rongdu.cashloan.cl.domain.UrgeRepayOrder;
//import com.rongdu.cashloan.cl.domain.UrgeRepayOrderLog;
//import com.rongdu.cashloan.cl.mapper.BorrowProgressMapper;
//import com.rongdu.cashloan.cl.mapper.ClBorrowMapper;
//import com.rongdu.cashloan.cl.mapper.UrgeRepayOrderLogMapper;
//import com.rongdu.cashloan.cl.mapper.UrgeRepayOrderMapper;
//import com.rongdu.cashloan.cl.model.UrgeRepayOrderModel;
//import com.rongdu.cashloan.cl.serviceNoSharding.UrgeRepayOrderLogService;
//import com.rongdu.cashloan.core.common.mapper.BaseMapper;
//import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
//import com.rongdu.cashloan.core.domain.Borrow;
//import com.rongdu.cashloan.core.model.BorrowModel;
//
///**
// * 催款记录表ServiceImpl
// *
// * @author jdd
// * @version 1.0.0
// * @date 2017-03-07 14:28:22
// * Copyright 杭州民华金融信息服务有限公司 arc All Rights Reserved
// * 官方网站：www.yongqianbei.com
// * 未经授权不得进行修改、复制、出售及商业使用
// */
//
//@Service("urgeRepayOrderLogServiceNoSharding")
//public class UrgeRepayOrderLogServiceImpl extends BaseServiceImpl<UrgeRepayOrderLog, Long> implements UrgeRepayOrderLogService {
//	 @Resource
//     private UrgeRepayOrderMapper urgeRepayOrderMapper;
//
//	@Resource
//	private UrgeRepayOrderLogMapper urgeRepayOrderLogMapper;
//
//    @Resource
//    private ClBorrowMapper clBorrowMapper;
//    
//    @Resource
//    private BorrowProgressMapper borrowProgressMapper;
//
//	@Override
//	public BaseMapper<UrgeRepayOrderLog, Long> getMapper() {
//		return urgeRepayOrderLogMapper;
//	}
//
//	@Override
//	public Page<UrgeRepayOrderLog> list(Map<String, Object> params,
//			int current, int pageSize) {
//		PageHelper.startPage(current, pageSize);
//		List<UrgeRepayOrderLog> list = urgeRepayOrderLogMapper
//				.listSelective(params);
//		return (Page<UrgeRepayOrderLog>) list;
//	}
//
//	@Override
//	public List<UrgeRepayOrderLog> getLogByParam(Map<String, Object> params) {
//		return urgeRepayOrderLogMapper.listSelective(params);
//	}
//
//	@Override
//	public int saveOrderInfo(UrgeRepayOrderLog  orderLog,UrgeRepayOrder order) {
//		 orderLog.setDueId(order.getId());
//		 orderLog.setBorrowId(order.getBorrowId());
//		 orderLog.setUserId(order.getUserId());
//		 //更新催收记录
//		 int count=urgeRepayOrderLogMapper.save(orderLog);
//		 //更新催收订单进度
//		 order.setCount(order.getCount()+1);
//		 order.setState(orderLog.getState());
//		 urgeRepayOrderMapper.update(order);
//		 if(order.getState().equals(UrgeRepayOrderModel.STATE_ORDER_BAD)){
//			//更新借款状态
//			Borrow b=clBorrowMapper.findByPrimary(order.getBorrowId());
//			Map<String,Object> stateMap = new HashMap<String,Object>();
//			stateMap.put("id", order.getBorrowId());
//			stateMap.put("state", BorrowModel.STATE_BAD);
//			clBorrowMapper.updateSelective(stateMap);
//			//添加借款进度
//			BorrowProgress bp=new BorrowProgress();
//			bp.setBorrowId(b.getId());
//			bp.setUserId(b.getUserId());
//			bp.setRemark(BorrowModel.convertBorrowRemark(BorrowModel.STATE_BAD));
//			bp.setState(BorrowModel.STATE_BAD);
//			bp.setCreateTime(new Date());
//			borrowProgressMapper.save(bp);
//		 }
//		return count;
//	}
//
//	@Override
//	public boolean deleteByOrderId(Long dueId) {
//		int count = urgeRepayOrderLogMapper.deleteByOrderId(dueId);
//		if (count > 0L) {
//			return true;
//		}
//		return false;
//	}
//
//}