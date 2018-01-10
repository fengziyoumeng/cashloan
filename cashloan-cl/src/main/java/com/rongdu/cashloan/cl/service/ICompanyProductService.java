package com.rongdu.cashloan.cl.service;

import com.rongdu.cashloan.cl.domain.CompanyProdDetail;

import java.util.List;
import java.util.Map;

public interface ICompanyProductService {

    void saveOrUpdate(CompanyProdDetail companyProdDetail) throws Exception;

    Map<String, Object> listHomeBdata();
}
