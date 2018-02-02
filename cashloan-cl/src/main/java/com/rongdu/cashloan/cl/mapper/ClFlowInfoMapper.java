package com.rongdu.cashloan.cl.mapper;


import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@RDBatisDao
public interface ClFlowInfoMapper extends BaseMapper<ClFlowInfo,Long> {
	int deleteById( @Param("id")long id);

	List<ClFlowInfo> getHot() throws Exception;

	List<ClFlowInfo> getAllHot(Map param) throws Exception;

	Long getTotal() throws Exception;

	List<ClFlowInfo> getAll(Map param) throws Exception;

	ClFlowInfo getDetail(Long id);

	ClFlowInfo getPnameByPCode(String pCode);

	String getUrl(Long id);

	List<ClFlowInfo> listSelectiveForUV(Map<String, Object> paramMap);

	List<ClFlowInfo> getAllPCode();

	List<ClFlowInfo> getAllPCodeForYesterday();

	List<ClFlowInfo> getAllToMonth();

	ClFlowInfo getAmountClick(Map<String, Object> paramMap);

	List<ClFlowInfo> getAllList();

	List<ClFlowInfo> getAllListOrderByTypeSort();

	String findName(Long id);

	Long queryCountByInsert(Map<String, Object> paramMap);

	Long queryCountByUpdate(Map<String, Object> paramMap);
}