package com.rongdu.cashloan.cl.mapper;


import com.rongdu.cashloan.cl.domain.BankInfo;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;


@RDBatisDao
public interface BankInfoMapper extends BaseMapper<BankInfo,Long> {

	int deleteById(long id);

	List<BankInfo> getListByType(long showType);

	List<BankInfo> getAll();

	String findName(Long id);
}