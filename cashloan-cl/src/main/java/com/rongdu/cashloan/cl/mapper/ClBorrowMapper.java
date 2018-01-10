package com.rongdu.cashloan.cl.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rongdu.cashloan.cl.model.ClBorrowModel;
import com.rongdu.cashloan.cl.model.IndexModel;
import com.rongdu.cashloan.cl.model.ManageBorrowExportModel;
import com.rongdu.cashloan.cl.model.ManageBorrowModel;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;
import com.rongdu.cashloan.core.domain.Borrow;

/**
 * 借款信息表Dao
 */
@RDBatisDao
public interface ClBorrowMapper extends BaseMapper<Borrow,Long> {
	
	/**
	 * 查询用户未完成的借款
	 * @param userId
	 * @return
	 */
	List<Borrow> findUserUnFinishedBorrow(@Param("userId")Long userId);

	/**
	 * 首页信息查询
	 * @return
	 */
	List<IndexModel> listIndex();

	/**
	 * 订单查询
	 * @param searchMap
	 * @return
	 */
	List<ClBorrowModel> findBorrow(Map<String, Object> searchMap);

	
	/**
	 * 查询
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listModel(Map<String, Object> params);

	/**
	 * 查询所有
	 * @param searchMap
	 * @return
	 */
	List<ClBorrowModel> listAll(Map<String, Object> searchMap);

	/**
	 * 逾期未入催
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listModelNotUrge(Map<String, Object> params);

	/**
	 * 查询未还款订单
	 * @param borrowMap
	 * @return
	 */
	Borrow findRepayBorrow(Map<String, Object> borrowMap);

	
	/**
	 * 更新借款状态
	 * @param
	 * @param id
	 * @return
	 */
	int updateState(@Param("state")String state,@Param("id")Long id);
	/**
	 * 借款部分还款信息
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listBorrowModel(Map<String, Object> params);

	/**
	 * 查询失败记录
	 * @param searchMap
	 * @return
	 */
	Borrow findLast(Map<String, Object> searchMap);
	
	/**
	 * 查询未还款
	 * @param paramMap
	 * @return
	 */
	Borrow findByUserIdAndState(Map<String, Object> paramMap);

    /**
     * 支付时更新Borrow状态
     * @param paramMap
     * @return
     */
    int updatePayState(Map<String,Object> paramMap);

	/**
	 * 统计成功借款次数
	 * @param l
	 * @return
	 */
	long countBorrow(long l);

	/**
	 * 导出查询
	 * @param params
	 * @return
	 */
	List<ManageBorrowExportModel> listExportModel(Map<String, Object> params);

	/**
	 * 人工复审修改状态
	 * @param
	 * @param
	 * @return
	 */
	int reviewState(Map<String, Object> map);

	/**
	 * 人工复审通过查询
	 * @return
	 */
	List<ManageBorrowModel> listReview(Map<String, Object> params);


	/**
	 *  人工复审时查看用户所有借款订单的信息（包含逾期天数和逾期金额）
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listBorrowModelForPersonCheck(Map<String, Object> params);

	/**
	 * 查询初审通过和初审建议人工复审的订单
	 * @param userId
	 * @return
	 */
//	List<Borrow> selectBorrowWithAudit(long userId);
//	begin pantheon 20170915 sj不支持OR
	List<Borrow> selectBorrowWithAuditState12(long userId);
	List<Borrow> selectBorrowWithAuditState14(long userId);

	/**
	 * 查询未还款的借款订单
	 * @param id
	 * @return
	 */
	Borrow selectUnpayBorrowById(long id);
    
}