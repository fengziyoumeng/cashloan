package com.rongdu.cashloan.manage.controller;

import com.rongdu.cashloan.cl.domain.CompanyInformation;
import com.rongdu.cashloan.cl.serviceNoSharding.ICompanyInfomationService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("prototype")
@Controller
public class CompanyInfoController {

    @Resource
    private ICompanyInfomationService companyInfomationService;

    /**
     * 入驻企业待审核列表
     * @param response
     */
    @RequestMapping("/act/model/company/auditList.htm")
    public void CompanySaveOrUpdate(HttpServletResponse response, @RequestParam(value="searchParams",required = false)String searchParams ){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<CompanyInformation> companyInformations = companyInfomationService.auditList(searchParams);
            result.put(Constant.RESPONSE_DATA, companyInformations);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 企业入驻审核
     * @param response
     * @param
     */
    @RequestMapping("/act/model/company/auditresult.htm")
    public void CompanyInfoAudit(HttpServletResponse response, @RequestParam(value = "form") String data ){
        Map<String,Object> result = new HashMap<>();
        try {
            companyInfomationService.infoAudit(data);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "操作成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

}
