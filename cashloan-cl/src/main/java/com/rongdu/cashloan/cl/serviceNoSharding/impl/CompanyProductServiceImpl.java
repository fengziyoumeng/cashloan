package com.rongdu.cashloan.cl.serviceNoSharding.impl;

import com.rongdu.cashloan.cl.domain.*;
import com.rongdu.cashloan.cl.mapper.*;
import com.rongdu.cashloan.cl.serviceNoSharding.ICompanyProductService;
import com.rongdu.cashloan.cl.serviceNoSharding.MessageService;
import com.rongdu.cashloan.cl.util.ImageUploadUtil;
import com.rongdu.cashloan.core.common.util.Base64;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.OrderNoUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.system.mapper.SysDictDetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("companyProductService")
public class CompanyProductServiceImpl implements ICompanyProductService{

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
    private CompanyInformationMapper companyInformationMapper;

    @Resource
    private MessageService messageService;

    @Override
    public Long saveOrUpdate(CompanyProdDetail companyProdDetail) throws Exception {
        long prod_id = Long.parseLong(OrderNoUtil.getSerialNumber());
        List<OperativeInfo> operativeInfos = companyProdDetail.getOperativeInfoList();
        if(companyProdDetail.getId()==null){
            //把传过来的图片路径用base64解密后上传到阿里云,返回的地址保存到对应字段中去
            String logo_path = ImageUploadUtil.uploadOSSDeleteTemp(Base64.decodeStr(companyProdDetail.getLogo_path()), "companyInfoPic");
            companyProdDetail.setLogo_path(logo_path);
            companyProdDetail.setProc_id(prod_id);
            companyProdDetail.setCp_type(Integer.parseInt(String.valueOf(companyProdDetail.getType()).substring(0,2)));
            companyProdDetail.setStatus(1);
            companyProdDetail.setAudit_state(1);//资料审核中
            companyProdDetail.setAudit_time(new Date());
            companyProdDetail.setCreate_time(new Date());
            companyProdDetail.setUpdate_time(new Date());
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
        return prod_id;
    }

    @Override
    public Map<String, Object> listHomeBdata() {

        Map<String, Object> resultMap = new HashMap<String,Object>();

        /*------------------------------------------------查询类型------------------------------------------------------------------*/
        List<CompanyProd> companyProds = (List<CompanyProd>)redisClient.getObject("cache_comProd_type_list");
        List<CompanyProd> companyProdServiceList = new ArrayList<CompanyProd>();//bigType=1的集合
        List<CompanyProd> companyProdServiceOrgList = new ArrayList<CompanyProd>();//bigType=2的集合
        if(companyProds==null || companyProds.size()==0){
            logger.info("cache_comProd_type_list从缓存中取值--8个服务类型");
            companyProds = companyProdMapper.listCompanyProd(null);
            if(companyProds.size()>0){
                redisClient.setObject("cache_comProd_type_list",companyProds);
            }
        }
        for(CompanyProd companyProd : companyProds){
            if(companyProd.getBig_type()==1){
                companyProdServiceList.add(companyProd);
            }else{
                companyProdServiceOrgList.add(companyProd);
            }
        }
        resultMap.put("serviceType",companyProdServiceList);//4个服务类型

        /*------------------------------------------------查询更多------------------------------------------------------------------*/
        //缓存中有从缓存中取出数据
        List<Map<String,Object>> cacheComProdOrgTypeList = (List<Map<String,Object>>)redisClient.getObject("cache_ComProd_org_type_list");
        if(cacheComProdOrgTypeList==null || cacheComProdOrgTypeList.size()==0){
            logger.info("cache_ComProd_org_type_list从缓存中取值--更多（企业服务大类小类集合）");
            cacheComProdOrgTypeList = new ArrayList<Map<String,Object>>();
            Map<String,Object> cacheMap = null;
            List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
            Map<String,Object> paraMap = null;
            if(companyProdServiceOrgList.size()>0){
                for(CompanyProd companyProd : companyProdServiceOrgList){
                    cacheMap = new HashMap<String,Object>();
                    paraMap = new HashMap<String,Object>();
                    paraMap.put("itemCode",companyProd.getType());
                    paraMap.put("parentId",26);
                    resultList = sysDictDetailMapper.queryItemValue(paraMap);
                    if(resultList.size()>0){
                        cacheMap.put("type",String.valueOf(companyProd.getType()));
                        cacheMap.put("type_name",companyProd.getType_name());
                        cacheMap.put("type_img_path",companyProd.getType_img_path());
                        cacheMap.put("detailType_list",resultList);
                        cacheComProdOrgTypeList.add(cacheMap);
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
                //取浏览量缓存数据，有就覆盖返回，没有就用数据库中查出来的
                String numString = redisClient.get(AppConstant.REDIS_KEY_CLICK_BDATA_PROD_INFO + companyProdDetail1.getProc_id());
                if(StringUtils.isNotBlank(numString)){
                    companyProdDetail1.setShow_click_num(Long.parseLong(numString));
                }

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
            record.setState(10);
            adInfos = adInfoMapper.selectByAdInfo(record);
            if(adInfos.size()>0){
                redisClient.setObject("cache_b_adinfo_img_list",adInfos);
            }
        }
        resultMap.put("adPics",adInfos);//广告图

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
                //取浏览量缓存数据，有就覆盖返回，没有就用数据库中查出来的
                String numString = redisClient.get(AppConstant.REDIS_KEY_CLICK_BDATA_PROD_INFO + comProdDetail.getProc_id());
                if(StringUtils.isNotBlank(numString)){
                    comProdDetail.setShow_click_num(Long.parseLong(numString));
                }

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

    @Override
    public Long getProdClickNum(String userId, String procId) {
        Long prodClickNum = redisClient.incr(AppConstant.REDIS_KEY_CLICK_BDATA_PROD_INFO + procId);
        return prodClickNum;
    }

    @Override
    public List<CompanyProdDetail> getCompanyproductAuditList() {
        List<CompanyProdDetail> companyProdDetailList = null;
        try{
            companyProdDetailList = companyProdDetailMapper.getAuditList();
        }catch (Exception e){
            logger.info("获取公司服务审核列表失败",e);
            throw e;
        }
        return companyProdDetailList;
    }

    @Override
    public CompanyProdDetail getDetailById(Long procId) {
        CompanyProdDetail companyProdDetail = null;
        try{
            companyProdDetail = companyProdDetailMapper.getProdDetailById(procId);
            if(companyProdDetail!=null && StringUtil.isNotBlank(companyProdDetail.getAudit_message())){
                String auditMsg = companyProdDetail.getAudit_message().substring(1,companyProdDetail.getAudit_message().length()-1);
                String[] split = auditMsg.split(",");
                List regectList = new ArrayList<Integer>();
                for (String s : split) {
                    regectList.add(Integer.parseInt(s.trim()));
                }
                companyProdDetail.setRegectMessage(regectList);
            }
        }catch (Exception e){
            logger.info("获取产品详情失败",e);
            throw e;
        }
        return companyProdDetail;
    }

    @Override
    public List getAllList(String searchParams, int current, int pageSize) {
        List<CompanyProdDetail> allListBySearch = null;
        try{
            Map params = JsonUtil.parse(searchParams, Map.class);
//            PageHelper.startPage(current,pageSize);
            allListBySearch = companyProdDetailMapper.getAllListBySearch(params);
        }catch (Exception e){
            logger.info("查询失败",e);
            throw e;
        }
        return allListBySearch;
    }

    @Override
    public List<CompanyProdDetail> selectAllStateList(Long userId, Integer auditState) {
        List<CompanyProdDetail> companyProdDetails = null;
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId",userId);
        params.put("auditState",auditState);
        try{
            /*Object object = redisClient.getObject(AppConstant.REDIS_KEY_AUDIT_STATE_LIST + userId + ":" + auditState);
            if(object != null){
                List<CompanyProdDetail> prodDetails = (List<CompanyProdDetail>)object;
                return prodDetails;
            }*/
            companyProdDetails = companyProdDetailMapper.getAuditStateList(params);
            if(companyProdDetails!=null){
                for ( CompanyProdDetail list: companyProdDetails) {
                    if(StringUtil.isNotBlank(list.getAudit_message())){
                        String auditMsg = list.getAudit_message().substring(1,list.getAudit_message().length()-1);
                        String[] split = auditMsg.split(",");
                        List regectList = new ArrayList<Integer>();
                        for (String s : split) {
                            regectList.add(Integer.parseInt(s.trim()));
                        }
                        list.setRegectMessage(regectList);
                    }
                }
            }
//            redisClient.setObject(AppConstant.REDIS_KEY_AUDIT_STATE_LIST+userId+":"+auditState,companyProdDetails);
        }catch (Exception e){
            logger.info("获取服务审核状态列表失败",e);
            throw e;
        }
        return companyProdDetails;
    }

    @Override
    public void serviceAudit(String data) {
        try{
            CompanyProdDetail companyProdDetail = JsonUtil.parse(data, CompanyProdDetail.class);
            Map map = JsonUtil.parse(data, Map.class);
            String auditState = map.get("pass") != null ? map.get("pass").toString() :"";
            String auditMessage = map.get("auditMessage") != null ? map.get("auditMessage").toString() :"";
            String auditPerson = map.get("auditPerson") != null ? map.get("auditPerson").toString() :"";

            Long id = companyProdDetail.getId();
            Map<String,Long> dataMap = companyProdDetailMapper.getUserIdByProcId(id);
            if(auditState.equals("no")){
                companyProdDetail.setAudit_message(auditMessage);
                companyProdDetail.setAudit_state(3);
                messageService.sendMessage("SMS_TEMPLATE","SERVICE_TITLE_REJ","SERVICE_MSG_REJ",dataMap.get("userId"),2,dataMap.get("procId"));
            }else if(auditState.equals("ok")){
                companyProdDetail.setAudit_state(2);
                messageService.sendMessage("SMS_TEMPLATE","SERVICE_TITLE_PASS","SERVICE_MSG_PASS",dataMap.get("userId"),2,dataMap.get("procId"));
            }

            companyProdDetail.setAudit_person(auditPerson);
            companyProdDetail.setAudit_time(new Date());

            companyProdDetailMapper.serviceAudit(companyProdDetail);
        }catch (Exception e){
            logger.info("审核失败",e);
            throw e;
        }
    }

    @Override
    public void serviceUpdata(String data) {
        try{
            CompanyProdDetail companyProdDetail = JsonUtil.parse(data, CompanyProdDetail.class);
            companyProdDetailMapper.updateByPrimaryKeySelective(companyProdDetail);

        }catch (Exception e){
            logger.info("产品修改失败",e);
            throw e;
        }
    }

    @Override
    public void updateProdClickNum() {
        CompanyProdDetail companyProdDetail = new CompanyProdDetail();
        companyProdDetail.setStatus(1);//上线
        companyProdDetail.setAudit_state(2);//审核通过
        List<CompanyProdDetail> companyProdDetails = companyProdDetailMapper.listCompanyprodDetail(companyProdDetail);
        if(companyProdDetails!=null && companyProdDetails.size()>0){
            for(CompanyProdDetail comProdDetail : companyProdDetails){
                String numString = redisClient.get(AppConstant.REDIS_KEY_CLICK_BDATA_PROD_INFO + comProdDetail.getProc_id());
                if(StringUtils.isNotBlank(numString)){
                    comProdDetail.setShow_click_num(Long.parseLong(numString));
                    companyProdDetailMapper.updateByPrimaryKeySelective(comProdDetail);
                }
            }
        }
    }
}
