package com.rongdu.cashloan.manage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.manage.domain.QuartzInfo;
import com.rongdu.cashloan.manage.mapper.QuartzInfoMapper;
import com.rongdu.cashloan.manage.mapper.QuartzLogMapper;
import com.rongdu.cashloan.manage.model.QuartzInfoModel;
import com.rongdu.cashloan.manage.service.QuartzInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tool.util.DateUtil;
import tool.util.StringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务详情ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-03-15 13:30:53
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Service("quartzInfoService")
public class QuartzInfoServiceImpl extends BaseServiceImpl<QuartzInfo, Long> implements QuartzInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(QuartzInfoServiceImpl.class);
   
    @Resource
    private QuartzInfoMapper quartzInfoMapper;
    
    @Resource
    private QuartzLogMapper quartzLogMapper;

	@Override
	public BaseMapper<QuartzInfo, Long> getMapper() {
		return quartzInfoMapper;
	}

	@Override
	public boolean save(QuartzInfo qi) {
		int result = quartzInfoMapper.save(qi);
		if (result > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean update(Map<String, Object> search) {
		int result = quartzInfoMapper.updateSelective(search);
		if (result > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<QuartzInfo> list(Map<String, Object> result) {
		return quartzInfoMapper.listSelective(result);
	}

	@Override
	public Page<QuartzInfoModel> page(Map<String, Object> searchMap,
                                      int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<QuartzInfoModel> list = quartzInfoMapper.page(searchMap);
		
		for (QuartzInfoModel quartzInfoModel : list) {
			String lastStartTime = quartzLogMapper.findLastTimeByQzInfoId(quartzInfoModel.getId());
			if(StringUtil.isNotBlank(lastStartTime)){
				quartzInfoModel.setLastStartTime(DateUtil.valueOf(lastStartTime));
			}
			
		}
		
		return (Page<QuartzInfoModel>) list;
	}

	@Override
	public QuartzInfo findByCode(String code){
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("code", code);
			QuartzInfo quartzInfo = quartzInfoMapper.findSelective(paramMap);
			if (null != quartzInfo) {
				return quartzInfo;
			}
		} catch (Exception e) {
			logger.error("查询定时任务异常", e);
		}
		return null;
	}
	
	@Override
	public QuartzInfo findSelective(Map<String, Object> paramMap) {
		try {
			QuartzInfo quartzInfo = quartzInfoMapper.findSelective(paramMap);
			if (null != quartzInfo) {
				return quartzInfo;
			}
		} catch (Exception e) {
			logger.error("查询定时任务异常", e);
		}
		return null;
	}
	
}