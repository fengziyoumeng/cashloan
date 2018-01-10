package com.rongdu.cashloan.core.redis.extension;

import java.util.List;
import java.util.Map;

public interface JedisTableClient {
	/**
	  * 获取table中的数据，返回List 
	  * @param tableName	表名
	  * @param indexKey		索引字段,如果多个字段，以逗号,隔开
	  * @param indexValue	索引字段值，对应indexKey字段，如果有多少，也以逗号,隔开
	  * @return 
	 */
	public List<Map<String, String>> getList(String tableName, String indexKey, String indexValue) throws Exception;

	/**
	  * 获取table中的数据，返回单个记录集 
	  * @param tableName	表名
	  * @param indexKey		索引字段,如果多个字段，以逗号,隔开
	  * @param indexValue	索引字段值，对应indexKey字段，如果有多少，也以逗号,隔开
	  * @return 
	 */
	public Map<String, String> getData(String tableName, String indexKey, String indexValue) throws Exception;

	/**
	  * 加载参数，如果按照主键加载参数，将检索数据存放在以tableName为key的hashMap区域。否则将检索数据放在以tableName_INDEX为key的区域。
	  * @param tableName	表名
	  * @param tableRecords	要保存到redis中的结果集，此结果集的获取由调用者提供
	 */
	public void loadParam(String tableName, List<Map<String, Object>> tableRecords) throws Exception;

	
	/**
	  * 加载参数，如果按照主键加载参数，将检索数据存放在以tableName为key的hashMap区域。否则将检索数据放在以tableName_INDEX为key的区域。
	  * @param tableName	表名
	  * @param indexKey 	索引字段,如果多个字段，以逗号,隔开
	  * @param indexValue	索引字段值，对应indexKey字段，如果有多少，也以逗号,隔开
	  * @param tableRecords	要保存到redis中的结果集，此结果集的获取由调用者提供
	 */
	public void loadParam(String tableName, String indexKey, String indexValue, List<Map<String, Object>> tableRecords) throws Exception;

	/**
	  * 加载参数，如果按照主键加载参数，将检索数据存放在以tableName为key的hashMap区域。否则将检索数据放在以tableName_INDEX为key的区域。
	  * @param tableName	表名
	  * @param inParam		索引字段的key-value集
	  * @param tableRecords	要保存到redis中的结果集，此结果集的获取由调用者提供
	 */
	public void loadParam(String tableName, Map<String, Object> inParam, List<Map<String, Object>> tableRecords) throws Exception;
	

	/**
	  * 获取对应表名缓存的所有结果集
	  * @param tableName	表名
	  * @return 所有结果记录
	 */
	public List<Map<String, String>> getAll(String tableName) throws Exception;
	
	/**
	 * 清空对应缓存表的所有记录
	  * @param tableName	表名
	 */
	public void clearTable(String tableName) throws Exception;
}
