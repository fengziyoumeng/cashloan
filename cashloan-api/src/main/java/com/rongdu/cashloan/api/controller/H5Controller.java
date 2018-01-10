package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.domain.FlowPic;
import com.rongdu.cashloan.cl.domain.FlowRadio;
import com.rongdu.cashloan.cl.service.ClFlowInfoService;
import com.rongdu.cashloan.cl.service.FlowPicService;
import com.rongdu.cashloan.cl.service.FlowRadioService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.system.domain.SysConfig;
import com.rongdu.cashloan.system.domain.SysDictDetail;
import com.rongdu.cashloan.system.serviceNoSharding.SysConfigService;
import com.rongdu.cashloan.system.serviceNoSharding.SysDictDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * H5页面Controller
 * @author xx
 * @version 1.0.0
 * @date 2017年2月24日 下午4:34:51
 * Copyright 杭州民华金融信息服务有限公司 金融创新事业部 此处填写项目英文简称  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Scope("prototype")
@Controller
@CrossOrigin
public class H5Controller extends BaseController{
	public static final Logger logger = LoggerFactory.getLogger(H5Controller.class);
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private ClFlowInfoService clFlowInfoService;
	@Autowired
	private ShardedJedisClient redisClient;
	@Resource
	private SysDictDetailService sysDictDetailService;
	@Resource
	private FlowPicService flowPicService;

	@Resource
	private FlowRadioService flowRadioService;
	/**
	 * 获取H5页面清单
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/h5/list.htm", method = RequestMethod.GET)
	public void list() throws Exception {
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		List<SysConfig> list = sysConfigService.listByCode("h5_");
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> pro =new HashMap<>();
			pro.put("code",list.get(i).getCode());
			pro.put("value",list.get(i).getValue());
			pro.put("name",list.get(i).getName());
			dataList.add(pro);
		}
		Map<String,Object> data = new HashMap<>();
		data.put("list", dataList);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 生成图片验证码
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/h5/imgCode/generate.htm", method = RequestMethod.GET)
	public void generate() throws Exception {
		super.generateImgCode();
	}
	/**
	 * 获取其他公司产品的信息（首页信息）
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/act/flowControl/getFlowPlatformInfo.htm")
	public void  getInfo(@RequestParam(value = "flag",required = false) String flag,
						 @RequestParam(value = "current",required = false) int current,
						 @RequestParam(value = "deadLine",required = false) String deadLine,
						 @RequestParam(value = "limit",required = false) String limit) throws Exception {
		Map<String, Object> searchMap = new HashMap<>();
		//展示热门信息
		List<ClFlowInfo> list =new ArrayList<ClFlowInfo>();
		Map<String,Object> result = new HashMap<String,Object>();
		try{
            //查询热门
			if("hot".equals(flag)){
			list = clFlowInfoService.getHot();
		}
		//搜索
		if("search".equals(flag)){
			searchMap.put("day", deadLine);
			searchMap.put("limit", limit);
			list = clFlowInfoService.getAll(current,searchMap);
		}
		//查所有
		if("all".equals(flag)){
			 list = clFlowInfoService.getAll(current,searchMap);
		}
			result.put(Constant.RESPONSE_DATA, list);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
	}catch (Exception e){
		result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
	}

		ServletUtils.writeToResponse(response,result);
		//加上返回参数
	}

	/**
	 * H5获取该条流量平台的详情信息
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/act/flowControl/getPlatformInfo.htm")
	public void getClickInfo(@RequestParam(value = "id") String id,@RequestParam(value = "pCode") String pCode) throws Exception {
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", id);
		List<ClFlowInfo> list =new ArrayList<ClFlowInfo>();
		ClFlowInfo item=clFlowInfoService.getProdctList(searchMap).get(0);
		Map<String,Object> result = new HashMap<String,Object>();
		try{
		//申请流程处理
		if(item.getPProcess()!=null){
			String processStr=handleDict(item.getPProcess().toString(),"FLOWINFO_P_PROCESS","process");
			item.setPProcess(processStr);
		}
		//申请标签处理
		if(item.getPTag()!=null) {
			String tagStr=handleDict(item.getPTag()+"","FLOWINFO_P_TAG","tag");
			item.setPTag(tagStr);
		}
		list.add(item);
		result.put(Constant.RESPONSE_DATA, list);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
	}catch (Exception e){
		result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
	}

		ServletUtils.writeToResponse(response,result);
	}
	/**
	 * H5点击申请进入其他公司产品的页面，并开始计数
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/act/flowControl/statisAmount.htm")
	public void saveClickInfo(@RequestParam(value = "pCode") String pCode,
							  @RequestParam(value = "id") Long id) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			boolean url = clFlowInfoService.getUrl(id,pCode);
			if(url){
				result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
			}else {
				result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
				result.put(Constant.RESPONSE_CODE_MSG, "插入失败");
			}
		}catch (Exception e){
			result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
		}
		ServletUtils.writeToResponse(response,result);
	}
	//处理字典字符串拼接
	private String handleDict(String pProcess,String itemCode,String flag) {
		String[] process=pProcess.split(",");
		String processStr="";
		for(int i=0;i<process.length;i++){
			try {
				SysDictDetail itemDict = sysDictDetailService.findDetail(process[i],itemCode);
				if(i<process.length-1) {
					if("process".equals(flag)){
						processStr += itemDict.getItemValue() + "=>";
					}else{
						processStr += itemDict.getItemValue() + "|";
					}
				}else{
					processStr += itemDict.getItemValue();
				}
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return processStr;
	}
	@RequestMapping("/act/infoH5/getPicUrl.htm")
	public void getPicUrl(HttpServletResponse response){
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> data = new HashMap<>();
		try {
			List<FlowPic> pics = flowPicService.getPic(1);
			List<FlowRadio> radios = flowRadioService.getRadio();
			data.put("pics",pics);
			data.put("radios",radios);
			result.put(Constant.RESPONSE_DATA, data);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		}catch (Exception e){
			result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
		}
		ServletUtils.writeToResponse(response,result);
	}
}
