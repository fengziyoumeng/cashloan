package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.CompanyInformation;
import com.rongdu.cashloan.cl.mapper.CompanyInformationMapper;
import com.rongdu.cashloan.cl.service.ICompanyInfomationService;
import com.rongdu.cashloan.cl.util.ImageUploadUtil;
import com.rongdu.cashloan.core.common.util.Base64;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyInfomationImpl implements ICompanyInfomationService {
    public static final Logger logger = LoggerFactory.getLogger(CompanyInfomationImpl.class);
    @Resource
    private CompanyInformationMapper CompanyInfomationMapper;

    @Override
    public void saveOrUpdate(CompanyInformation companyInformation) throws Exception {
            try {
                CompanyInformation companyInfo = new CompanyInformation();
                companyInfo.setUserId(companyInformation.getUserId());
                companyInfo.setCompanyName(companyInformation.getCompanyName());
//                companyInfo.setCompanyAddress(companyInformation.getCompanyAddress());
                companyInfo.setLegalPersonName(companyInformation.getLegalPersonName());
                companyInfo.setIDNumber(companyInformation.getIDNumber());
                companyInfo.setContactPerson(companyInformation.getContactPerson());
                companyInfo.setContactTel(companyInformation.getContactTel());
                companyInfo.setIntroduction(companyInformation.getIntroduction());
                companyInfo.setUpdateTime(new Date());
                companyInfo.setAuditState(1);
                companyInfo.setState(20);
                //把传过来的图片路径用base64解密后上传到阿里云,返回的地址保存到对应字段中去
                String licensePicUrl = ImageUploadUtil.uploadOSSDeleteTemp(Base64.decodeStr(companyInformation.getLicensePic()), "companyInfoPic");
                String identityFrontPicUrl = ImageUploadUtil.uploadOSSDeleteTemp(Base64.decodeStr(companyInformation.getIdentityFrontPic()), "companyInfoPic");
                String identityReversePicUrl = ImageUploadUtil.uploadOSSDeleteTemp(Base64.decodeStr(companyInformation.getIdentityReversePic()), "companyInfoPic");
                String holdCardPicUrl = ImageUploadUtil.uploadOSSDeleteTemp(Base64.decodeStr(companyInformation.getHoldCardPic()), "companyInfoPic");

                companyInfo.setLicensePic(licensePicUrl);
                companyInfo.setIdentityFrontPic(identityFrontPicUrl);
                companyInfo.setIdentityReversePic(identityReversePicUrl);
                companyInfo.setHoldCardPic(holdCardPicUrl);

                if (companyInformation.getId() == null) {
                    companyInfo.setCreateTime(new Date());
                    CompanyInfomationMapper.save(companyInfo);
                } else {
                    companyInfo.setId(companyInformation.getId());
                    CompanyInfomationMapper.update(companyInfo);
                }
            }catch (Exception e){
                logger.info("保存或更新失败",e);
                throw e;
            }
    }

    @Override
    public String uploadCompanyImage(String realPath,MultipartFile image,String fileName) {
        String tempPath = "";

        try{
            String _tempPath = ImageUploadUtil.tempSave(realPath, "CompanyInfo", image,fileName);
            //使用base64加密是因为.png这种字符串在app中取出会有问题
            tempPath = Base64.encodeStr(_tempPath);
        }catch (Exception e){
            logger.info("上传企业资质图片失败"+e);
            throw e;
        }
        return tempPath;
    }

    @Override
    public List<CompanyInformation> auditList() {
        Map<String, Object> param = new HashMap<>();
        List<CompanyInformation> companyInformations = null;
        try {
            param.put("auditState", 1);
            companyInformations = CompanyInfomationMapper.auditList(param);

        }catch(Exception e){
            logger.info("获取审核列表失败",e);
            throw e;
        }
        return companyInformations;
    }

    @Override
    public void infoAudit(String data) {
        try {
            HashMap<String, Object> dataMap = JsonUtil.parse(data, HashMap.class);

            String id = dataMap.get("id") != null ? dataMap.get("id").toString() : "";
            String auditMessage = dataMap.get("auditMessage") != null ? dataMap.get("auditMessage").toString() : "";
            String pass = dataMap.get("pass") != null ? dataMap.get("pass").toString() : "";
            String auditPerson = dataMap.get("auditPerson") != null ? dataMap.get("auditPerson").toString() : "";

            CompanyInformation companyInfo = new CompanyInformation();

            companyInfo.setAuditTime(new Date());
            companyInfo.setId(Long.parseLong(id));
            companyInfo.setAuditMessage(auditMessage);
            companyInfo.setAuditPerson(auditPerson);

            if (pass.equals("ok")) {
                companyInfo.setState(10);
                companyInfo.setAuditState(2);
            } else if (pass.equals("no")) {
                companyInfo.setState(20);
                companyInfo.setAuditState(3);
            }
            CompanyInfomationMapper.updateAudit(companyInfo);
        }catch(Exception e){
            logger.info("企业资质审核失败",e);
            throw e;
        }

    }

    @Override
    public CompanyInformation selectAuditState(Long userId) {
        List<CompanyInformation> companyInfo = null;
        try{
            companyInfo = CompanyInfomationMapper.selectAuditStateByUserId(userId);
            if(companyInfo.size()>1){
                return companyInfo.get(companyInfo.size()-1);
            }else if(companyInfo.size()==0){
                CompanyInformation companyInformation = new CompanyInformation();
                companyInformation.setAuditState(0);
                return companyInformation;
            }else if(companyInfo.size()==1){
                return companyInfo.get(0);
            }
        }catch (Exception e){
            logger.info("获取审核状态失败",e);
            throw e;
        }
        return null;
    }

}
