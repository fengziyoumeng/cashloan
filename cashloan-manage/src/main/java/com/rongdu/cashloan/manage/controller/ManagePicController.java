package com.rongdu.cashloan.manage.controller;


import com.rongdu.cashloan.cl.domain.FlowPic;
import com.rongdu.cashloan.cl.service.FlowPicService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("prototype")
@Controller
public class ManagePicController extends BaseController {
    @Resource
    private FlowPicService flowPicService;

    /**
     * 获取banner及跳转图片列表信息
     *
     * @throws Exception
     */
    @RequestMapping(value = "/act/picController/picManage.htm")
    public void  getInfoManage( ) throws Exception {
        List<FlowPic> picList = flowPicService.getAllBanner();
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, picList);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response,result);
    }
    /**
     * 删除banner
     *
     * @throws Exception
     */
    @RequestMapping(value = "/act/bannerInfo/delete.htm")
    public void  deleteBanner(HttpServletRequest request, HttpServletResponse response,
                               Long id) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            flowPicService.deleteById(id);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "删除成功");
            ServletUtils.writeToResponse(response, result);
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "删除失败");
            ServletUtils.writeToResponse(response, result);
        }
    }

    /**
     * 保存或更新banner
     *
     * @throws Exception
     */
    @RequestMapping(value = "/act/bannerInfo/saveOrUpdate.htm")
    public void  saveBnnerInfo(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "form") String data) throws Exception{

        String picUrls ="";
        //将前台传的数据进行解析
        HashMap<String, Object> dataMap = JsonUtil.parse(data, HashMap.class);

        String id = dataMap.get("id") != null ? dataMap.get("id").toString() : "";
        String pType = dataMap.get("pType")!=null ? dataMap.get("pType").toString() :"";
        String skipType = dataMap.get("skipType") !=null ? dataMap.get("skipType").toString() :"";
        String state = dataMap.get("state") !=null ? dataMap.get("state").toString():"";
        String wUrl = dataMap.get("wUrl") !=null ? dataMap.get("wUrl").toString():"";
        String url = dataMap.get("url") !=null ? dataMap.get("url").toString():"";
        String sort = dataMap.get("sort") !=null ? dataMap.get("sort").toString():"";

        String bannerPath = dataMap.get("path") !=null ? dataMap.get("path").toString():"";
        String skipImgPath = dataMap.get("picPath")!= null ? dataMap.get("picPath").toString():"";

/*
        System.out.println(id);
        System.out.println(pType);
        System.out.println(skipType);
        System.out.println(state);
        System.out.println("bannerPath"+bannerPath);
        System.out.println("skipImgPath:"+skipImgPath);
*/

        FlowPic flowPic = new FlowPic();
        if(StringUtil.isNotEmpty(id)){
            flowPic.setId(Long.parseLong(id));
        }
        flowPic.setPId(3);
        if(StringUtil.isNotEmpty(bannerPath)){
            flowPic.setUrl(bannerPath);
        }else{
            flowPic.setUrl(url);
        }
        flowPic.setState(Integer.parseInt(state));
        flowPic.setpType(Integer.parseInt(pType));
        flowPic.setSort(Integer.parseInt(sort));
       if (skipType.equals("0")){
           flowPic.setPId(3);
       }else if(skipType.equals("1")){
           if(wUrl!=null && StringUtil.isNotEmpty(wUrl)){
               flowPic.setwUrl(wUrl);
           }else{
               throw new RuntimeException("请填写跳转地址");
           }
           flowPic.setpCode("1");
       }else if(skipType.equals("2")){
           if(skipImgPath!=null && StringUtil.isNotEmpty(skipImgPath)){
               flowPic.setwUrl(skipImgPath);
           }else if(StringUtil.isNotEmpty(wUrl)){
               flowPic.setwUrl(wUrl);
           }else{
               throw new RuntimeException("请上传要跳转的图片");
           }
       }else if(skipType.equals("3")){
           if(wUrl!=null && StringUtil.isNotEmpty(wUrl)){
               flowPic.setwUrl(wUrl);
               flowPic.setpCode("1");
           }else{
               throw new RuntimeException("请填写跳转地址");
           }
       }

        Map<String,Object> result = new HashMap<String,Object>();
        try{
           flowPicService.saveOrUpdate(flowPic);
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "操作成功");
           ServletUtils.writeToResponse(response,result);
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "插入或更新失败");
            ServletUtils.writeToResponse(response,result);

       }
    }


}
