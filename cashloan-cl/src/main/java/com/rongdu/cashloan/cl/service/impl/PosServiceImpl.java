package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.PosInfo;
import com.rongdu.cashloan.cl.mapper.IPosMapper;
import com.rongdu.cashloan.cl.service.IPosService;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("IPosService")
public class PosServiceImpl extends BaseServiceImpl<PosInfo, Long> implements IPosService{

    public static final Logger logger = LoggerFactory.getLogger(PosServiceImpl.class);

    @Resource
    private IPosMapper posMapper;


    @Override
    public List<PosInfo> listPosInfo() {
        return posMapper.listPosInfo();
    }

    @Override
    public BaseMapper<PosInfo, Long> getMapper() {
        return null;
    }
}
