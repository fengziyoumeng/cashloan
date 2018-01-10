package com.rongdu.cashloan.cl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rongdu.cashloan.cl.domain.Credit;
import com.rongdu.cashloan.cl.domain.UserAuth;
import com.rongdu.cashloan.cl.mapper.ClBorrowMapper;
import com.rongdu.cashloan.cl.mapper.CreditMapper;
import com.rongdu.cashloan.cl.mapper.UserAuthMapper;
import com.rongdu.cashloan.cl.model.ClBorrowModel;
import com.rongdu.cashloan.cl.model.IndexModel;
import com.rongdu.cashloan.cl.model.ManageBorrowExportModel;
import com.rongdu.cashloan.cl.model.ManageBorrowModel;
import com.rongdu.cashloan.cl.service.ClBorrowService;
import com.rongdu.cashloan.cl.service.ClSmsService;
import com.rongdu.cashloan.cl.service.UserAuthService;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.exception.BussinessException;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.core.common.util.DateUtil;
import com.rongdu.cashloan.core.common.util.NidGenerator;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.domain.Borrow;
import com.rongdu.cashloan.core.domain.UserBaseInfo;
import com.rongdu.cashloan.core.mapper.UserBaseInfoMapper;
import com.rongdu.cashloan.core.mapper.UserMapper;
import com.rongdu.cashloan.core.model.BorrowModel;
import com.rongdu.cashloan.core.service.UserBaseInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tool.util.BigDecimalUtil;

import javax.annotation.Resource;
import java.util.*;


