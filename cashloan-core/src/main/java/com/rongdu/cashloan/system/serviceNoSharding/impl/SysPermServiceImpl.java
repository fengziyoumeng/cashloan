package com.rongdu.cashloan.system.serviceNoSharding.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.system.domain.SysPerm;
import com.rongdu.cashloan.system.mapper.SysPermMapper;
import com.rongdu.cashloan.system.serviceNoSharding.SysPermService;

@Service("sysPermService")
public class SysPermServiceImpl extends BaseServiceImpl<SysPerm,Long> implements SysPermService {

	@Resource
	private SysPermMapper sysPermDao;

	@Override
	public int updateByPrimaryKeySelective(SysPerm record) {
		return sysPermDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<SysPerm> listByUserName(String userName) {
		return sysPermDao.listByUserName(userName);
	}

	@Override
	public List<Map<String, Object>> fetchAll() {
		return sysPermDao.fetchAll();
	}

	@Override
	public List<SysPerm> listByRoleId(Long roleId) {
		return sysPermDao.listByRoleId(roleId);
	}

	@Override
	public BaseMapper<SysPerm,Long> getMapper() {
		return sysPermDao;
	}


}
