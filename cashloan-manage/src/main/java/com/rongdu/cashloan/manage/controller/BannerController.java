package com.rongdu.cashloan.manage.controller;


import com.rongdu.cashloan.cl.domain.BannerInfo;
import com.rongdu.cashloan.cl.service.BannerService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("prototype")
@Controller
public class BannerController extends BaseController {
    @Resource
    private BannerService bannerService;

    /**
     * 获取banner及跳转图片列表信息
     *
     * @throws Exception
     */
    @RequestMapping(value = "/act/newbannerinfo/getall.htm")
    public void getInfoManage() throws Exception {
        List<BannerInfo> picList = bannerService.getAllBanner();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_DATA, picList);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 删除banner
     *
     * @throws Exception
     */
    @RequestMapping(value = "/act/newbannerInfo/delete.htm")
    public void deleteBanner(Long id) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            bannerService.deleteById(id);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "删除成功");
            ServletUtils.writeToResponse(response, result);
        } catch (Exception e) {
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
    @RequestMapping(value = "/act/newbannerInfo/saveOrUpdate.htm")
    public void saveBnnerInfo(@RequestParam(value = "form") String data) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            bannerService.saveOrUpdate(data);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "删除成功");
            ServletUtils.writeToResponse(response, result);
        } catch (Exception e) {
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "删除失败");
            ServletUtils.writeToResponse(response, result);
        }
    }
}
