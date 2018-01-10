package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.BankInfo;
import com.rongdu.cashloan.cl.mapper.BankInfoMapper;
import com.rongdu.cashloan.cl.service.IBankInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("bankInfoService")
public class BankInfoServiceImpl implements IBankInfoService{

    public static final Logger logger = LoggerFactory.getLogger(ClFlowInfoServiceImpl.class);
    @Resource
    private BankInfoMapper bankInfoMapper;

    @Override
    public int deleteById(long id) {
        int ret = 0;
        try{
            ret = bankInfoMapper.deleteById(id);
        }catch (Exception e){
            logger.info("=========>银行信息删除失败"+e);
        }
        return ret;
    }

    @Override
    public List<BankInfo> getListByType(long showType) {
        List<BankInfo> bankInfoList= null;
        try{
            bankInfoList = bankInfoMapper.getListByType(showType);

        }catch (Exception e){
            logger.info("=========>根据showType获取银行列表信息失败"+e);
        }
        return bankInfoList;
    }

    @Override
    public List<BankInfo> getAll() {
        List<BankInfo> bankInfoList= null;
        try{
            return bankInfoMapper.getAll();
        }catch (Exception e){
            logger.info("=========>获取所有银行列表信息失败"+e);
        }
        return bankInfoList;
    }
}
