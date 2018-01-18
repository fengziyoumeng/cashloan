package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.CompanyProd;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;

@RDBatisDao
public interface CompanyProdMapper {
    /**
     *
     * @mbggenerated 2018-01-09
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int insert(CompanyProd record);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int insertSelective(CompanyProd record);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    CompanyProd selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int updateByPrimaryKeySelective(CompanyProd record);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int updateByPrimaryKey(CompanyProd record);

    //------------------------------------------------------------
    List<CompanyProd> listCompanyProd (CompanyProd record);

    List<CompanyProd> listSelective ();


}