package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.AdInfo;
import com.rongdu.cashloan.cl.mapper.AdInfoMapper;
import com.rongdu.cashloan.cl.service.AdInfoService;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

public class AdInfoServiceImpl implements AdInfoService{

//    public static final Logger logger = LoggerFactory.getLogger(AdInfoServiceImpl.class);

    @Resource
    private AdInfoMapper adInfoMapper;

    @Override
    public List<AdInfo> selectAll() {
        List<AdInfo> adInfos = null;
        try{
            adInfos = adInfoMapper.selectAll();

        }catch (Exception e){

        }
        return adInfos;
    }
}
