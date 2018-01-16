package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.BannerInfo;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;

@RDBatisDao
public interface BannerInfoMapper {
    /**
     *
     * @mbggenerated 2018-01-10
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int insert(BannerInfo record);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int insertSelective(BannerInfo record);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    BannerInfo selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int updateByPrimaryKeySelective(BannerInfo record);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int updateByPrimaryKey(BannerInfo record);

    List<BannerInfo> selectByBannerInfo(BannerInfo record);

    List<BannerInfo> selectAll();

}