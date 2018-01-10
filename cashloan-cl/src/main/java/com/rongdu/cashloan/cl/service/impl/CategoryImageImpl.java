package com.rongdu.cashloan.cl.service.impl;

import com.rongdu.cashloan.cl.domain.CategoryImage;
import com.rongdu.cashloan.cl.mapper.CategoryImageMapper;
import com.rongdu.cashloan.cl.mapper.CompanyInformationMapper;
import com.rongdu.cashloan.cl.service.CategoryImageService;
import com.rongdu.cashloan.cl.util.ImageUploadUtil;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CategoryImageImpl implements CategoryImageService {
    public static final Logger logger = LoggerFactory.getLogger(CategoryImageImpl.class);

    @Resource
    private CategoryImageMapper categoryImageMapper;


    @Override
    public List<CategoryImage> selectAll() {
        List<CategoryImage> categoryImages = null;
        try{
            categoryImages = categoryImageMapper.listSelective(null);
        }catch(Exception e){
            logger.info("查询分类图标失败",e);
        }
        return categoryImages;
    }

    @Override
    public void deleteCategoryById(Long id) {
        try{
            categoryImageMapper.deleteCategoryById(id);
        }catch(Exception e){
            logger.info("删除失败",e);
        }
    }

    @Override
    public void saveOrUpdate(String data) throws Exception{
        CategoryImage categoryImage = JsonUtil.parse(data, CategoryImage.class);
        Map<String,Object> map = JsonUtil.parse(data, Map.class);
        String iconUrl = (String)map.get("path");

        System.out.println(">>>>>>>>>"+categoryImage);
        try{
            String ossPath = ImageUploadUtil.uploadOSSDeleteTemp(iconUrl, "categoryImage");
            categoryImage.setIconUrl(ossPath);
            if(categoryImage.getId()==null){
                categoryImageMapper.save(categoryImage);
            }else{
                categoryImageMapper.update(categoryImage);
            }
        }catch (Exception e){
            logger.info("保存或更新失败",e);
            throw e;
        }
    }

    @Override
    public List<CategoryImage> getCategoryImageList(Integer typeValue) throws Exception {
        try{
            categoryImageMapper.getCategoryImageByTypeValue(typeValue);
        }catch(Exception e){
            logger.info("获取分类图片信息失败",e);
            throw e;
        }

        return null;
    }

    @Override
    public String uploadCategoryById(String realPath, MultipartFile image, String fileName) {
        String categoryImage = null;
        try{
            categoryImage = ImageUploadUtil.tempSave(realPath, "categoryImage", image, fileName);
        }catch (Exception e){
            logger.info("上传失败",e);
            throw e;
        }
        return categoryImage;
    }
}
