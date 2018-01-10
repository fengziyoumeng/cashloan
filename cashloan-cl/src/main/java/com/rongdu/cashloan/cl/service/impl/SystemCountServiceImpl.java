package com.rongdu.cashloan.cl.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.rongdu.cashloan.core.enums.AreaStatisticsEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import tool.util.BigDecimalUtil;
import tool.util.StringUtil;

import com.rongdu.cashloan.cl.mapper.SystemCountMapper;
import com.rongdu.cashloan.cl.service.SystemCountService;
import com.rongdu.cashloan.core.common.util.DateUtil;

/**
 * 首页系统数据统计
 * @author caitt
 * @version 1.0
 * @date 2017年3月16日上午10:16:51
 * Copyright 杭州民华金融信息服务有限公司 现金贷  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@SuppressWarnings("unchecked")
@Service("systemCountService")
public class SystemCountServiceImpl implements SystemCountService {
	
	@Resource
	private SystemCountMapper systemCountMapper;

	@Override
	public Map<String, Object> systemCount()throws Exception {
		Map<String,Object> rtMap = new HashMap<String, Object>();

		//当天注册数
		Integer register = systemCountMapper.todayRegister();
		rtMap.put("todayRegister", register);

		//当月注册数
		Integer monthRegister = systemCountMapper.monthRegister();
		rtMap.put("monthRegister", monthRegister);

		//总注册数
		Integer totalRegister = systemCountMapper.totalRegister();
		rtMap.put("totalRegister", totalRegister);


		List<String> days = new ArrayList<String>();
		Date nowDate = DateUtil.getNow();
		days.add(DateUtil.dateStr(nowDate, DateUtil.DATEFORMAT_STR_002));
		Calendar date = Calendar.getInstance();
		for(int i=0;i<15;i++){
			date.setTime(nowDate);
			date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
			nowDate = date.getTime();
			String day = DateUtil.dateStr(nowDate, DateUtil.DATEFORMAT_STR_002);
			days.add(day);
		}

		//注册柱状图
		List<Map<String,Object>> rtValue1 = systemCountMapper.countFifteenDaysRegister();
		HashMap<String,Object> fiftyRegisterMap = new HashMap<String,Object>();
		for(Map<String,Object> temp : rtValue1){
			fiftyRegisterMap.put(temp.get("regist_time").toString(), Long.parseLong( temp.get("user_count").toString()));
		}



        //UV柱状图
        List<Map<String,Object>> rtValue2 = systemCountMapper.countPnameUV();
        HashMap<String,Object> tValue2Map = new HashMap<String,Object>();
        for(Map<String,Object> temp : rtValue2){
        	if ( temp.get("p_name")!=null && temp.get("p_count")!= null){
				tValue2Map.put(temp.get("p_name").toString(), Long.parseLong( temp.get("p_count").toString()));
			}
        }


//		Map<String,Object> result1 = reBuildMap(rtValue1);
//		Map<String,Object> result2 = reBuildMap(rtValue2);
//		Map<String,Object> result4 = reBuildMap(rtValue4);
//		Map<String,Object> result3 = new HashMap<String, Object>();
//		for(int i=0;i<days.size();i++){
//			String day = days.get(i);
//			if(!result1.containsKey(day)){
//				result1.put(day, 0.00);
//			}
//			if(!result2.containsKey(day)){
//				result2.put(day, 0.00);
//			}
//
//		}

		rtMap.put("countFifteenDaysRegister", fiftyRegisterMap);
        rtMap.put("countPnameUV", tValue2Map);
		
		return rtMap;
	}
	
	public Map<String,Object> reBuildMap(List<Map<String,Object>> maps){
		if(maps!=null){
			Map<String,Object> result = new HashMap<String, Object>();
			for(int i=0;i<maps.size();i++){
				String key = String.valueOf(maps.get(i).get("key"));
				if(StringUtil.isNotBlank(key)){
					key = key==null?"":key;
					
				}else{
					key = "未知地区";
				}
				Object value = maps.get(i).get("value");
				result.put(key, value);
			}
			result.remove("null");
			return result;
		}else{
			return new HashMap<String, Object>();
		}
	}

}
