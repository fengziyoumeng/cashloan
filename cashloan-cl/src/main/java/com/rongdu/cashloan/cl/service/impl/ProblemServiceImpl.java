package com.rongdu.cashloan.cl.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.rongdu.cashloan.cl.domain.ProblemGeneralDetail;
import com.rongdu.cashloan.cl.mapper.IProblemGeneralMapper;
import com.rongdu.cashloan.cl.domain.ProblemGeneral;
import com.rongdu.cashloan.cl.service.IProblemService;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Service("IProblemService")
public class ProblemServiceImpl implements IProblemService {

    public static final Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);

    @Resource
    private IProblemGeneralMapper problemGeneralMapper;

    @Resource
    private ShardedJedisClient redisClient;

    @Override
    public List<ProblemGeneral> getListProblemGeneral() {

        //缓存中有从缓存中取出数据
        List<ProblemGeneral> problemGeneralList = (List<ProblemGeneral>)redisClient.getObject("problemGeneral");
        if(problemGeneralList!=null && problemGeneralList.size()>0){
            logger.info("常见问题缓存数据：" + problemGeneralList.toString());
            return problemGeneralList;
        }

        //缓存中没有从数据库中取出
        List<ProblemGeneral> problemGenerals = problemGeneralMapper.getListProblemGeneral();
        if(problemGenerals!=null && problemGenerals.size()>0){
            for(ProblemGeneral problemGeneral:problemGenerals){
                List<ProblemGeneralDetail> problemGeneralDetails = problemGeneralMapper.getListProblemGeneralDetail(problemGeneral.getSort());
                problemGeneral.setProblemGeneralDetails(problemGeneralDetails);
            }
            logger.info("查询出所有常见问题：" + problemGenerals.toString());
        }
        redisClient.setObject("problemGeneral",problemGenerals);

        return problemGenerals;
    }
}
