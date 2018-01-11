package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.*;
import com.rongdu.cashloan.cl.mapper.*;
import com.rongdu.cashloan.cl.service.ICompanyProductService;
import com.rongdu.cashloan.core.common.util.OrderNoUtil;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.system.mapper.SysDictDetailMapper;
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

    public static final Logger logger = LoggerFactory.getLogger(CompanyProductServiceImpl.class);

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

    @Resource
    private BannerInfoMapper bannerInfoMapper;

    @Resource
    private AdInfoMapper adInfoMapper;

    @Resource
    CompanyInformationMapper companyInformationMapper;

    @Override
    public void saveOrUpdate(CompanyProdDetail companyProdDetail) throws Exception {
        long prod_id = Long.parseLong(OrderNoUtil.getSerialNumber());
        List<OperativeInfo> operativeInfos = companyProdDetail.getOperativeInfoList();
        if(companyProdDetail.getId()==null){
            companyProdDetail.setProc_id(prod_id);
            companyProdDetail.setCp_type(Integer.parseInt(String.valueOf(companyProdDetail.getType()).substring(0,2)));
            companyProdDetail.setStatus(1);
            companyProdDetail.setAudit_state(1);//资料审核中
            companyProdDetailMapper.insertSelective(companyProdDetail);
            if(operativeInfos!=null && operativeInfos.size()>0){
                for(OperativeInfo operativeInfo : operativeInfos){
                    operativeInfo.setProc_id(prod_id);
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
        List<CompanyProd> companyProds = (List<CompanyProd>)redisClient.getObject("cache_comProd_type_list");
        if(companyProds==null || companyProds.size()==0){
            logger.info("cache_comProd_type_list从缓存中取值--8个服务类型");
            companyProds = companyProdMapper.listCompanyProd(null);
            if(companyProds.size()>0){
                redisClient.setObject("cache_comProd_type_list",companyProds);
            }
        }
        resultMap.put("serviceType",companyProds);//8个服务类型

        /*------------------------------------------------查询更多------------------------------------------------------------------*/
        //缓存中有从缓存中取出数据
        List<Map<String,Object>> cacheComProdOrgTypeList = (List<Map<String,Object>>)redisClient.getObject("cache_ComProd_org_type_list");
        if(cacheComProdOrgTypeList==null || cacheComProdOrgTypeList.size()==0){
            logger.info("cache_ComProd_org_type_list从缓存中取值--更多（企业服务大类小类集合）");
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
                            cacheMap.put("type",String.valueOf(companyProd.getType()));
                            cacheMap.put("type_name",companyProd.getType_name());
                            cacheMap.put("detailType_list",resultList);
                            cacheComProdOrgTypeList.add(cacheMap);
                        }
                    }
                }
                if(cacheComProdOrgTypeList.size()>0){
                    redisClient.setObject("cache_ComProd_org_type_list",cacheComProdOrgTypeList);
                }
            }
        }
        resultMap.put("moreInterface",cacheComProdOrgTypeList);//更多（企业服务大类小类集合）

        /*------------------------------------------------查询推荐产品------------------------------------------------------------------*/
        CompanyProdDetail companyProdDetail = new CompanyProdDetail();
        companyProdDetail.setStatus(1);//上线
        companyProdDetail.setProc_flag(1);//推荐产品
        companyProdDetail.setAudit_state(2);//审核通过
        List<CompanyProdDetail> companyProdDetails = companyProdDetailMapper.listCompanyprodDetail(companyProdDetail);
        if(companyProdDetails!=null && companyProdDetails.size()>0){
            OperativeInfo operativeInfo = new OperativeInfo();
            for(CompanyProdDetail companyProdDetail1 : companyProdDetails){
                //获取企业相关信息
                CompanyInformation companyInformation = companyInformationMapper.findByPrimary(companyProdDetail1.getOrg_id());
                companyProdDetail1.setCompanyAddress(companyInformation.getCompanyAddress());
                companyProdDetail1.setIntroduction(companyInformation.getIntroduction());
                companyProdDetail1.setRegisteredCapital(companyInformation.getRegisteredCapital());

                //获取运营信息
                operativeInfo.setProc_id(companyProdDetail1.getProc_id());
                List<OperativeInfo> operativeInfos = operativeInfoMapper.listOperativeInfo(operativeInfo);
                companyProdDetail1.setOperativeInfoList(operativeInfos);
            }
        }
        resultMap.put("recommendProd",companyProdDetails);//推荐产品

        /*------------------------------------------------查询banner图------------------------------------------------------------------*/
        List<BannerInfo> bannerInfos = (List<BannerInfo>)redisClient.getObject("cache_b_banner_img_list");
        if(bannerInfos==null || bannerInfos.size()==0){
            logger.info("cache_b_banner_img_list从缓存中取值--金融圈子的banner图");
            BannerInfo bannerInfo = new BannerInfo();
            bannerInfo.setSite("0");//金融圈子的banner
            bannerInfo.setState("10");//上线
            bannerInfos = bannerInfoMapper.selectByBannerInfo(bannerInfo);
            if(bannerInfos.size()>0){
                redisClient.setObject("cache_b_banner_img_list",bannerInfos);
            }
        }
        resultMap.put("bannerPics",bannerInfos);//banner图

        /*------------------------------------------------查询广告图--------------------------------------------------------------------*/
        List<AdInfo> adInfos = (List<AdInfo>)redisClient.getObject("cache_b_adinfo_img_list");
        if(adInfos==null || adInfos.size()==0){
            logger.info("cache_b_adinfo_img_list从缓存中取值--金融圈子的ad图");
            AdInfo record = new AdInfo();
            adInfos = adInfoMapper.selectByAdInfo(record);
            if(adInfos.size()>0){
                redisClient.setObject("cache_b_adinfo_img_list",bannerInfos);
            }
        }
        resultMap.put("adPics",adInfos);//推荐产品

        return resultMap;
    }

    @Override
    public List<CompanyProdDetail> listCompanyprodDetail(CompanyProdDetail companyProdDetail) {
        companyProdDetail.setStatus(1);//上线
        companyProdDetail.setAudit_state(2);//审核通过
        List<CompanyProdDetail> companyProdDetails = companyProdDetailMapper.listCompanyprodDetail(companyProdDetail);
        if(companyProdDetails!=null && companyProdDetails.size()>0){
            OperativeInfo operativeInfo = new OperativeInfo();
            for(CompanyProdDetail comProdDetail : companyProdDetails){
                //获取企业相关信息
                CompanyInformation companyInformation = companyInformationMapper.findByPrimary(comProdDetail.getOrg_id());
                comProdDetail.setCompanyAddress(companyInformation.getCompanyAddress());
                comProdDetail.setIntroduction(companyInformation.getIntroduction());
                comProdDetail.setRegisteredCapital(companyInformation.getRegisteredCapital());

                //获取运营信息
                operativeInfo.setProc_id(comProdDetail.getProc_id());
                List<OperativeInfo> operativeInfos = operativeInfoMapper.listOperativeInfo(operativeInfo);
                comProdDetail.setOperativeInfoList(operativeInfos);
            }
        }
        return companyProdDetails;
    }
}
