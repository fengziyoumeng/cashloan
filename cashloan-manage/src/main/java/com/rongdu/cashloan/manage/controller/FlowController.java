
package com.rongdu.cashloan.manage.controller;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.domain.ClFlowUV;
import com.rongdu.cashloan.cl.service.ClFlowInfoService;
import com.rongdu.cashloan.cl.service.ClFlowUVService;
import com.rongdu.cashloan.cl.util.StateUtil;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.util.*;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.system.domain.SysDictDetail;
import com.rongdu.cashloan.system.permission.annotation.RequiresPermission;
import com.rongdu.cashloan.system.serviceNoSharding.SysDictDetailService;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 第三方注册记录，流量管理 Controller
 *
 * @author wulang
 * @version 1.0.0
 * @date 2017-10-24 15:54:41
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * <p>
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Scope("prototype")
@Controller
public class FlowController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(FlowController.class);

    @Resource
    private ClFlowInfoService clFlowInfoService;

    @Resource
    private SysDictDetailService sysDictDetailService;

    @Resource
    private ClFlowUVService clFlowUVService;


//    /**
//     * 获取其他公司产品的信息
//     * jsonp解决跨域问题
//     *
//     * @throws Exception
//     */
//    @RequestMapping(value = "/act/flowControl/getOtherInfo.htm")
//    public void getInfo(HttpServletRequest request, @RequestParam(value = "type", required = false) String type,
//                        @RequestParam(value = "userId", required = false) Long userId) throws Exception {
//
//        int leftHours = 0;
//        int day = 0;
//        if (userId != null) {
//            //1.6 查询用户最后一笔放款是否是在24小时之内，需要限制
//            BorrowProgress borrowProgress = borrowProgressMapper.findLastLoanSuccessProgressByUserId(userId);
//            if (borrowProgress != null) {
//                //有过放款记录
//                Date loanSuccessCreateTime = borrowProgress.getCreateTime();
//                DateTime dt = new DateTime(loanSuccessCreateTime.getTime()).plusHours(24);//24小时之后的时间点
//                if (DateTime.now().getMillis() < dt.getMillis()) {
//                    //当前时间还未达到下次放款许可时间
//                    leftHours = Math.abs(Hours.hoursBetween(DateTime.now(), dt).getHours());
//                }
//            }
//
//            //1.7 借款天数限制，wl:新需求提出当拒绝选项为“放弃本次申请”、“二次拨打未联系上”时不受7天后才能借款限制
//
//            String againBorrow = Global.getValue("again_borrow");
//            Map<String, Object> searchMap = new HashMap<>();
//            searchMap.put("userId", userId);
//            Borrow borrowTemp = clBorrowMapper.findLast(searchMap);
//            if (StringUtil.isNotBlank(borrowTemp) && !"02".equals(borrowTemp.getRejectReason()) && !"03".equals(borrowTemp.getRejectReason())) {
//                day = DateUtil.daysBetween(borrowTemp.getCreateTime(), new Date());
//                day = Integer.parseInt(againBorrow) - day;
//            }
//        }
//        if ("1".equals(type)) {
//            //更多 获取开启状态的数据
//            List<Map> list = flowControlService.querySql("usr.queryFlowInfo", MapUtil.array2Map(new Object[][]{{"openFlag", "0"}}));
//            //将json串拼接
//            String jsonData = JSON.toJSONString(list);
//            JSONObject jsonOne = new JSONObject();
//            jsonOne.put("jsonData", jsonData);
//            jsonOne.put("day", day);
//            jsonOne.put("leftHours", leftHours);
//            String callback = (String) request.getParameter("callback");
//            String retStr = callback + "(" + jsonData + ")";
//            response.getWriter().print(retStr);
//        } else {
//            //获取开启状态的数据,热门贷款和极速放款,orderNo不为空则进行排序展示
//            List<Map> listHot = flowControlService.querySql("usr.queryFlowInfo", MapUtil.array2Map(new Object[][]{{"openFlag", "0"}, {"loanType", "0"}, {"orderNo", "orderNo"}}));
//            List<Map> listQucik = flowControlService.querySql("usr.queryFlowInfo", MapUtil.array2Map(new Object[][]{{"openFlag", "0"}, {"loanType", "1"}, {"orderNo", "orderNo"}}));
//            //将json串拼接
//            String jsonData = JSON.toJSONString(listHot);
//            String jsonData1 = JSON.toJSONString(listQucik);
//            JSONObject jsonOne = new JSONObject();
//            jsonOne.put("hot", jsonData);
//            jsonOne.put("quick", jsonData1);
//            jsonOne.put("day", day);
//            jsonOne.put("leftHours", leftHours);
//            String callback = (String) request.getParameter("callback");
//            String retStr = callback + "(" + jsonOne + ")";
//            response.getWriter().print(retStr);
//        }
//        //加上返回参数
//    }


    /**
     * 获取流量平台列表信息
     *
     * @throws Exception
     */
    @RequestMapping(value = "/act/flowControl/getInfoManage.htm")
    public void  getInfoManage(@RequestParam(value="searchParams",required=false) String searchParams) throws Exception {
        Map params = JsonUtil.parse(searchParams, Map.class);
        List<ClFlowInfo> list = clFlowInfoService.getAllProdctList(params);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, list);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response,result);
    }


    /**
     * 获取流向平台UV点击数
     *
     * @throws Exception
     */
    @RequestMapping(value = "/act/flowControl/getAllForUV.htm")
    public void  getAllForUV(@RequestParam(value="searchParams",required=false) String searchParams,
                             @RequestParam(value = "current") int current,
                             @RequestParam(value = "pageSize") int pageSize ) throws Exception {
        Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
        if(params!=null){
            params.put("pCode",String.valueOf(params.get("realName")));
            params.put("pName",String.valueOf(params.get("phone")));
            params.put("pHandPerson",String.valueOf(params.get("pHandPerson")));
        }
        Page<ClFlowInfo> page = clFlowInfoService.getAllProdctListForUV(params,current,pageSize);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, page);
        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        ServletUtils.writeToResponse(response,result);
    }


    /**
     *催款订单详细记录
     */
    @RequestMapping(value="/act/flowControl/getAllForUVDetail.htm",method={RequestMethod.GET,RequestMethod.POST})
    public void listDetail(@RequestParam(value = "id") Long id) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flowId",id);
        List<ClFlowUV> clFlowUVList= clFlowUVService.getAllFlowUv(params);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, clFlowUVList);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        ServletUtils.writeToResponse(response,result);
    }




    /**
     *  获取字典值集合
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/act/flowControl/getMutilCheckBox.htm")
    public void list(HttpServletRequest request, HttpServletResponse response,String typeCode) throws Exception{
        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<>();
        param.put("typeCode", typeCode);  //如：FLOWINFO_P_TAG
        List<SysDictDetail> sysDictDetailList =  sysDictDetailService.listByTypeCode(param);
        res.put(Constant.RESPONSE_DATA, sysDictDetailList);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        ServletUtils.writeToResponse(response, res);
    }




    /**
     * 保存流量平台信息
     *
     * @param request
     * @param response
     * @param data
     * @throws Exception
     */
    @RequestMapping("/act/flowControl/saveInfo.htm")
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "form") String data) throws Exception {
        String picUrls ="";
        //将前台传的数据进行解析
        HashMap<String, Object> dataMap = JsonUtil.parse(data, HashMap.class);

        String id = dataMap.get("id") != null ? dataMap.get("id").toString() : "";
        System.out.println("----------------------------------id = " + id);
        String pName = dataMap.get("pname")!=null ? dataMap.get("pname").toString() :"";
        String pType = dataMap.get("pType_convert") !=null ? dataMap.get("pType_convert").toString() :"0";
//        String pType = dataMap.get("pType") !=null ? dataMap.get("pType").toString() :"0";
        String pRemark = dataMap.get("premark") !=null ? dataMap.get("premark").toString():"";
        String pState = dataMap.get("pstate") !=null ? dataMap.get("pstate").toString():"0";
        String pHttp = dataMap.get("phttp") !=null ? dataMap.get("phttp").toString():"";
        String pCode = dataMap.get("pcode")!= null ? dataMap.get("pcode").toString():"";
        String pSort = dataMap.get("psort") !=null ? dataMap.get("psort").toString():"";
        String picUrl = dataMap.get("picUrl") !=null ?dataMap.get("picUrl").toString():"";
        String minLimit = dataMap.get("minLimit")!=null?dataMap.get("minLimit").toString():"0";
        String maxLimit =  dataMap.get("maxLimit")!=null? dataMap.get("maxLimit").toString():"0";
        String minDay = dataMap.get("minDay")!=null ? dataMap.get("minDay").toString():"0";
        String maxDay = dataMap.get("maxDay")!=null ? dataMap.get("maxDay").toString():"0";
        String pCondition =dataMap.get("pcondition")!=null ? dataMap.get("pcondition").toString():"";
        String pProcess = dataMap.get("pprocess")!=null ? dataMap.get("pprocess").toString():"";
        String pInterest = dataMap.get("pinterest")!=null ? dataMap.get("pinterest").toString():"";
        String pBorrowNum = dataMap.get("pBorrowNum") !=null ? dataMap.get("pBorrowNum").toString():"";
        String pLoanMaxTime = dataMap.get("ploanMaxTime")!=null ? dataMap.get("ploanMaxTime").toString():"0";
        String pLoanMinTime = dataMap.get("ploanMinTime")!=null ? dataMap.get("ploanMinTime").toString():"0";
        String pLoanTimeType = dataMap.get("ploanTimeType")!=null ? dataMap.get("ploanTimeType").toString():"-1";
        String pTag = dataMap.get("ptag")!=null ? dataMap.get("ptag").toString():"";
        String pMarks = dataMap.get("pmarks")!=null ? dataMap.get("pmarks").toString():"";
        String pMessage = dataMap.get("pmessage")!=null ? dataMap.get("pmessage").toString():"";
        String handPerson = dataMap.get("pHandPerson")!=null ? dataMap.get("pHandPerson").toString():"";
        String channelPrice = dataMap.get("pChannelPrice")!=null ? dataMap.get("pChannelPrice").toString():"";
        String typeSort = dataMap.get("typeSort")!=null ? dataMap.get("typeSort").toString():"";

        String imgPath= dataMap.get("path")!=null?dataMap.get("path").toString():"";
        String imgName= dataMap.get("name")!=null?dataMap.get("name").toString():"";

        String tempImgTemp = request.getServletContext().getRealPath("/")+"flowPlatFormImg";  //服务器中临时存放目录
        ClFlowInfo flowInfo = new ClFlowInfo();
        flowInfo.setPName(pName);
//        flowInfo.setPType( pType == null?-1:Integer.parseInt(pType) );
        flowInfo.setpBorrowNum( pBorrowNum == null?-1:Integer.parseInt(pBorrowNum) );
        flowInfo.setPRemark( pRemark );
        flowInfo.setPMarks( pMarks );
        flowInfo.setPMessage( pMessage );
        flowInfo.setPState( pState == null?-1:Integer.parseInt(pState) );
        flowInfo.setPHttp( pHttp );
        flowInfo.setPCode( pCode );
        flowInfo.setPSort( pSort == null?-1:Integer.parseInt(pSort) );
        flowInfo.setTypeSort( typeSort == null?-1:Integer.parseInt(typeSort) );
        flowInfo.setPicUrl(picUrl);
        flowInfo.setMinLimit(minLimit == null ?0:Long.parseLong(minLimit));
        flowInfo.setMaxLimit(maxLimit == null ?0:Long.parseLong(maxLimit));
        flowInfo.setMinDay( minDay == null ?0:Integer.parseInt(minDay) );
        flowInfo.setMaxDay(maxDay == null ?0:Integer.parseInt(maxDay));
        flowInfo.setPCondition(pCondition);
        flowInfo.setPInterest(pInterest);

        flowInfo.setPLoanMaxTime(Integer.parseInt( pLoanMaxTime ));
        flowInfo.setPLoanMinTime(Integer.parseInt( pLoanMinTime ));
        flowInfo.setPLoanTimeType(Integer.parseInt( pLoanTimeType ));
        flowInfo.setpHandPerson(handPerson);
        flowInfo.setpChannelPrice(minLimit == null ?0:Double.parseDouble(channelPrice));

         if(!StringUtil.isEmpty(pTag)){
             String trimpTag = pTag.replaceAll(" ","");
             pTag = trimpTag.substring(1,trimpTag.length()-1);
             flowInfo.setPTag(pTag);
         }

        if(!StringUtil.isEmpty(pProcess)){
             String trimPProcess = pProcess.replaceAll(" ","");
            pProcess = trimPProcess.replaceAll(" ","").substring(1,trimPProcess.length()-1);
            flowInfo.setPProcess(pProcess);
        }
        //渠道可多选分类
        if(!StringUtil.isEmpty(pType)){
            String trimPProcess = pType.replace("[","").replace("]","").replaceAll(" ","");
            String[] types = trimPProcess.split(",");
            Integer value =0;

            for (int i =0;i<types.length ;i++) {
                value = StateUtil.addState(value,Integer.parseInt(types[i]));
            }
            flowInfo.setPType(value);
        }

        String opType = "insert";
        if(!StringUtil.isEmpty(id)){
            opType = "update";
            flowInfo.setId(Long.parseLong(id));
        }else{
            //新增加上创建时间
            Date now = DateUtil.getNow();
            flowInfo.setCreateTime(now);
        }
        //imgPath:临时服务器路径,imgName:图片名称,tempImgTemp:服务器临时存放目录,opType:保存或更新标志
         picUrls =  clFlowInfoService.saveOrUpdate(flowInfo,imgPath,imgName,tempImgTemp,opType);
        Map<String,Object> responseMap = new HashMap<String,Object>();
        if (!StringUtil.isEmpty( picUrls)  ) {
            responseMap.put("imgUrlOss", picUrls);

        }
        responseMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        responseMap.put(Constant.RESPONSE_CODE_MSG, "保存成功");
        ServletUtils.writeToResponse(response, responseMap);
    }

    /**
     * 获取本月的点击数
     *
     * @throws Exception
     */
    @RequestMapping(value = "/act/flowControl/getPlatFormClick.htm")
    public void getPlatFormClick(@RequestParam(value = "code") String code) throws Exception {

        Map<String, Integer> amountStatic = new HashMap<String, Integer>();
        Map<String, Object> result = clFlowInfoService.getAmountClick(code);

        result.put("amount", amountStatic);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "处理成功");
        //加上返回参数
        ServletUtils.writeToResponse(response, result);
    }


    /**
     * 平台信息删除（物理删除），此步骤涉及删除redis中的平台地址及平台点击数等数据
     *
     * @throws ServiceException
     * @throws Exception
     */
    @RequestMapping(value = "/act/flowControl/delete.htm")
    public void channelDelete(@RequestParam("id") Long id,
                           @RequestParam(value = "code") String code,
                           @RequestParam(value = "picName") String picName,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> res = new HashMap<String, Object>();
        boolean result =  clFlowInfoService.channelDelete(id,code,picName);
        if (result) {
            res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            res.put(Constant.RESPONSE_CODE_MSG, "删除成功");
        } else {
            res.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            res.put(Constant.RESPONSE_CODE_MSG, "删除失败");
        }
        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 临时上传图片到服务器
     *
     * @throws ServiceException
     * @throws Exception
     */
    @RequestMapping(value = "/act/flowControl/uploadImg.htm")
    public void upload(@RequestParam(value = "image", required = false) MultipartFile image,
                       @RequestParam(value = "filename", required = false) String fileName) throws Exception {
        String realPath = request.getServletContext().getRealPath("/");

        String  imgPath = clFlowInfoService.savePic(realPath,image,fileName);

        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, "成功");
        res.put("imgPath", imgPath);
        logger.info("-------------------------------------上传图片到本地服务器成功了！！！,服务器位置"+imgPath);
        ServletUtils.writeToResponse(response, res);
    }

}
