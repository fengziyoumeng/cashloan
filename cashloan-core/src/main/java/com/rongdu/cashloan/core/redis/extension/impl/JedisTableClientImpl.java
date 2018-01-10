package com.rongdu.cashloan.core.redis.extension.impl;


import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.core.redis.SimpleUtils;
import com.rongdu.cashloan.core.redis.exception.RedisClientException;
import com.rongdu.cashloan.core.redis.extension.JedisTableClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.*;

public class JedisTableClientImpl implements JedisTableClient {
	private ShardedJedisClient redisClient;
	private final static Map<String, Map<String, String>> tableInfos = new HashMap<String, Map<String, String>>();

	public JedisTableClientImpl(String configPath, ShardedJedisClient redisClient) {
		this.redisClient = redisClient;
		XMLParser.parse(configPath);
	}

	public List<Map<String, String>> getList(String tableName, String indexKey, String indexValue) throws Exception {
		String keys[] = indexKey.split(",");
		String values[] = indexValue.split(",");
		if (tableName == null || "".equals(tableName)) {
			throw new RedisClientException("从缓存中获取参数时，tableName不能为空值或者null");
		}

		if (keys.length != values.length) {
			throw new RedisClientException("从缓存中获取参数时，key与value数量不一致");
		}

		TreeMap<String, String> inParam = new TreeMap<String, String>();
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == null || "".equals(keys[i]) || values[i] == null) {
				throw new RedisClientException("从缓存中获取参数时，keys或者values中不能包含空值或者null");
			}
			inParam.put(keys[i], values[i]);

		}
		return getList(tableName, inParam);
	}

	public List<Map<String, String>> getList(String tableName, TreeMap<String, String> inParam) {
		boolean ifPk = true;
		try {
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();

			tableName = tableName.toUpperCase();
			Map<String, String> tableInfo = tableInfos.get(tableName);

			if (tableInfo == null) {
				throw new RedisClientException("没有配置" + tableName + "表的缓存信息");
			}

			String vkey = tableName;
			String vvalue = "";
			for (Map.Entry<String, String> entry : inParam.entrySet()) {
				vkey = vkey.concat("_").concat(entry.getKey().trim());
				vvalue = vvalue.concat("_").concat(entry.getValue().trim());
			}
			// 判断查询的范围
			if (!tableInfo.get("TABLE_PK_STR").equals(vkey)) {
				ifPk = false;
			}
			vkey = vkey.concat(vvalue);
			//
			String cacheKey = tableName;
			if (!ifPk) {
				cacheKey = cacheKey.concat("_INDEX");
			}
			String tableIndex = redisClient.hget(cacheKey, vkey);
			if (tableIndex != null) {
				if ("".equals(tableIndex)) {
					return null;
				} else {
					String[] keys = tableIndex.split("\\$@\\$");
					for (String key : keys) {
						Map<String, String> data = redisClient.hgetAll(key);
						if (data != null) {
							result.add(data);
						}
					}
					return result;
				}
			}
		} catch (Exception e) {
			throw new RedisClientException(e);
		}
		return null;
	}

	public Map<String, String> getData(String tableName, String indexKey, String indexValue) throws Exception {
		String keys[] = indexKey.split(",");
		String values[] = indexValue.split(",");
		if (tableName == null || "".equals(tableName)) {
			throw new RedisClientException("从缓存中获取参数时，tableName不能为空值或者null");
		}

		if (keys.length != values.length) {
			throw new RedisClientException("从缓存中获取参数时，key与value数量不一致");
		}

		TreeMap<String, String> inParam = new TreeMap<String, String>();
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == null || "".equals(keys[i]) || values[i] == null) {
				throw new RedisClientException("从缓存中获取参数时，keys或者values中不能包含空值或者null");
			}
			inParam.put(keys[i], values[i]);
		}
		return getData(tableName, inParam);
	}

	public Map<String, String> getData(String tableName, TreeMap<String, String> inParam) {
		boolean ifPk = true;
		try {
			tableName = tableName.toUpperCase();
			Map<String, String> tableInfo = tableInfos.get(tableName);

			if (tableInfo == null) {
				throw new RedisClientException("没有配置" + tableName + "表的缓存信息");
			}

			String vkey = tableName;
			String vvalue = "";
			for (Map.Entry<String, String> entry : inParam.entrySet()) {
				vkey = vkey.concat("_").concat(entry.getKey().trim());
				vvalue = vvalue.concat("_").concat(entry.getValue().trim());
			}
			if (!tableInfo.get("TABLE_PK_STR").equals(vkey)) {
				ifPk = false;
			}
			vkey = vkey.concat(vvalue);
			//
			String cacheKey = tableName;
			if (!ifPk) {
				cacheKey = cacheKey.concat("_INDEX");
			}
			String tableIndex = redisClient.hget(cacheKey, vkey);
			if (tableIndex != null) {
				if ("".equals(tableIndex)) {
					return null;
				} else {
					String[] keys = tableIndex.split("\\$@\\$");
					for (String key : keys) {
						Map<String, String> data = redisClient.hgetAll(key);
						if (data != null) {
							return data;
						}
					}
				}
			}

		} catch (Exception e) {
			throw new RedisClientException(e);
		}
		return null;
	}

	public void loadParam(String tableName, String indexKey, String indexValue, List<Map<String, Object>> tableRecords)
			throws Exception {
		String keys[] = indexKey.split(",");
		String values[] = indexValue.split(",");
		if (tableName == null || "".equals(tableName)) {
			throw new RedisClientException("tableName不能为空值或者null");
		}

		if (keys.length != values.length) {
			throw new RedisClientException("key与value数量不一致");
		}

		TreeMap<String, String> inParam = new TreeMap<String, String>();
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == null || "".equals(keys[i]) || values[i] == null) {
				throw new RedisClientException("keys或者values中不能包含空值或者null");
			}
			inParam.put(keys[i], values[i]);
		}

		if (tableRecords == null || tableRecords.size() == 0) {
			throw new RedisClientException("需要缓存的记录数不能为空.");
		}
		//
		loadParam2(tableName, inParam, tableRecords);
	}

	public void loadParam(String tableName, Map<String, Object> params, List<Map<String, Object>> tableRecords)
			throws Exception {
		TreeMap<String, String> inParam = new TreeMap<String, String>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String vkey = entry.getKey();
			Object vvalue = entry.getValue();
			if (vkey == null || "".equals(vkey) || vvalue == null) {
				throw new RedisClientException("keys或者values中不能包含空值或者null");
			}
			inParam.put(vkey.trim(), String.valueOf(vvalue).trim());
		}
		if (tableRecords == null || tableRecords.size() == 0) {
			throw new RedisClientException("需要缓存的记录数不能为空.");
		}
		//
		loadParam2(tableName, inParam, tableRecords);
	}
	
	public void loadParam(String tableName, List<Map<String, Object>> tableRecords) throws Exception {
		loadParam2(tableName, null, tableRecords);
	}

	private void loadParam2(String tableName, TreeMap<String, String> inParam, List<Map<String, Object>> tableRecords)
			throws Exception {
		boolean ifPk = true;
		try {
			String xTable = tableName.toUpperCase();
			Map<String, String> tableInfo = tableInfos.get(xTable);
			if (tableInfo == null) {
				throw new RedisClientException("没有配置" + xTable + "表的缓存信息");
			} else {
				String[] txpks = tableInfo.get("TABLE_PK").split(",");

				String cacheValue = "";
				for (int i = 0; i < tableRecords.size(); i++) {
					Map<String, Object> param = (Map<String, Object>) tableRecords.get(i);

					String pkValue = "";
					for (int j = 0; j < txpks.length; j++) {
						pkValue = pkValue.concat("_").concat(SimpleUtils.getStr(param, txpks[j].trim()).trim());
					}
					String pkKey = tableInfo.get("TABLE_PK_STR").concat(pkValue);

					if (i == 0) {
						cacheValue = pkKey;
					} else {
						cacheValue = cacheValue.concat("$@$").concat(pkKey);
					}
					if (ifPk || !redisClient.exists(pkKey)) {
						Map<String, String> strParam = new HashMap<String, String>();
						for (Map.Entry<String, Object> entry : param.entrySet()) {
							strParam.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
						}
						redisClient.del(pkKey);
						redisClient.hmset(pkKey, strParam);
						redisClient.hset(xTable, pkKey, pkKey);
					}
				}

				if(inParam != null) {
					String vkey = xTable;
					String vvalue = "";
					for (Map.Entry<String, String> entry : inParam.entrySet()) {
						vkey = vkey.concat("_").concat(entry.getKey().trim());
						vvalue = vvalue.concat("_").concat(entry.getValue().trim());
					}
					// 如果查询的字段不是表的全主键，则查询索引
					if (!tableInfo.get("TABLE_PK_STR").equals(vkey)) {
						ifPk = false;
					}
					vkey = vkey.concat(vvalue);

					if (ifPk) {
						redisClient.hset(xTable, vkey, cacheValue);
					} else {
						redisClient.hset(xTable.concat("_INDEX"), vkey, cacheValue);
					}
				}
			}
		} catch (Exception e) {
			throw new RedisClientException(e);
		}
	}

	public List<Map<String, String>> getAll(String tableName) {
		try {
			tableName = tableName.toUpperCase();
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
			Map<String, String> tableDatas = redisClient.hgetAll(tableName);
			if (tableDatas == null) {
				return result;
			}

			for (Map.Entry<String, String> entry : tableDatas.entrySet()) {
				if (!"".equals(entry.getValue())) {
					result.add(redisClient.hgetAll(entry.getKey()));
				}
			}
			return result;
		} catch (Exception e) {
			throw new RedisClientException(e);
		}
	}

	public void clearTable(String tableName) {
		String xTable = tableName.toUpperCase();

		try {
			if (tableInfos.containsKey(xTable)) {
				Map<String, String> m = redisClient.hgetAll(xTable);
				if (m != null) {
					for (Map.Entry<String, String> entry : m.entrySet()) {
						String[] keys = entry.getValue().split("\\$@\\$");
						for (String key : keys) {
							redisClient.del(key);
						}
					}
					redisClient.del(xTable);
				}
				redisClient.del(xTable.concat("_INDEX"));
			}
		} catch (Exception e) {
			throw new RedisClientException(e);
		}
	}

	private static class XMLParser {
		private static XPath path;
		private static Document doc;

		private static String getString(Object node, String expression) throws XPathExpressionException {
			return ((String) path.evaluate(expression, node, XPathConstants.STRING));
		}

		private static NodeList getList(Object node, String expression) throws XPathExpressionException {
			return ((NodeList) path.evaluate(expression, node, XPathConstants.NODESET));
		}

		private static Node getNode(Object node, String expression) throws XPathExpressionException {
			return ((Node) path.evaluate(expression, node, XPathConstants.NODE));
		}

		public static void parse(String redisConfig) {
			try {
				DocumentBuilder dbd = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				doc = dbd.parse(XMLParser.class.getResourceAsStream(redisConfig));
				path = XPathFactory.newInstance().newXPath();
				Node rootN = getNode(doc, "paramtables");
				if (null == rootN) {
					throw new RedisClientException("Invalid xml format, can't find <config> root node!");
				}

				NodeList nodeList = getList(rootN, "table");
				if (null == nodeList || nodeList.getLength() == 0) {
					throw new RedisClientException("Invalid xml format, can't find <table>!");
				}
				for (int i = 0; i < nodeList.getLength(); ++i) {
					Node node = nodeList.item(i);
					String tableName = getString(node, "@tableName");
					String primaryKeys = getString(node, "@primaryKeys");

					if (null == tableName || "".equals(tableName.trim())) {
						throw new RedisClientException(
								"Invalid xml format, <table> node should have tableName attribute.");
					}

					if (null == primaryKeys || "".equals(primaryKeys.trim())) {
						throw new RedisClientException(
								"Invalid xml format, <table> node should have primaryKeys attribute.");
					}

					String[] txpks = primaryKeys.toUpperCase().split(",");
					Arrays.sort(txpks);
					String xTable = tableName.toUpperCase();

					Map<String, String> tableInfo = new HashMap<String, String>();
					tableInfo.put("TABLE_NAME", xTable);
					tableInfo.put("TABLE_PK", SimpleUtils.join(txpks, ","));
					String tablePkStr = xTable;
					for (String txpk : txpks) {
						tablePkStr = tablePkStr.concat("_").concat(txpk.trim());
					}
					tableInfo.put("TABLE_PK_STR", tablePkStr);
					tableInfos.put(xTable, tableInfo);
				}
			} catch (IOException e) {
				throw new RedisClientException("IOException!", e);
			} catch (Exception ex) {
				throw new RedisClientException("Fail to parse redis configure file.", ex);
			}
		}
	}
}
