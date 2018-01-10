package com.rongdu.cashloan.cl.mapper;


import com.rongdu.cashloan.cl.domain.BankInfo;
import com.rongdu.cashloan.cl.domain.ClickTrack;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;


@RDBatisDao
public interface ClickTrackMapper extends BaseMapper<ClickTrack,Long> {

	int deleteById(long id);

	List<ClickTrack> queryTrailRecodes(Map<String, Object> paramMap);

	List<ClickTrack> queryTrailsByDate(String recodeDate);

	//总UV
	Long totalUV(Map<String,Object> map);

	//去重后总UV
	Long duplicateUV(Map<String,Object> map);

	//去重后注册UV
	Long registerUV(Map<String,Object> map);
}