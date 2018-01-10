package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.AdInfo;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;
@RDBatisDao
public interface AdInfoMapper extends BaseMapper<AdInfo,Long> {
    int deleteByPrimaryKey(Long id);

    int insert(AdInfo record);

    AdInfo selectByPrimaryKey(Long id);

    List<AdInfo> selectAll();

    int updateByPrimaryKey(AdInfo record);

    List<AdInfo> selectByAdInfo(AdInfo record);
}