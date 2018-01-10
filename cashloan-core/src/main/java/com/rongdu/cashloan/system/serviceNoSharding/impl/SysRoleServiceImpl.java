package com.rongdu.cashloan.system.serviceNoSharding.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.system.domain.SysRole;
import com.rongdu.cashloan.system.mapper.SysRoleMapper;
import com.rongdu.cashloan.system.security.authentication.encoding.PasswordEncoder;
import com.rongdu.cashloan.system.serviceNoSharding.SysRoleService;

@SuppressWarnings("rawtypes")
@Service(value = "sysRoleServiceImpl")
public class SysRoleServiceImpl extends BaseServiceImpl implements SysRoleService {
	@Resource
	private SysRoleMapper sysRoleMapper;
	@Resource
	private PasswordEncoder passwordEncoder;
	@Override
	public List<SysRole> getRoleListByUserId(long userId) throws ServiceException{
		try {
			return this.sysRoleMapper.getRoleListByUserId(userId);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public SysRole getRoleById(long id) throws ServiceException {
		try {
			return this.sysRoleMapper.findByPrimary(id);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public int deleteRole(long id) throws ServiceException {
		try {
			return this.sysRoleMapper.deleteById(id);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public Page<SysRole> getRolePageList(int currentPage,int pageSize,Map<String, Object> mapdata) throws ServiceException {
		try {
			PageHelper.startPage(currentPage, pageSize);
			return (Page<SysRole>)sysRoleMapper.listSelective(mapdata);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public long addRole(SysRole role) throws ServiceException {
		try {
			return this.sysRoleMapper.save(role);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public long insertByMap(Map<String, Object> data) throws ServiceException {
		try {
			return this.sysRoleMapper.insertByMap(data);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public int updateRole(Map<String, Object> arg) throws ServiceException {
		try {
			return this.sysRoleMapper.updateByMap(arg);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public List<SysRole> getList(Map<String, Object> paramMap) {
		return sysRoleMapper.listSelective(paramMap);
	}

	@Override
	public BaseMapper getMapper() {
		return null;
	}

	@Override
	public List<Map<String, Object>> getByUserPassRolesList(String username,
			String password) throws ServiceException {
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("username", username);
		data.put("password", passwordEncoder.encodePassword(password, "rongdumlms"));

		try {
		  List<Map<String, Object>>  roles = sysRoleMapper.getByUserPassRolesList(data);
		  
		  if(roles == null){
			  
			  throw new ServiceException("获取角色数据失败");			  
		  }
		  return roles;
		  
		} catch (Exception e) {
			 throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public int getRolecount(Map<String, Object> mapdata) throws ServiceException {
		try {
			return this.sysRoleMapper.getRolecount(mapdata);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public SysRole findByNid(String nid) {
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("nid", nid);
		return sysRoleMapper.findSelective(roleMap);
	}
	
	
}
