package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.cl.domain.CompanyProdDetail;
import com.rongdu.cashloan.cl.serviceNoSharding.ICompanyProductService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@CrossOrigin
@RequestMapping("/api/company")
public class CompanyProductController {

    public static final Logger logger = LoggerFactory.getLogger(CompanyProductController.class);

    @Resource
    private ICompanyProductService companyProductService;

    /**
     * 保存上传产品
     * @param response
     * @param companyProdDetail
     */
    @RequestMapping("/save/prod/detail")
    public void saveProDetail(HttpServletResponse response, CompanyProdDetail companyProdDetail){
        logger.info(String.format("保存公司产品>>前端传入的参数【%s】",companyProdDetail.toString()));
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Long prodId = companyProductService.saveOrUpdate(companyProdDetail);
            result.put(Constant.RESPONSE_DATA, prodId);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "保存失败");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 查询B圈数据
     * @param response
     */
    @RequestMapping("/query/prod/bdata")
    public void queryProdBdata(HttpServletResponse response){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Map<String, Object> resultMap = companyProductService.listHomeBdata();
            result.put(Constant.RESPONSE_DATA, resultMap);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询失败");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 根据分类类型查询公司产品信息列表
     * @param response
     * @param type 分类
     */
    @RequestMapping("/query/prod/details")
    public void queryProdDetails(HttpServletResponse response,String type){
        Map<String,Object> result = new HashMap<String,Object>();
        CompanyProdDetail companyProdDetail = new CompanyProdDetail();
        try {
            if(type.length()==2){
                companyProdDetail.setCp_type(Integer.parseInt(type));//分类
            }else if(type.length()==3){
                companyProdDetail.setType(Integer.parseInt(type));//子分类
            }
            List<CompanyProdDetail> companyProdDetails = companyProductService.listCompanyprodDetail(companyProdDetail);
            result.put(Constant.RESPONSE_DATA, companyProdDetails);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询失败");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 获取产品用户浏览数
     * @param response
     * @param userId
     * @param procId
     */
    @RequestMapping("/query/prod/click")
    public void queryProdClick(HttpServletResponse response,String userId,String procId){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Long prodClickNum = companyProductService.getProdClickNum(userId,procId);
            result.put(Constant.RESPONSE_DATA, prodClickNum);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询失败");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 获取服务的状态列表
     * @param response
     * @param userId
     */
    @RequestMapping("/all/product/auditstate.htm")
    public void auditStateList(HttpServletResponse response,Long userId,Integer auditState){
        Map<String,Object> result = new HashMap<>();
        try {
            List<CompanyProdDetail> companyProdDetailList = companyProductService.selectAllStateList(userId, auditState);
            result.put(Constant.RESPONSE_DATA, companyProdDetailList);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询失败");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 根据id获取产品详情
     * @param response
     * @param procId
     */
    @RequestMapping("/get/product/byid.htm")
    public void auditStateList(HttpServletResponse response,Long procId){
        Map<String,Object> result = new HashMap<>();
        try {
            CompanyProdDetail companyProdDetail = companyProductService.getDetailById(procId);
            result.put(Constant.RESPONSE_DATA, companyProdDetail);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询失败");
        }
        ServletUtils.writeToResponse(response,result);
    }
}
