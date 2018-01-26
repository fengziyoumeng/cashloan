package com.rongdu.cashloan.manage.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.rongdu.cashloan.cl.domain.CompanyProdDetail;
import com.rongdu.cashloan.cl.serviceNoSharding.ICompanyProductService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class CompanyProdDetailController extends ManageBaseController{
    @Resource
    private ICompanyProductService companyProductService;

    /**
     * 服务审核列表
     */
    @RequestMapping("/act/model/companyservice/auditList.htm")
    public void serviceAuditList(){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<CompanyProdDetail> listData = companyProductService.getCompanyproductAuditList();
            result.put(Constant.RESPONSE_DATA, listData);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 服务审核
     * @param data
     */
    @RequestMapping("/act/model/companyservice/auditresult.htm")
    public void serviceAudit(@RequestParam(value = "form") String data){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            companyProductService.serviceAudit(data);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "审核成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 服务列表
     * @param searchParams
     * @param current
     * @param pageSize
     */
    @RequestMapping("/act/model/companyservice/alllist.htm")
    public void serviceAllList(@RequestParam(value="searchParams",required=false) String searchParams,
                               @RequestParam(value="current") int current,
                               @RequestParam(value="pageSize") int pageSize){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<CompanyProdDetail> listData = companyProductService.getAllList(searchParams,current,pageSize);
            result.put(Constant.RESPONSE_DATA, listData);
//            result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(listData));
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "审核成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }


    @RequestMapping("/act/model/companyservice/editupdate.htm")
    public void serviceEdit(@RequestParam(value="form",required=false) String data){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            companyProductService.serviceUpdata(data);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "修改成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }
}
