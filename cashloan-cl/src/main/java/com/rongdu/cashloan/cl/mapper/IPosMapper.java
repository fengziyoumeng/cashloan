package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.PosInfo;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;

@RDBatisDao
public interface IPosMapper extends BaseMapper<PosInfo,Long> {

    List<PosInfo> listPosInfo();
}
