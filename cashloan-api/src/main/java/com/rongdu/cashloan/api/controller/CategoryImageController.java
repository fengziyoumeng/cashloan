package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.cl.domain.BankInfo;
import com.rongdu.cashloan.cl.domain.CategoryImage;
import com.rongdu.cashloan.cl.service.CategoryImageService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Scope("prototype")
@Controller
public class CategoryImageController extends BaseController{
    @Resource
    private CategoryImageService categoryImageService;

    /**
     * 金融圈子分类图片信息
     */
    @RequestMapping("/api/category/image/infinancial.htm")
    public void getCategoryInInfinancial(){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<CategoryImage> categoryImageList = categoryImageService.getCategoryImageList(2);
            result.put(Constant.RESPONSE_DATA, categoryImageList);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 首页分类图片信息
     */
    @RequestMapping("/api/category/image/index.htm")
    public void getCategoryInHome(){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<CategoryImage> categoryImageList = categoryImageService.getCategoryImageList(1);
            result.put(Constant.RESPONSE_DATA, categoryImageList);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }
}
