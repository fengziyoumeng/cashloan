package com.rongdu.cashloan.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.system.domain.SysConfig;
import com.rongdu.cashloan.system.serviceNoSharding.SysConfigService;


/**
* 协议Controller
* 
* @author sly
* @version 1.0.0
* @date 2017-02-22 13:57:14
* Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
* 官方网站：www.yongqianbei.com
* 未经授权不得进行修改、复制、出售及商业使用
*/
@Scope("prototype")
@Controller
public class ProtocolController  extends BaseController{
	
	@Resource
	private SysConfigService sysConfigService;
	
	
	/**
	 * 获取协议清单
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/protocol/list.htm", method = RequestMethod.GET)
	public void list() throws Exception {
		Map<String,Object>  data = new HashMap<>();
		
		List<Map<String,Object>> dataList= new ArrayList<Map<String,Object>>();
		List<SysConfig> list= sysConfigService.listByCode("protocol_");
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> pro =new HashMap<>();
			pro.put("code",list.get(i).getCode());
			pro.put("value",list.get(i).getValue());
			pro.put("name",list.get(i).getName());
			dataList.add(pro);
		}
		data.put("list", dataList);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}

}
