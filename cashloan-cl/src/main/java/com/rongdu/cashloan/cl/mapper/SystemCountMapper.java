package com.rongdu.cashloan.cl.mapper;

import java.util.List;
import java.util.Map;

import com.rongdu.cashloan.core.common.mapper.RDBatisDao;




@RDBatisDao
public interface SystemCountMapper {
	/**
	 * 统计当日注册用户数量
	 * @return
	 */
	Integer todayRegister();


	/**
	 * 统计当月注册用户数量
	 * @return
	 */
	Integer monthRegister();


	/**
	 * 总注册用户数量
	 * @return
	 */
	Integer totalRegister();


	/**
	 *  15日内用户注册数分布图
	 * @return
	 */
	List<Map<String,Object>> countFifteenDaysRegister();

	/**
	 *  15日内用户注册数分布图
	 * @return
	 */
	List<Map<String,Object>> countPnameUV();



	/**
	 * 统计当日登陆用户数量
	 * @return
	 */
	Integer countLogin();
	
	/**
	 * 统计当日借款申请通过的数量
	 * @return
	 */
	double countBorrow();
	
	/**
	 * 统计当日借款申请通过的数量
	 * @return
	 */
	double countBorrowPass();
	
	/**
	 * 统计当日借款申请放款数量
	 * @return
	 */
	Integer countBorrowLoan();
	
	/**
	 * 统计当日还款量
	 * @return
	 */
	Integer countBorrowRepay();
	
	/**
	 * 统计历史放款总量
	 * @return
	 */
	Integer countBorrowLoanHistory();
	
	/**
	 * 统计历史还款总量
	 * @return
	 */
	Integer countBorrowRepayHistory();
	
	/**
	 * 待还款总额
	 * @return
	 */
	Double sumBorrowNeedRepay();
	
	/**
	 * 逾期未还款总额
	 * @return
	 */
	Double sumBorrowOverdueRepay();
	
	/**
	 * 当月融资金额(按地区分组)
	 * @return
	 */
	List<Map<String,Object>> sumMonthBorrowAmtByProvince();
	
	/**
	 * 当月借款次数(按地区分组)
	 * @return
	 */
	List<Map<String,Object>> countMonthBorrowByProvince();
	
	/**
	 * 当月还款金额(按地区分组)
	 * @return
	 */
	List<Map<String,Object>> sumMonthBorrowRepayByProvince(Map<String, Object> borrowIdMap);
	
	/**
	 * 当月新增用户(按地区分组)
	 * @return
	 */
	List<Map<String,Object>> countMonthRegisterByProvince();
	
	/**
	 * 最近15日每天放款量
	 * @return
	 */
	List<Map<String,Object>> countFifteenDaysLoan();
	
	/**
	 * 还款来源 10代扣，20银行卡转账，30支付宝转账
	 * @return
	 */
	List<Map<String,Object>> countRepaySource();
	
//	/**
//	 * 最近15日应还款量
//	 * @return
//	 */
//	List<Map<String,Object>> countFifteenDaysNeedRepay();
	
	/**
	 * 最近15日实际还款量
	 * @return
	 */
	List<Map<String,Object>> countFifteenDaysRealRepay();


	/**
	 * 查询借款进度表，状态为40,41的
	 * @return
	 */
	List<Map<String,Object>> queryBorrowProgressBorrowId();
}
