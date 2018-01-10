package com.rongdu.cashloan.system.serviceNoSharding;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.system.domain.SysRole;
import com.rongdu.cashloan.system.domain.SysUser;


/**
 * 
 * 系统用户服务
 * @version 1.0
 * @author 吴国成
 * @created 2014年9月22日 下午3:48:21
 */
public interface SysUserService {

	/**
	 * 根据userName修改用户登陆信息（只修改登陆时间，登陆IP）
	 * @param sysUser 用户信息
	 * @return 是否成功
	 */
	Boolean editUserLoginInfo(SysUser sysUser) throws ServiceException;
	
	/**
	 * 根据用户ID修改用户密码
	 * @param sysUser 用户
	 * @return 是否成功
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	Boolean editUserPassWord(SysUser sysUser) throws ServiceException;
	
	/**
	 * 添加用户方法
	 * @param user 用户对象
	 * @param roleIdArr 用户选择角色数组
	 * @throws ServiceException 
	 */
	void addUser(SysUser user, String roleIdArr) throws ServiceException;
	
	/**
	 * 用户查询
	 * @param id 主键ID
	 * @return 用户信息
	 */
	SysUser getUserById(long id)  throws ServiceException;
	
	/**
	 * 修改用户
	 * @param user 用户
	 */
	int userUpdate(SysUser user) throws ServiceException;
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName 用户名
	 * @return 用户信息
	 */
	SysUser getUserByUserName(String userName) throws ServiceException;
     
    /**
     * 分页查询出的列表
     * @param mapdata
     * @return
     */
	Page<Map<String, Object>> getUserPageList(int currentPage,int pageSize,Map<String, Object> mapdata) throws ServiceException;
    
    /**
     * 获取用户总记录数
     * @return
     */
    int getUserSum(Map<String,Object> map) throws ServiceException;
    
    /**
     *根据ID更新
     *@param map
     *@return Boolean
     */ 
    Boolean updateSysUserById(Map<String , Object> map) throws ServiceException;  
    
	/**
	 * 根据机构获取客户专员
	 * @param paramMap
	 * @return 
	 * @version 1.0
	 * @author 吴国成
	 * @created 2015年1月15日
	 */
	List<Map<String, Object>> getCustomerServiceStaffList(Map<String, Object> paramMap) throws ServiceException;

	int queryRoleUserIsUse(Map<String, Object> data) throws ServiceException;
	
	SysRole getRoleById(long id) throws ServiceException ;

	SysUser queryTheLeastBusyUserByRoleName(String roleName, String officeId,
			String processInstanceId, String taskDefinition)throws ServiceException;

	SysUser queryTheLeastBusyUserByHeadRoleName(String roleName,
			String officeId, String processInstanceId, String taskDefinition) throws ServiceException;

	Map<String, Object> queryTheUserWhoDidThisTask(String processInstanceId, String taskDef) throws ServiceException;

	SysUser getUserByRoleAndOfficeId(String superiorRoleName, Long long1) throws ServiceException;

	List<Map<String, Object>> getUserInfo(Map<String, Object> params) throws ServiceException;

}
