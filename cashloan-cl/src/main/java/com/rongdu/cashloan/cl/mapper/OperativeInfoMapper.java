package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.OperativeInfo;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;

@RDBatisDao
public interface OperativeInfoMapper {
    /**
     *
     * @mbggenerated 2018-01-09
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int insert(OperativeInfo record);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int insertSelective(OperativeInfo record);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    OperativeInfo selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int updateByPrimaryKeySelective(OperativeInfo record);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int updateByPrimaryKey(OperativeInfo record);

    List<OperativeInfo> listOperativeInfo(OperativeInfo record);
}