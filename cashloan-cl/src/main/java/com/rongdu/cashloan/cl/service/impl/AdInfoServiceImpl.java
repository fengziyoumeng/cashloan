package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.AdInfo;
import com.rongdu.cashloan.cl.mapper.AdInfoMapper;
import com.rongdu.cashloan.cl.service.AdInfoService;
import com.rongdu.cashloan.cl.util.ImageUploadUtil;
import com.rongdu.cashloan.core.aliyun.AliYunUtil;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import org.activiti.explorer.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class AdInfoServiceImpl implements AdInfoService{

    public static final Logger logger = LoggerFactory.getLogger(AdInfoServiceImpl.class);

    @Resource
    private AdInfoMapper adInfoMapper;

    @Override
    public List<AdInfo> selectAll() {
        List<AdInfo> adInfos = null;
        try{
            adInfos = adInfoMapper.selectAll();

        }catch (Exception e){
            logger.info("查询失败",e);
            throw e;
        }
        return adInfos;
    }

    @Override
    public void deleteAdInfoByid(Long id,String adUrl) throws Exception{
        try{
            adInfoMapper.deleteByPrimaryKey(id);
            //删除在阿里云上的图片
            ImageUploadUtil.deleteImageOnOSS("adInfoImage",adUrl);
        }catch (Exception e){
            logger.info("删除失败",e);
            throw e;
        }
    }

    @Override
    public void saveOrUpdate(String data) throws Exception{
        AdInfo adInfo = JsonUtil.parse(data, AdInfo.class);
        Map map = JsonUtil.parse(data, Map.class);
        String adUrl = (String)map.get("path");
        try{
            String oldImgPath = adInfo.getAdUrl();
            //传入oldImgPath是为了更新的时候用原来的key做新图片的key直接覆盖原文件，就不用删除老文件
            String ossImage = ImageUploadUtil.uploadOSSDeleteTemp(adUrl, "adInfoImage",oldImgPath);

            if(StringUtil.isNotBlank(ossImage)){
                adInfo.setAdUrl(ossImage);
            }
            if(adInfo.getId()==null){
                adInfoMapper.insert(adInfo);
            }else {
                adInfoMapper.updateByPrimaryKey(adInfo);
            }
        }catch (Exception e){
            logger.info("保存或更新失败",e);
            throw e;
        }
    }
}
