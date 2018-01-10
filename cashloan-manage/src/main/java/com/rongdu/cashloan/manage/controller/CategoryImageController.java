package com.rongdu.cashloan.manage.controller;

import com.rongdu.cashloan.cl.domain.CategoryImage;
import com.rongdu.cashloan.cl.domain.CompanyInformation;
import com.rongdu.cashloan.cl.service.CategoryImageService;
import com.rongdu.cashloan.cl.service.ICompanyInfomationService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("prototype")
@Controller
public class CategoryImageController  extends BaseController {

    @Resource
    private CategoryImageService categoryImageService;

    /**
     * 获取所有的分类图标
     */
    @RequestMapping("/act/model/categoryimage/getall.htm")
    public void getAllCtegoryimage(){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<CategoryImage> CategoryImages = categoryImageService.selectAll();
            result.put(Constant.RESPONSE_DATA, CategoryImages);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 获取所有的分类图标
     */
    @RequestMapping("/act/categoryimage/delete.htm")
    public void deleteCategoryImage(Long id){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            categoryImageService.deleteCategoryById(id);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "删除成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 上传图标
     */
    @RequestMapping("/act/categoryimage/upload.htm")
    public void uploadCategoryImage(MultipartFile image,String fileName){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            String realPath = request.getServletContext().getRealPath("/");
            String tempPath = categoryImageService.uploadCategoryById(realPath, image, fileName);

            result.put("imagePath", tempPath);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "上传成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }
    /**
     * 保存或更新
     */
    @RequestMapping("/act/category/saveorupdate.htm")
    public void saveOrUpdateCategoryImage(@RequestParam(value = "form") String data){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            categoryImageService.saveOrUpdate(data);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "保存或更新成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }
}
