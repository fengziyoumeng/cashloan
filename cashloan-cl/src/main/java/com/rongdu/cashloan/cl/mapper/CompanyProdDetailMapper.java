package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.CompanyProdDetail;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

@RDBatisDao
public interface CompanyProdDetailMapper {
    /**
     *
     * @mbggenerated 2018-01-10
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int insert(CompanyProdDetail record);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int insertSelective(CompanyProdDetail record);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    CompanyProdDetail selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int updateByPrimaryKeySelective(CompanyProdDetail record);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int updateByPrimaryKeyWithBLOBs(CompanyProdDetail record);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int updateByPrimaryKey(CompanyProdDetail record);

    List<CompanyProdDetail> listCompanyprodDetail(CompanyProdDetail record);

    List<CompanyProdDetail> getAuditList();

    List<CompanyProdDetail> getAllListBySearch(Map params);

    List<CompanyProdDetail> getAuditStateList(Map params);

    CompanyProdDetail getProdDetailById(Long procId);

    void serviceAudit(CompanyProdDetail companyProdDetail);

    Map<String,Long>  getUserIdByProcId(Long procId);
}