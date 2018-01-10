package com.rongdu.cashloan.cl.service;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.UserAuth;
import com.rongdu.cashloan.cl.model.UserAuthModel;
import com.rongdu.cashloan.core.common.service.BaseService;

import java.util.Map;


/**
 * 用户认证信息表Service
 *
 */
public interface UserAuthService extends BaseService<UserAuth, Long>{

	public UserAuth getUserAuth(Map<String,Object> paramMap);

	/**
	 * 获取用户认证状态，关联同盾认证状态
	 * @author pantheon
	 * @param paramMap
	 * @return
	 */
	public UserAuth getUserAuthNewVersion(Map<String, Object> paramMap) ;
	
	public Integer updateByUserId(Map<String,Object> paramMap);
	
	
	Page<UserAuthModel> listUserAuth(Map<String, Object> params, int currentPage,
			int pageSize);

	/**
	 * 查询认证状态
	 * @param userId
	 * @return
	 */
	public UserAuth findSelective(long userId);
	
	public UserAuth findSelectiveWithVersion(long userId);
	
//	public Map<String,Object> getAuthState(String userId);
	
	public int updatePhoneState(Map<String, Object> userAuth);
	
}
