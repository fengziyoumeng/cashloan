package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.Expressage;
import com.rongdu.cashloan.cl.mapper.ExpressageMapper;
import com.rongdu.cashloan.cl.service.ExpressageService;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("ExpressageService")
public class ExpressageServiceImpl extends BaseServiceImpl<Expressage, Long> implements ExpressageService{

    @Resource
    private ExpressageMapper expressageMapper;

    @Override
    public int deleteById(Long id) throws Exception{
        return expressageMapper.deleteById(id);
    }

    public int saveOrUpdate(Expressage expressage){
        expressage.setCreatTime(new Date());
        int ret =0;
        if(expressage.getId()==null){
            ret = expressageMapper.save(expressage);
        }else{
            ret = expressageMapper.update(expressage);
        }
        return ret;
    }


    public List<Expressage> getListByCreatTime(Date creatTime){
        HashMap<String, Object> result = new HashMap<>();
        result.put("creatTime",creatTime);
        return expressageMapper.listSelective(result);
    }

    @Override
    public Expressage getExpressageById(Long id) {
        return expressageMapper.findByPrimary(id);
    }

    @Override
    public List<Map<String, Object>> listExpressages(Map<String, Object> map) {
        return expressageMapper.listExpressages(map);
    }


    @Override
    public BaseMapper<Expressage, Long> getMapper() {
        return null;
    }

    @Override
    public boolean isExistByTel(String tel){
        int count = expressageMapper.isExistByTel(tel);
        if(count>0){
            return true;
        }
        return false;
    }

}
