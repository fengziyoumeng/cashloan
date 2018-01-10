package com.rongdu.cashloan.system.mapper;

import java.util.List;
import java.util.Map;

import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;
import com.rongdu.cashloan.system.domain.SysPerm;

@RDBatisDao
public interface SysPermMapper extends BaseMapper<SysPerm,Long>  {

    SysPerm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPerm record);
    
    List<SysPerm> listByUserName(String userName);
    
    List<SysPerm> selectAll();
    
	List<SysPerm> listByRoleId(Long roleId);

	List<Map<String, Object>> fetchAll();
}