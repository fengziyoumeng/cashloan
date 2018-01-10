//package com.rongdu.cashloan.cl.serviceNoSharding.impl;
//
//import java.util.ArrayList;
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
//import com.rongdu.cashloan.cl.domain.BorrowRepay;
//import com.rongdu.cashloan.cl.domain.UrgeRepayOrder;
//import com.rongdu.cashloan.cl.mapper.BorrowRepayMapper;
//import com.rongdu.cashloan.cl.mapper.ClBorrowMapper;
//import com.rongdu.cashloan.cl.mapper.UrgeRepayOrderMapper;
//import com.rongdu.cashloan.cl.model.UrgeRepayCountModel;
//import com.rongdu.cashloan.cl.model.UrgeRepayOrderModel;
//import com.rongdu.cashloan.cl.serviceNoSharding.UrgeRepayOrderService;
//import com.rongdu.cashloan.core.common.context.Constant;
//import com.rongdu.cashloan.core.common.mapper.BaseMapper;
//import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
//import com.rongdu.cashloan.core.domain.Borrow;
//import com.rongdu.cashloan.core.domain.UserBaseInfo;
//import com.rongdu.cashloan.core.mapper.UserBaseInfoMapper;
//import com.rongdu.cashloan.core.model.BorrowModel;
//
//
///**
// * 催款计划表ServiceImpl
// *
// * @author jdd
// * @version 1.0.0
// * @date 2017-03-07 14:21:58
// * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
// * 官方网站：www.yongqianbei.com
// *
// * 未经授权不得进行修改、复制、出售及商业使用
// */
//
//@Service("urgeRepayOrderServiceNoSharding")
//public class UrgeRepayOrderServiceImpl extends BaseServiceImpl<UrgeRepayOrder, Long> implements UrgeRepayOrderService {
//
//    @Resource
//    private UrgeRepayOrderMapper urgeRepayOrderMapper;
//    @Resource
//    private ClBorrowMapper clBorrowMapper;
//    @Resource
//    private BorrowRepayMapper borrowRepayMapper;
//    @Resource
//    private UserBaseInfoMapper userBaseinfoMapper;
//
//
//	@Override
//	public BaseMapper<UrgeRepayOrder, Long> getMapper() {
//		return urgeRepayOrderMapper;
//	}
//
//
//	@Override
//	public Page<UrgeRepayOrder> list(Map<String, Object> params, int current,
//			int pageSize) {
//		PageHelper.startPage(current, pageSize);
//		List<UrgeRepayOrder> list = urgeRepayOrderMapper.listSelective(params);
//		return (Page<UrgeRepayOrder>)list;
//	}
//
//
//	public Page<UrgeRepayOrderModel> listModel(Map<String, Object> params,
//			int current, int pageSize) {
//		PageHelper.startPage(current, pageSize);
//		List<UrgeRepayOrderModel> list = urgeRepayOrderMapper
//				.listModel(params);
//		return (Page<UrgeRepayOrderModel>) list;
//	}
//
//
//	@Override
//	public int orderAllotUser(Map<String, Object> params) {
//		int r=urgeRepayOrderMapper.updateSelective(params);
//		return r;
//	}
//
//
//	@Override
//	public Map<String, Object> saveOrder(Long id) {
//		Map<String, Object> result=new HashMap<String, Object>();
//		Borrow b=clBorrowMapper.findByPrimary(id);
//		if(b!=null){
//			//是否逾期标判断
//			if(b.getState().equals(BorrowModel.STATE_DELAY)){
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("borrowId", b.getId());
//				List<UrgeRepayOrder> list = urgeRepayOrderMapper.listSelective(params);
//				if(list.size()<=0){
//					UrgeRepayOrder order=new UrgeRepayOrder();
//					order.setBorrowId(b.getId());
//					order.setBorrowTime(b.getCreateTime());
//					order.setTimeLimit(b.getTimeLimit());
//
//				    params = new HashMap<String, Object>();
//					params.put("userId", b.getUserId());
//					UserBaseInfo user=userBaseinfoMapper.findSelective(params);
//					if(user!=null){
//					order.setPhone(user.getPhone());
//					order.setBorrowName(user.getRealName());
//					}
//					params = new HashMap<String, Object>();
//					params.put("borrowId", b.getId());
//				    BorrowRepay br=borrowRepayMapper.findSelective(params);
//				    if(br!=null){
//				    order.setAmount(br.getAmount());
//				    order.setRepayTime(br.getRepayTime());
//					order.setPenaltyDay(Long.valueOf(br.getPenaltyDay()));
//					order.setPenaltyAmout(br.getPenaltyAmout());
//					order.setLevel(UrgeRepayOrderModel.getLevelByDay(Long.valueOf(br.getPenaltyDay())));
//				    }
//
//					order.setState(UrgeRepayOrderModel.STATE_ORDER_PRE);
//					order.setCreateTime(new Date());
//					order.setCount(Long.valueOf(0));
//
//					urgeRepayOrderMapper.save(order);
//
//					result.put("code",  Constant.SUCCEED_CODE_VALUE);
//					result.put("msg", "提交成功");
//					return result;
//				}else{
//					result.put("code",  Constant.FAIL_CODE_VALUE);
//					result.put("msg", "已存在催收订单中，请勿重复添加");
//				}
//			}else{
//				result.put("code",  Constant.FAIL_CODE_VALUE);
//				result.put("msg", "借款信息未逾期");
//			}
//		}else{
//			result.put("code",  Constant.FAIL_CODE_VALUE);
//			result.put("msg", "借款信息不存在");
//		}
//		return result;
//	}
//
//	@Override
//	public List<UrgeRepayOrder> listAll(HashMap<String, Object> hashMap) {
//		List<UrgeRepayOrder> list = urgeRepayOrderMapper.listSelective(hashMap);
//		return list;
//	}
//
//	@Override
//	public Page<UrgeRepayCountModel> memberCount(Map<String, Object> params,
//			int current, int pageSize) {
//		if(params==null){
//			params=new HashMap<String, Object> ();
//		}
//		Map<String, Object> params2 = new HashMap<String, Object>();
//		List<UrgeRepayOrder> orlist = urgeRepayOrderMapper.listSelective(params2);
//		List<Long> ids = new ArrayList<Long>();
//		for (UrgeRepayOrder or : orlist) {
//			if (null != or.getUserId() && or.getUserId() != 0) {
//				ids.add(or.getUserId());
//			}
//		}
//
//		if(!ids.isEmpty()){
//			params.put("idList", ids);
//		} else {
//			params.put("idList", null);
//		}
//
//		PageHelper.startPage(current, pageSize);
//		List<UrgeRepayCountModel> list = urgeRepayOrderMapper.memberCount(params);
//		return (Page<UrgeRepayCountModel>)list;
//	}
//
//
//	@Override
//	public Page<UrgeRepayCountModel> orderCount(Map<String, Object> params,
//			int current, int pageSize) {
//		PageHelper.startPage(current, pageSize);
//		List<UrgeRepayCountModel> list = urgeRepayOrderMapper.orderCount(params);
//		return (Page<UrgeRepayCountModel>)list;
//	}
//
//
//	@Override
//	public Page<UrgeRepayCountModel> urgeCount(Map<String, Object> params,
//			int current, int pageSize) {
//		PageHelper.startPage(current, pageSize);
//		List<UrgeRepayCountModel> list = urgeRepayOrderMapper.urgeCount(params);
//		return (Page<UrgeRepayCountModel>)list;
//	}
//
//
//	@Override
//	public Page<UrgeRepayCountModel> memberDayCount(Map<String, Object> params,
//			int current, int pageSize) {
//		PageHelper.startPage(current, pageSize);
//		List<UrgeRepayCountModel> list = urgeRepayOrderMapper.memberDayCount(params);
//		return (Page<UrgeRepayCountModel>)list;
//	}
//
//
//	@Override
//	public UrgeRepayOrder findByBorrowId(long borrowId) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("borrowId", borrowId);
//		return urgeRepayOrderMapper.findSelective(map);
//	}
//	@Override
//	public UrgeRepayOrder findByPrimary(long id) {
//		return urgeRepayOrderMapper.findByPrimary(id);
//	}
//
//	@Override
//	public int updateLate(Map<String, Object> uroMap) {
//		return urgeRepayOrderMapper.updateSelective(uroMap);
//	}
//
//
//	@Override
//	public UrgeRepayOrder findOrderByMap(Map<String, Object> orderMap) {
//		return urgeRepayOrderMapper.findSelective(orderMap);
//	}
//
//
//	@Override
//	public List<?> listUrgeRepayOrder(Map<String, Object> params) {
//		List<UrgeRepayOrder> list = urgeRepayOrderMapper.listSelective(params);
//		for (UrgeRepayOrder uro : list) {
//			uro.setState(UrgeRepayOrderModel.change(uro.getState()));
//		}
//		return list;
//	}
//
//
//	@Override
//	public List<?> listUrgeLog(Map<String, Object> params) {
//		List<UrgeRepayOrderModel> list = urgeRepayOrderMapper.listModel(params);
//		for (UrgeRepayOrderModel uroModel : list) {
//			uroModel.setState(UrgeRepayOrderModel.change(uroModel.getState()));
//		}
//		return list;
//	}
//
//	@Override
//	public boolean deleteByBorrowId(Long borrowId) {
//		int count = urgeRepayOrderMapper.deleteByBorrowId(borrowId);
//		if (count > 0L) {
//			return true;
//		}
//		return false;
//	}
//
//}