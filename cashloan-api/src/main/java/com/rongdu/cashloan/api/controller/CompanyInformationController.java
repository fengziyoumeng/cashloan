package com.rongdu.cashloan.api.controller;


import com.rongdu.cashloan.cl.domain.*;
import com.rongdu.cashloan.cl.service.ICompanyInfomationService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 借贷产品数据
 * @author caitt
 * @version 1.0
 * @date 2017年11月09日下午21:10:14
 * Copyright 杭州民华金融信息服务有限公司 现金贷  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Scope("prototype")
@Controller
public class CompanyInformationController extends BaseController {

    @Resource
    private ICompanyInfomationService companyInfomationService;


    /**
     * 企业资质,入驻信息保存
     * @param response
     * @param companyInformation
     */
    @RequestMapping("/api/company/enter/Recommend.htm")
    public void CompanySaveOrUpdate( HttpServletResponse response,CompanyInformation companyInformation ){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            companyInfomationService.saveOrUpdate(companyInformation);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "保存成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 图片上传
     * @param image
     * @param fileName 为了得到他的拓展名
     * @return
     */
    @RequestMapping("/upload/image/uploadlocal.htm")
    public void uploadImgTotemp(MultipartFile image,String fileName){
        Map<String,Object> result = new HashMap<>();
        try {
            String realPath = request.getServletContext().getRealPath("/");
            String tempPath = companyInfomationService.uploadCompanyImage(realPath,image,fileName);
            result.put(Constant.RESPONSE_DATA, tempPath);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "操作成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }


    /**
     * 获取入驻状态的信息
     * @param response
     * @param
     */
    @RequestMapping("/company/auditstate.htm")
    public void CompanyAuditState(HttpServletResponse response,Long userId){
        Map<String,Object> result = new HashMap<>();
        try {
            CompanyInformation auditState = companyInfomationService.selectAuditState(userId);
            result.put(Constant.RESPONSE_DATA, auditState);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "操作成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }
}
