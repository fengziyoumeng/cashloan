package com.rongdu.cashloan.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;
import com.rongdu.cashloan.system.domain.SysDictDetail;

/**
 * DAO接口
 * @author wgc
 * @version 1.0
 * @since 2014-11-03
 */
@RDBatisDao
public interface SysDictDetailMapper extends BaseMapper<SysDictDetail,Long> {

	
	
	int deleteByPrimary(Long id);
	
	
	Long getCount(Map<String, Object> data);

	/**
	 * @description
	 * @return
	 * @author liyc
	 * @return List<Map<String,Object>>
	 * @since  1.0.0
	*/
	List<Map<String, Object>> queryAllDic();

	List<Map<String, Object>> getPageListMap(Map<String, Object> data);

	/**
	 * 根据父类ID获取详细值
	 * @param parentId
	 * @return
	 */
	List<String> getItemVlueByParentId(String parentId);
	
	/**
	 * 查询数据字典详情
	 * @param code
	 * @param parentName
	 * @return
	 */
	SysDictDetail findDetail(@Param("code")String code, @Param("parentName")String parentName);

	/**
	 * 查询字典详情列表
	 * @param parentName
	 * @return
	 */
	List<SysDictDetail> findAllDetail(@Param("parentName")String parentName);

	/**
	 * 根据父级类型名称查询字典列表
	 * @param parentName
	 * @return
	 */
	List<Map<String, Object>> queryAllDicByParentName(@Param("parentName")String parentName);

	/**
	 * 新增时查询额度类型名称
	 * @param data
	 * @return
	 */
	List<SysDictDetail> listByTypeCode(Map<String, Object> data);

	/**
	 * 修改时查询额度类型名称
	 * @param data
	 * @return
	 */
	List<SysDictDetail> listUpdateCode(Map<String, Object> data);

	/**
	 * 通过pTag和parentName获取数据
	 * @param param
	 * @return
	 */
	List<String> listValue(Map<String, Object> param);

	List<Map<String,Object>> queryItemValue(Map<String, Object> param);
}
