package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.AccountRecord;
import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.mapper.ClFlowInfoMapper;
import com.rongdu.cashloan.cl.mapper.IAccountRecordMapper;
import com.rongdu.cashloan.cl.service.IAccountRecordService;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("IAccountRecordService")
public class AccountRecordServiceImpl extends BaseServiceImpl<AccountRecord, Long> implements IAccountRecordService{

    public static final Logger logger = LoggerFactory.getLogger(AccountRecordServiceImpl.class);

    @Resource
    private IAccountRecordMapper accountRecordMapper;

    @Resource
    private ClFlowInfoMapper clFlowInfoMapper;

    @Resource
    private ShardedJedisClient redisClient;

    @Override
    public BaseMapper<AccountRecord, Long> getMapper() {
        return accountRecordMapper;
    }

    @Override
    public Map<String, Object> totalAccRecords(Map<String, Object> paramsMap) {

        List<AccountRecord> accountRecords = accountRecordMapper.listAccRecords(paramsMap);
        if(accountRecords!=null && accountRecords.size()>0){
            for(AccountRecord accountRecord:accountRecords){
                ClFlowInfo clFlowInfo = clFlowInfoMapper.getPnameByPCode(accountRecord.getpCode());
                accountRecord.setpName(clFlowInfo.getPName());
                accountRecord.setPicUrl(clFlowInfo.getPicUrl());
            }
        }
        Map<String, Object> resultParamMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = accountRecordMapper.queryTotalAmtByTh(paramsMap);
        if(resultMap!=null){
            resultParamMap.put("totalAmt",resultMap.get("totalAmt"));
        }else{
            resultParamMap.put("totalAmt",0);
        }
        resultParamMap.put("accountRecords",accountRecords);
        return resultParamMap;
    }

    @Override
    public List<AccountRecord> listAccRecords(Map<String, Object> paramsMap) {
        return accountRecordMapper.listAccRecords(paramsMap);
    }

    @Override
    public AccountRecord queryRecordsDetail(Map<String,Object> paramsMap){
        return accountRecordMapper.queryRecordsDetail(paramsMap);
    }

    @Override
    public int updateRepayStatus(Map<String, Object> paramsMap) {
        return accountRecordMapper.updateRepayStatus(paramsMap);
    }

    @Override
    public int deleteStatus(Long id) {
        return accountRecordMapper.deleteStatus(id);
    }

    @Override
    public int batchUpdateRemRepDay(List<AccountRecord> accountRecords) {
        return accountRecordMapper.batchUpdateRemRepDay(accountRecords);
    }
}
