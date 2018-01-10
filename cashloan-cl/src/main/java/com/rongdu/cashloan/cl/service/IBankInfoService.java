package com.rongdu.cashloan.cl.service;

import com.rongdu.cashloan.cl.domain.BankInfo;

import java.util.List;

public interface IBankInfoService {

    int deleteById(long id);

    List<BankInfo> getListByType(long showType);

    List<BankInfo> getAll();
}
