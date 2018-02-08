package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.CompanyProd;
import com.rongdu.cashloan.cl.mapper.CompanyProdMapper;
import com.rongdu.cashloan.cl.service.CompanyProdService;
import com.rongdu.cashloan.cl.util.ImageUploadUtil;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CompanyProdServiceImpl implements CompanyProdService {
    public static final Logger logger = LoggerFactory.getLogger(CompanyProdServiceImpl.class);

    @Resource
    private CompanyProdMapper companyProdMapper;


    @Override
    public List<CompanyProd> selectAll() {
        List<CompanyProd> companyProds = null;
        try{
            companyProds = companyProdMapper.listSelective();
        }catch(Exception e){
            logger.info("查询分类图标失败",e);
        }
        return companyProds;
    }

    @Override
    public void deleteCategoryById(Long id,String imgPath) {
        try{
            companyProdMapper.deleteByPrimaryKey(id);
            ImageUploadUtil.deleteImageOnOSS("categoryImage",imgPath);
        }catch(Exception e){
            logger.info("删除失败",e);
        }
    }

    @Override
    public void saveOrUpdate(String data) throws Exception{
        CompanyProd companyProd = JsonUtil.parse(data, CompanyProd.class);
        Map<String,Object> map = JsonUtil.parse(data, Map.class);
        String iconUrl = (String)map.get("path");

        try{
            String oldPath = companyProd.getType_img_path();
            String ossPath = ImageUploadUtil.uploadOSSDeleteTemp(iconUrl, "categoryImage",oldPath);
            companyProd.setType_img_path(ossPath);

            if(companyProd.getId()==null){
                companyProdMapper.insert(companyProd);
            }else{
                companyProdMapper.updateByPrimaryKeySelective(companyProd);
            }
        }catch (Exception e){
            logger.info("保存或更新失败",e);
            throw e;
        }
    }

    @Override
    public String uploadCategoryById(String realPath, MultipartFile image, String fileName) {
        String companyProd = null;
        try{
            companyProd = ImageUploadUtil.tempSave(realPath, "categoryImage", image, fileName);
        }catch (Exception e){
            logger.info("上传失败",e);
            throw e;
        }
        return companyProd;
    }
}
