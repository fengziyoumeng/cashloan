/**
 *
 *
 * @author rd
 * @version 1.0.0.0
 * @date 2016年12月02日 下午14:56:41
 * Copyright 杭州民华金融信息服务有限公司 资金托管系统  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
package com.rongdu.cashloan.system.mapper;

import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;
import com.rongdu.cashloan.system.domain.SysRolePerm;

@RDBatisDao
public interface SysRolePermMapper extends BaseMapper<SysRolePerm, Long>{

    SysRolePerm selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePerm record);
    
    int deleteByRoleId(Integer roleId);
    
}