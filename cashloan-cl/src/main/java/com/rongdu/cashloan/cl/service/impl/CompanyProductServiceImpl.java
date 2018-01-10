package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.CompanyProd;
import com.rongdu.cashloan.cl.domain.CompanyProdDetail;
import com.rongdu.cashloan.cl.domain.OperativeInfo;
import com.rongdu.cashloan.cl.mapper.CompanyProdDetailMapper;
import com.rongdu.cashloan.cl.mapper.CompanyProdMapper;
import com.rongdu.cashloan.cl.mapper.OperativeInfoMapper;
import com.rongdu.cashloan.cl.service.ICompanyProductService;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.system.mapper.SysDictDetailMapper;
import groovy.util.ObservableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyProductServiceImpl implements ICompanyProductService {

    public static final Logger logger = LoggerFactory.getLogger(CompanyInfomationImpl.class);

    @Resource
    private CompanyProdMapper companyProdMapper;

    @Resource
    private CompanyProdDetailMapper companyProdDetailMapper;

    @Resource
    private SysDictDetailMapper sysDictDetailMapper;

    @Resource
    private ShardedJedisClient redisClient;

    @Resource
    private OperativeInfoMapper operativeInfoMapper;

    @Override
    public void saveOrUpdate(CompanyProdDetail companyProdDetail) throws Exception {
        List<OperativeInfo> operativeInfos = companyProdDetail.getOperativeInfos();
        if(companyProdDetail.getId()!=null){
            companyProdDetail.setStatus(1);
            companyProdDetailMapper.insertSelective(companyProdDetail);
            if(operativeInfos!=null && operativeInfos.size()>0){
                for(OperativeInfo operativeInfo : operativeInfos){
                    operativeInfoMapper.insertSelective(operativeInfo);
                }
            }
        }else{
            companyProdDetailMapper.updateByPrimaryKeySelective(companyProdDetail);
            if(operativeInfos!=null && operativeInfos.size()>0){
                for(OperativeInfo operativeInfo : operativeInfos){
                    operativeInfoMapper.updateByPrimaryKeySelective(operativeInfo);
                }
            }
        }
    }

    @Override
    public Map<String, Object> listHomeBdata() {

        Map<String, Object> resultMap = new HashMap<String,Object>();

        /*------------------------------------------------查询类型------------------------------------------------------------------*/
        List<CompanyProd> companyProds = (List<CompanyProd>)redisClient.getObject("cache_ComProd_list");
        if(companyProds==null || companyProds.size()==0){
            companyProds = companyProdMapper.listCompanyProd(null);
            if(companyProds.size()>0){
                redisClient.setObject("cache_ComProd_list",companyProds);
            }
        }
        resultMap.put("serviceType",companyProds);//服务类型

        /*------------------------------------------------查询更多------------------------------------------------------------------*/
        //缓存中有从缓存中取出数据
        List<Map<String,Object>> cacheComProdOrgTypeList = (List<Map<String,Object>>)redisClient.getObject("cache_ComProd_org_type_list");
        if(cacheComProdOrgTypeList==null || cacheComProdOrgTypeList.size()==0){
            cacheComProdOrgTypeList = new ArrayList<Map<String,Object>>();
            Map<String,Object> cacheMap = null;
            List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
            Map<String,Object> paraMap = null;
            if(companyProds.size()>0){
                for(CompanyProd companyProd : companyProds){
                    cacheMap = new HashMap<String,Object>();
                    if(companyProd.getBig_type()==2){
                        paraMap = new HashMap<String,Object>();
                        paraMap.put("itemCode",companyProd.getType());
                        paraMap.put("parentId",26);
                        resultList = sysDictDetailMapper.queryItemValue(paraMap);
                        if(resultList.size()>0){
                            cacheMap.put(String.valueOf(companyProd.getType()),companyProd.getType_name());
                            cacheMap.put("detailType",resultList);
                            cacheComProdOrgTypeList.add(cacheMap);
                        }
                    }
                }
                if(cacheComProdOrgTypeList.size()>0){
                    redisClient.setObject("cache_ComProd_org_type_list",cacheComProdOrgTypeList);
                }
            }
        }
        resultMap.put("moreInterface",cacheComProdOrgTypeList);//更多

        /*------------------------------------------------查询推荐产品------------------------------------------------------------------*/
        CompanyProdDetail companyProdDetail = new CompanyProdDetail();
        companyProdDetail.setStatus(1);//上线
        companyProdDetail.setProc_flag(1);//推荐产品
        List<CompanyProdDetail> companyProdDetails = companyProdDetailMapper.listCompanyprodDetail(companyProdDetail);
        resultMap.put("recommendProd",companyProdDetails);//推荐产品

        /*------------------------------------------------查询banner图------------------------------------------------------------------*/
        /*------------------------------------------------查询广告图--------------------------------------------------------------------*/

        return resultMap;
    }
}
