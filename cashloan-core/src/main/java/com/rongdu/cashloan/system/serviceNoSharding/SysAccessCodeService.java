package com.rongdu.cashloan.system.serviceNoSharding;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.core.common.service.BaseService;
import com.rongdu.cashloan.system.domain.SysAccessCode;
import com.rongdu.cashloan.system.domain.SysUser;
import com.rongdu.cashloan.system.model.SysAccessCodeModel;

/**
 * 访问码Service
 * 
 * @author dufy
 * @version 1.0.0
 * @date 2017-03-24 17:37:49
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public interface SysAccessCodeService extends BaseService<SysAccessCode, Long>{
	/**
	 * 访问码信息列表查询
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<SysAccessCodeModel> listAccessCodeModel(Map<String, Object> params,
			int currentPage, int pageSize);
	
	/**
	 * 保存
	 * @param accessCode
	 * @param time
	 * @return
	 */
	int save(SysAccessCode accessCode, String time);
	
	/**
	 * 根据ID更新
	 * @param data
	 * @return
	 */
	int update(SysAccessCode ac,String time);
	
	/**
	 * 查询某用户code数量
	 * @param sysUserId
	 * @param code
	 * @return
	 */
	int countCode(long sysUserId, String code);
	
	/**
	 * 查询访问码列表
	 * @param sysUserId
	 * @return
	 */
	List<SysAccessCode> listSysAccessCode(Long sysUserId);
	
	/**
	 * 查询访问码
	 * @param map
	 * @return
	 */
	SysAccessCode findSysAccessCode(Map<String,Object> map);
	
	/**
	 * 更新访问码状态
	 * @param data
	 * @return
	 */
	int updateState(SysAccessCode ac);
	
	/**
	 * 用户名列表
	 * @return
	 */
	List<SysUser> listUserName();
}
