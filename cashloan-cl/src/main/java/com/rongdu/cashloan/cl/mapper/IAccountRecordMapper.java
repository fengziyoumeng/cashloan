package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.AccountRecord;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

@RDBatisDao
public interface IAccountRecordMapper extends BaseMapper<AccountRecord,Long> {

    List<AccountRecord> listAccRecords(Map<String, Object> paramsMap);

    Map<String, Object> queryTotalAmtByTh(Map<String, Object> paramsMap);

    AccountRecord queryRecordsDetail(Map<String, Object> paramsMap);

    int updateRepayStatus(Map<String, Object> paramsMap);

    int deleteStatus(Long id);

    int batchUpdateRemRepDay(List<AccountRecord> accountRecords);
}
