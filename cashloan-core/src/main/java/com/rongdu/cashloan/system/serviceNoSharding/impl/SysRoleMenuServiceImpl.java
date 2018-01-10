package com.rongdu.cashloan.system.serviceNoSharding.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rongdu.cashloan.core.common.exception.PersistentDataException;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.system.domain.SysRoleMenu;
import com.rongdu.cashloan.system.mapper.SysRoleMenuMapper;
import com.rongdu.cashloan.system.serviceNoSharding.SysRoleMenuService;

@Service(value = "sysRoleMenuServiceImpl")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
	@Resource
	private SysRoleMenuMapper sysRoleMenuDao;
	
	@Override
	public List<SysRoleMenu> getRoleMenuList(Long roleId) throws ServiceException, PersistentDataException {
		return this.sysRoleMenuDao.getRoleMenuList(roleId);
	}

	public SysRoleMenuMapper getSysRoleMenuDao() {
		return sysRoleMenuDao;
	}

	public void setSysRoleMenuDao(SysRoleMenuMapper sysRoleMenuDao) {
		this.sysRoleMenuDao = sysRoleMenuDao;
	}



}
