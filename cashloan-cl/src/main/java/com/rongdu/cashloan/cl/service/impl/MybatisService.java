package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.vo.ChannelForH5;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lsk on 2017/2/24.
 */
@Service
@SuppressWarnings({ "rawtypes" })
public class MybatisService {
    @Resource
    private SqlSessionTemplate sessionTemplate;

    public List<Map> querySql(String statment, Object parameter) {
        return sessionTemplate.selectList(statment, parameter);
    }

    public List<ChannelForH5> channelForH5(String statment, Object parameter) {
        return sessionTemplate.selectList(statment, parameter);
    }

    public Map queryRec(String statment, Object parameter) {
        return sessionTemplate.selectOne(statment, parameter);
    }

    public int insertSQL(String statment, Object parameter) {
        return sessionTemplate.insert(statment,parameter);
    }

    public int updateSQL(String statment, Object parameter){
        return sessionTemplate.update(statment,parameter);
    }


}
