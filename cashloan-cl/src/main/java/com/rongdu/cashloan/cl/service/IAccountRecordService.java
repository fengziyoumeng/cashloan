package com.rongdu.cashloan.cl.service;


import com.rongdu.cashloan.cl.domain.AccountRecord;
import com.rongdu.cashloan.core.common.service.BaseService;

import java.util.List;
import java.util.Map;

public interface IAccountRecordService extends BaseService<AccountRecord, Long> {

    /**
     * 根据p_code和userId来查询该用户所有账单
     * @return
     */
    Map<String,Object> totalAccRecords(Map<String,Object> paramsMap);

    List<AccountRecord> listAccRecords(Map<String,Object> paramsMap);

    AccountRecord queryRecordsDetail(Map<String,Object> paramsMap);

    int updateRepayStatus(Map<String, Object> paramsMap);

    int deleteStatus(Long id);

    int batchUpdateRemRepDay(List<AccountRecord> accountRecords);
}
