package com.rongdu.cashloan.cl.service;

import com.rongdu.cashloan.cl.domain.AdInfo;

import java.util.List;

public interface AdInfoService {

    List<AdInfo> selectAll();

    void deleteAdInfoByid(Long id,String adUrl) throws Exception;

    void saveOrUpdate(String data) throws Exception;
}