/**
 * 借款信息表ServiceImpl
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 10:36:53 
 * Copyright 杭州民华金融信息服务有限公司 arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
@Service("clBorrowService")
@Transactional(rollbackFor = Exception.class)
public class ClBorrowServiceImpl extends BaseServiceImpl<Borrow, Long> implements ClBorrowService {

	private static final Logger logger = LoggerFactory.getLogger(ClBorrowServiceImpl.class);

	@Resource
	private ClBorrowMapper clBorrowMapper;

	@Resource
	private CreditMapper creditMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserAuthMapper userAuthMapper;
	@Resource
	private UserAuthService userAuthService;
	@Resource
	private UserBaseInfoMapper userBaseInfoMapper;
	@Resource
	private ClSmsService clSmsService;

	@Resource
	private UserBaseInfoService userBaseInfoService;

	@Resource
	private DataSourceTransactionManager transactionManager;


	public BaseMapper<Borrow, Long> getMapper() {
		return clBorrowMapper;
	}


	public String getQcOrderNo(){
		Random random = new Random();
		String reqOrderNo = "";
 	   	for(int i=0;i<9;i++){
 		   int a = random.nextInt(10);
 		   reqOrderNo += a;
 	   	}
 	   	return reqOrderNo;
	}

	@Override
	public Page<ClBorrowModel> page(Map<String, Object> searchMap, int current,
			int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<ClBorrowModel> list = clBorrowMapper.listAll(searchMap);
		for (ClBorrowModel clBorrowModel : list) {
			clBorrowModel.setCreditTimeStr(DateUtil.dateStr(
					clBorrowModel.getCreateTime(), "yyyy-MM-dd HH:mm"));
			clBorrowModel.setStateStr(clBorrowModel.getState());
			if ("审核通过".equals(clBorrowModel.getStateStr())||"放款失败".equals(clBorrowModel.getStateStr())) {
				clBorrowModel.setState("打款中");
				clBorrowModel.setStateStr("打款中");
			}
			if ("还款中".equals(clBorrowModel.getStateStr())) {
				clBorrowModel.setState("待还款");
				clBorrowModel.setStateStr("待还款");
			}
		}
		return (Page<ClBorrowModel>) list;
	}

	@Override
	public List<IndexModel> listIndex() {
		List<IndexModel> list = clBorrowMapper.listIndex();
		List indexList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			String cardNo = list.get(i).getCardNo();
			if (StringUtil.isBlank(cardNo)) {
				continue;
			}
			cardNo = StringUtil.substring(cardNo, cardNo.length() - 4);
			double money = list.get(i).getAmount();
//			int time = 0;
//			if (list.get(i) != null && list.get(i).getCreateTime() != null
//					&& list.get(i).getLoanTime() != null) {
//				time = DateUtil.minuteBetween(list.get(i).getCreateTime(), list
//						.get(i).getLoanTime());
//			}
			//String value = "尾号" + cardNo + " " + "成功借款" + (int) (money) + "元 用时" + time + "分钟";
			String value = "尾号" + cardNo + " " + "成功借款" + (int) (money) + "元";  //wangxb修改文案
			Map<String, Object> map = new HashMap<>();
			map.put("id", i);
			map.put("value", value);
			indexList.add(map);
		}
		return indexList;
	}



	@Override
	public Map<String, Object> choice(double amount, String timeLimit) {
		String fee_ = Global.getValue("fee");// 综合费用
		String[] fees = fee_.split(",");
		String borrowDay = Global.getValue("borrow_day");// 借款天数
		String[] days = borrowDay.split(",");
		double infoAuthFee = Double.valueOf(Global.getValue("info_auth_fee"));// 信息认证费
		double serviceFee = Double.valueOf(Global.getValue("service_fee"));// 居间服务费
		double interest = Double.valueOf(Global.getValue("interest"));// 利息
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("timeLimit", timeLimit);
		
		double fee = 0;
		for (int i = 0; i < days.length; i++) {
			if (timeLimit.equals(days[i])) {
				fee = BigDecimalUtil.round(BigDecimalUtil.mul(amount, Double.parseDouble(fees[i])));
				map.put("fee", BigDecimalUtil.decimal(fee, 2));
				
				String beheadFee = Global.getValue("behead_fee");// 是否启用砍头息
				if (beheadFee.equals("10")) {// 启用
					map.put("realAmount", amount - fee);
				} else {
					map.put("realAmount", amount);
				}
			}
		}
		
		Map<String, Object> feeDetail = new HashMap<String, Object>();
		infoAuthFee = BigDecimalUtil.round(BigDecimalUtil.mul(fee, infoAuthFee));
		serviceFee = BigDecimalUtil.round(BigDecimalUtil.mul(fee, serviceFee));
		interest = BigDecimalUtil.sub(fee, infoAuthFee, serviceFee);
		
		feeDetail.put("infoAuthFee", infoAuthFee);
		feeDetail.put("serviceFee", serviceFee);
		feeDetail.put("interest", interest);
		
		map.put("feeDetail", feeDetail);

		return map;
	}

	@Override
	public Borrow saveBorrow(Borrow borrow) {
		String fee_ = Global.getValue("fee");// 综合费用
		String[] fees = fee_.split(",");
		String borrowDay = Global.getValue("borrow_day");// 借款天数
		String[] days = borrowDay.split(",");
		double serviceFee = Double.valueOf(Global.getValue("service_fee"));// 服务费
		double infoAuthFee = Double.valueOf(Global.getValue("info_auth_fee"));// 信息认证费
		double interest = Double.valueOf(Global.getValue("interest"));// 利息
		String beheadFee = Global.getValue("behead_fee");// 是否启用砍头息

		double fee = 0;
		for (int i = 0; i < days.length; i++) {
			if (borrow.getTimeLimit().equals(days[i])) {
				fee = BigDecimalUtil.round(BigDecimalUtil.mul(borrow.getAmount(), Double.parseDouble(fees[i])));
				borrow.setFee(fee);
				if ("10".equals(beheadFee)) {
					borrow.setRealAmount(borrow.getAmount() - fee);
				} else {
					borrow.setRealAmount(borrow.getAmount());
				}
			}
		}

		if (StringUtil.equals("dev", Global.getValue("app_environment"))
				&&StringUtil.equals("1", borrow.getTimeLimit())) {
			fee = BigDecimalUtil.round(BigDecimalUtil.mul(borrow.getAmount(), 0.01));
			borrow.setFee(fee);
			if (beheadFee.equals("10")) {
				borrow.setRealAmount(borrow.getAmount() - fee);
			} else {
				borrow.setRealAmount(borrow.getAmount());
			}
		}

		infoAuthFee = BigDecimalUtil.round(BigDecimalUtil.mul(fee, infoAuthFee));
		serviceFee = BigDecimalUtil.round(BigDecimalUtil.mul(fee, serviceFee));
		interest = BigDecimalUtil.sub(fee, infoAuthFee, serviceFee);

		borrow.setInfoAuthFee(infoAuthFee);
		borrow.setServiceFee(serviceFee);
		borrow.setInterest(interest);

		borrow.setOrderNo(NidGenerator.getOrderNo());
		borrow.setState(BorrowModel.STATE_PRE);
		borrow.setCreateTime(DateUtil.getNow());

		//wangxb原先这里的save又判断了一次借款状态，isCanborrow已经判断了故去掉,其它接口都要调用下isCanborrow接口
		clBorrowMapper.save(borrow);

		return borrow;
	}

	@Override
	public List<Borrow> findBorrowByMap(Map<String, Object> searchMap) {
		List<Borrow> list = clBorrowMapper.listSelective(searchMap);
		return list;
	}

	@Override
	public Page<ManageBorrowModel> listModel(Map<String, Object> params,
			int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<ManageBorrowModel> list = clBorrowMapper.listModel(params);
		return (Page<ManageBorrowModel>) list;
	}


	@Override
	public List<ManageBorrowModel> listByMap(Map<String, Object> params) {
		List<ManageBorrowModel> list = clBorrowMapper.listModel(params);
		return  list;
	}


	@Override
	public int updateSelective(Map<String, Object> data) {
		String state = data.get("state").toString();
		if(StringUtils.isNotEmpty(state)){
			if(BorrowModel.STATE_PASS.equals(state)){
				//人工复审通过
				data.put("stateSuccess", BorrowModel.STATE_SUCCESS_PASS);
			}else if(BorrowModel.STATE_AUTO_PASS.equals(state)){
				//自动审核通过
				data.put("stateSuccess", BorrowModel.STATE_SUCCESS_AUTO_PASS);
			}
		}
		return clBorrowMapper.updateSelective(data);
	}


	/**
	 * 修改标的状态
	 *
	 * @param id
	 * @param state
	 */
	@Override
	public int modifyState(long id, String state) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("state", state);
		if(BorrowModel.STATE_PASS.equals(state)){
			//人工复审通过
			paramMap.put("stateSuccess", BorrowModel.STATE_SUCCESS_PASS);
		}else if(BorrowModel.STATE_AUTO_PASS.equals(state)){
			//自动审核通过
			paramMap.put("stateSuccess", BorrowModel.STATE_SUCCESS_AUTO_PASS);
		}
		paramMap.put("id", id);
		return clBorrowMapper.updateSelective(paramMap);
	}

	/**
	 * 信用额度修改
	 * 
	 * @param userId
	 * @param amount
	 * @param type
	 */
	@Override
	public int modifyCredit(Long userId, double amount, String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> creditMap = new HashMap<String, Object>();
		creditMap.put("consumerNo", userId);
		Credit credit = creditMapper.findSelective(creditMap);
		if (credit != null) {
			params.put("id", credit.getId());
			if ("used".equals(type)) {
				params.put("used", amount);
				params.put("unuse", - amount);
			} else {
				params.put("used", - amount);
				params.put("unuse", amount);
			}
			params.put("consumerNo",userId);  //增加路由条件
			int result = creditMapper.updateAmount(params);
			
			if(result != 1){
				throw new BussinessException("更新额度失败，不能出现负值，type：" + type + ",userId:" + userId);
			}
			return result;
		} else {
			throw new BussinessException("更新额度失败，未找到用户额度信息，userId:" + userId);
		}
	}



	@Override
	public Page<ManageBorrowModel> listBorrowModel(Map<String, Object> params, int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<ManageBorrowModel> list = clBorrowMapper.listBorrowModel(params);
		return (Page<ManageBorrowModel>) list;
	}

	@Override
	public Borrow findLast(Map<String, Object> searchMap) {
		return clBorrowMapper.findLast(searchMap);
	}

	@Override
	public void updatePayState(Map<String, Object> paramMap){
		int result  =  clBorrowMapper.updatePayState(paramMap);
		if(result < 1){
			throw new BussinessException("当前借款状态不允许修改!");
		}
	}

	@Override
	public Borrow findByPrimary(Long borrowId) {
		return clBorrowMapper.findByPrimary(borrowId);
	}


	public List listBorrow(Map<String, Object> params) {
		List<ManageBorrowExportModel> list = clBorrowMapper.listExportModel(params);
		for (ManageBorrowExportModel model : list) {
			model.setState(BorrowModel.apiConvertBorrowState(model.getState()));
			UserBaseInfo ubi = userBaseInfoMapper.findByUserId(model.getUserId());
			if (ubi!=null) {
				model.setRealName(ubi.getRealName());
				model.setPhone(ubi.getPhone());
			}
			Map<String, Object> params2 = new HashMap<>();
			params2.put("borrowId", model.getId());
			params2.put("state", BorrowModel.STATE_REPAY);
			
		}
		return list;
	}

	@Override
	public Page<ManageBorrowModel> listReview(Map<String, Object> params,int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<ManageBorrowModel> list = clBorrowMapper.listReview(params);
		return (Page<ManageBorrowModel>) list;
	}



	@Override
	public Borrow selectUnpayBorrowById(long id) {
		return clBorrowMapper.selectUnpayBorrowById(id);
	}

	private Map<String,Object>  getZmRequiredAuthState(UserAuth userAuth){
		int result = 0;
		String idState = userAuth.getIdState();
		if(StringUtils.isNotBlank(idState) && "30".equals(idState)){
			result+=1;
		}
		String phoneState = userAuth.getPhoneState();
		if(StringUtils.isNotBlank(phoneState) && "30".equals(phoneState)){
			result+=1;
		}
		String contactState = userAuth.getContactState();
		if(StringUtils.isNotBlank(contactState) && "30".equals(contactState)){
			result+=1;
		}
		String bankCardState = userAuth.getBankCardState();
		if(StringUtils.isNotBlank(bankCardState) && "30".equals(bankCardState)){
			result+=1;
		}
		String workInfoState = userAuth.getWorkInfoState();
		if(StringUtils.isNotBlank(workInfoState) && "30".equals(workInfoState)){
			result+=1;
		}
		String ortherInfoState = userAuth.getOtherInfoState();
		if(StringUtils.isNotBlank(ortherInfoState) && "30".equals(ortherInfoState)){
			result+=1;
		}
		String zhimaState = userAuth.getZhimaState();
		if(StringUtils.isNotBlank(zhimaState) && "30".equals(zhimaState)){
			result+=1;
		}
		String qualified = "0";

		if( "30".equals(idState) && "30".equals(phoneState) && "30".equals(contactState) &&
		   "30".equals(bankCardState) && "30".equals(zhimaState) ){
			qualified = "1";
		}
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap.put("qualified",qualified);
		resultMap.put("total",7);
		resultMap.put("result",result);
		return resultMap;
	}

	private Map<String,Object>  getZmOptionalAuthState(UserAuth userAuth){
		int result = 0;
		String idState = userAuth.getIdState();
		if(StringUtils.isNotBlank(idState) && "30".equals(idState)){
			result+=1;
		}
		String phoneState = userAuth.getPhoneState();
		if(StringUtils.isNotBlank(phoneState) && "30".equals(phoneState)){
			result+=1;
		}
		String contactState = userAuth.getContactState();
		if(StringUtils.isNotBlank(contactState) && "30".equals(contactState)){
			result+=1;
		}
		String bankCardState = userAuth.getBankCardState();
		if(StringUtils.isNotBlank(bankCardState) && "30".equals(bankCardState)){
			result+=1;
		}
		String qualified = "0";
		if( "30".equals(idState) && "30".equals(phoneState) && "30".equals(contactState) &&
				"30".equals(bankCardState) ){
			qualified = "1";
		}
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap.put("qualified",qualified);
		resultMap.put("total",4);
		resultMap.put("result",result);
		return resultMap;
	}

	private Map<String,Object>  getZmRemoveAuthState(UserAuth userAuth){
		int result = 0;
		String idState = userAuth.getIdState();
		if(StringUtils.isNotBlank(idState) && "30".equals(idState)){
			result+=1;
		}
		String phoneState = userAuth.getPhoneState();
		if(StringUtils.isNotBlank(phoneState) && "30".equals(phoneState)){
			result+=1;
		}
		String contactState = userAuth.getContactState();
		if(StringUtils.isNotBlank(contactState) && "30".equals(contactState)){
			result+=1;
		}
		String bankCardState = userAuth.getBankCardState();
		if(StringUtils.isNotBlank(bankCardState) && "30".equals(bankCardState)){
			result+=1;
		}
		String qualified = "0";
		if( "30".equals(idState) && "30".equals(phoneState) && "30".equals(contactState) &&
				"30".equals(bankCardState) ){
			qualified = "1";
		}
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap.put("qualified",qualified);
		resultMap.put("total",4);
		resultMap.put("result",result);
		return resultMap;
	}

}