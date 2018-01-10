package com.rongdu.cashloan.cl.service;

import com.rongdu.cashloan.cl.domain.Expressage;
import com.rongdu.cashloan.core.common.service.BaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ExpressageService  extends BaseService<Expressage, Long> {

    int deleteById(Long id) throws Exception;

    int saveOrUpdate(Expressage expressage);

    List<Expressage> getListByCreatTime(Date creatTime);

    Expressage getExpressageById(Long id);

    boolean isExistByTel(String tel);

    List<Map<String,Object>> listExpressages(Map<String,Object> map);
}
