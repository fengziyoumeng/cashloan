package com.rongdu.cashloan.core.redis.impl;

import com.rongdu.cashloan.core.redis.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class ShardedJedisClientImpl extends AbstractClient implements ShardedJedisClient {
	private final Logger logger = Logger.getLogger(getClass().getName());

	public ShardedJedisClientImpl(String host, int port) {
		super(host, port);
	}

	public ShardedJedisClientImpl(String host, int port,String passwd) {
		super(host, port,passwd);
	}

	public void destory() {
		JedisPool pool = getJedisPool();
		if (null != pool)
			pool.destroy();
	}
	
	public <T> T execute(ShardedJedisAction<T> paramShardedJedisAction) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T execute(JedisAction<T> action) {
		JedisPool pool = getJedisPool();
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return action.doAction(jedis);
		} catch (JedisConnectionException e) {
			if (null != jedis)
				throw e;
		} finally {
			if (null != jedis) {
				try {
					pool.returnResource(jedis);
				} catch (Exception ex) {
					logger.warning("Can not return resource." + ex.getMessage());
				}
			}
		}
		return null;
	}

	/**
	 * 将序列化对象值value关联到key，如果key已经持有其他值，SET就覆写旧值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(final String key, final String value) {
		return this.execute(new JedisAction<String>() {
			public String doAction(Jedis jedis) {
				return jedis.set(key, value);
			}
		});
	}

	/**
	 * 保存序列化的对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String setObject(final String key, final Object value) {
		return this.execute(new JedisAction<String>() {
			public String doAction(Jedis jedis) {
				return jedis.set(key.getBytes(), SimpleUtils.serialize(value));
			}
		});
	}
	
	public String set(final byte[] key, final Object value) {
		return this.execute(new JedisAction<String>() {
			public String doAction(Jedis jedis) {
				return jedis.set(key, SimpleUtils.serialize(value));
			}
		});
	}

	private String setnx(final byte[] key, final Object value) {
		return this.execute(new JedisAction<String>() {
			public String doAction(Jedis jedis) {
				return String.valueOf(jedis.setnx(key, SimpleUtils.serialize(value)));
			}
		});
	}

	public boolean setnx(final String key, final Object value) {
		String returnValue = setnx(key.getBytes(),value);
		if("1".equals(returnValue)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 将序列化对象值value关联到key，如果key已经持有其他值，SET就覆写旧值，有效时间为expire秒
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public String set(final String key, final String value, final int expire) {
		return this.execute(new JedisAction<String>() {
			public String doAction(Jedis jedis) {
				String result = jedis.set(key, value);
				jedis.expire(key, expire);
				return result;
			}
		});
	}
	
	public String set(final byte[] key, final byte[] value, final int expire) {
		return this.execute(new JedisAction<String>() {
			public String doAction(Jedis jedis) {
				String result = jedis.set(key, value);
				jedis.expire(key, expire);
				return result;
			}
		});
	}

	/**
	 * 保存序列化的对象（带有效时间）
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public String setObject(final String key, final Object value, final int expire) {
		return this.execute(new JedisAction<String>() {
			public String doAction(Jedis jedis) {
				String result = jedis.set(key.getBytes(), SimpleUtils.serialize(value));
				jedis.expire(key.getBytes(), expire);
				return result;
			}
		});
	}

	/**
	 * 返回key所关联的序列化对象。如果key不存在则返回null。
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		return this.execute(new JedisAction<String>() {
			public String doAction(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}

	public Object getObject(final String key) {
		return this.execute(new JedisAction<Object>() {
			public Object doAction(Jedis jedis) {
				return SimpleUtils.unserialize(jedis.get(key.getBytes()));
			}
		});
	}
	
	public Object get(final byte[] key) {
		return this.execute(new JedisAction<Object>() {
			public Object doAction(Jedis jedis) {
				return SimpleUtils.unserialize(jedis.get(key));
			}
		});
	}

	/**
	 * 移除给定的key。如果key不存在，则忽略该命令
	 * 
	 * @param key
	 * @return
	 */
	public long del(final String key) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.del(key);
			}
		});
	}
	
	public long del(final byte[] key) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.del(key);
			}
		});
	}

	/**
	 * 删除缓存的对象
	 * 
	 * @param key
	 * @return
	 */
	public long delObject(final String key) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.del(key.getBytes());
			}
		});
	}

	/**
	 * 将哈希表key中的域field的值设为value。如果key不存在，一个新的哈希表被创建并进行HSET操作。如果域field已经存在于哈希表中，
	 * 旧值将被覆盖。
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public long hset(final String key, final String field, final String value) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.hset(key, field, value);
			}
		});
	}

	public long hsetObject(final String key, final String field, final Object value) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.hset(key.getBytes(), field.getBytes(), SimpleUtils.serialize(value));
			}
		});
	}

	/**
	 * 将哈希表key中的域field的值设为value。如果key不存在，一个新的哈希表被创建并进行HSET操作。如果域field已经存在于哈希表中，
	 * 旧值将被覆盖。 expire秒后失效
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @param expire
	 * @return
	 */
	public long hset(final String key, final String field, final String value, final int expire) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				Long result = jedis.hset(key, field, value);
				jedis.expire(key, expire);
				return result;
			}
		});
	}

	public long hsetObject(final String key, final String field, final Object value, final int expire) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				Long result = jedis.hset(key.getBytes(), field.getBytes(), SimpleUtils.serialize(value));
				jedis.expire(key.getBytes(), expire);
				return result;
			}
		});
	}

	/**
	 * 返回哈希表key中给定域field的值。
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(final String key, final String field) {
		return this.execute(new JedisAction<String>() {
			public String doAction(Jedis jedis) {
				return jedis.hget(key, field);
			}
		});
	}

	public Object hgetObject(final String key, final String field) {
		return this.execute(new JedisAction<Object>() {
			public Object doAction(Jedis jedis) {
				return SimpleUtils.unserialize(jedis.hget(key.getBytes(), field.getBytes()));
			}
		});
	}

	/**
	 * 返回哈希表 key 中，所有的域和值。
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(final String key) {
		return this.execute(new JedisAction<Map<String, String>>() {
			public Map<String, String> doAction(Jedis jedis) {
				return jedis.hgetAll(key);
			}
		});
	}

	public Map<String, Object> hgetAllObject(final String key) {
		return this.execute(new JedisAction<Map<String, Object>>() {
			public Map<String, Object> doAction(Jedis jedis) {
				Map<byte[], byte[]> result = jedis.hgetAll(key.getBytes());
				Map<String, Object> m = new HashMap<String, Object>();
				for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
					m.put(SimpleUtils.unserialize(entry.getKey()).toString(),
							SimpleUtils.unserialize(entry.getValue()));
				}
				return m;
			}
		});
	}

	/**
	 * 同时将多个域-值对设置到哈希表key中。如果key不存在，一个空哈希表被创建并执行HMSET操作。
	 * 
	 * @param key
	 * @param hash
	 * @return
	 */
	public String hmset(final String key, final Map<String, String> hash) {
		return this.execute(new JedisAction<String>() {
			public String doAction(Jedis jedis) {
				return jedis.hmset(key, hash);
			}
		});
	}

	/**
	 * 同时将多个域-值对设置到哈希表key中。如果key不存在，一个空哈希表被创建并执行HMSET操作。expire秒后失效
	 * 
	 * @param key
	 * @param hash
	 * @param expire
	 * @return
	 */
	public String hmset(final String key, final Map<String, String> hash, final int expire) {
		return this.execute(new JedisAction<String>() {
			public String doAction(Jedis jedis) {
				String result = jedis.hmset(key, hash);
				jedis.expire(key, expire);
				return result;
			}
		});
	}

	/**
	 * 删除哈希表key中的指定域，不存在的域将被忽略。
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public long hdel(final String key, final String... field) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.hdel(key, field);
			}
		});
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return this.execute(new JedisAction<Boolean>() {
			public Boolean doAction(Jedis jedis) {
				return jedis.exists(key);
			}
		});
	}

	/**
	 * 在指定Key所关联的List
	 * Value的头部插入参数中给出的所有Values。如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，
	 * 之后再将数据从链表的头部插入。如果该键的Value不是链表类型，该命令将返回相关的错误信息。
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lpush(final String key, final Object value) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.lpush(key.getBytes(), SimpleUtils.serialize(value));
			}
		});
	}


	public List<String> lrage(final String key, final long start, final long end){
		return this.execute(new JedisAction<List<String>>() {
			public  List<String> doAction(Jedis jedis) {
				return jedis.lrange(key,start,end);
			}
		});
	}


	/**
	 * 在指定Key所关联的List
	 * Value的尾部插入参数中给出的所有Values。如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，
	 * 之后再将数据从链表的尾部插入。如果该键的Value不是链表类型，该命令将返回相关的错误信息。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long rpush(final String key, final Object value) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.rpush(key.getBytes(), SimpleUtils.serialize(value));
			}
		});
	}

	/**
	 * 返回指定Key关联的链表中元素的数量，如果该Key不存在，则返回0。如果与该Key关联的Value的类型不是链表，则返回相关的错误信息。
	 * 
	 * @param key
	 * @return
	 */
	public Long llen(final String key) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.llen(key.getBytes());
			}
		});
	}

	/**
	 * 返回并弹出指定Key关联的链表中的第一个元素，即头部元素。如果该Key不存，返回null。
	 * 
	 * @param key
	 * @return
	 */
	public Object lpop(final String key) {
		return this.execute(new JedisAction<Object>() {
			public Object doAction(Jedis jedis) {
				return SimpleUtils.unserialize(jedis.lpop(key.getBytes()));
			}
		});
	}

	/**
	 * 返回并弹出指定Key关联的链表中的最后一个元素，即尾部元素。如果该Key不存，返回nil。
	 * 
	 * @param key
	 * @return
	 */
	public Object rpop(final String key) {
		return this.execute(new JedisAction<Object>() {
			public Object doAction(Jedis jedis) {
				return SimpleUtils.unserialize(jedis.rpop(key.getBytes()));
			}
		});
	}


	/**
	 * 返回并弹出指定Key关联的链表中从start到end范围到元素。
	 *
	 * @param key
	 * @return
	 */
	public List<String> lrange(final String key,final Long start,final Long end ) {
		return this.execute(new JedisAction<List<String>>() {
			public List<String> doAction(Jedis jedis) {
				return jedis.lrange(key,start,end);
			}
		});
	}

	/**
	 * 递增计时器
	 * 
	 * @param key
	 * @return
	 */
	public Long incr(final String key) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.incr(key);
			}
		});
	}

	/**
	 * 带有效期的递增计数器
	 * 
	 * @param key
	 * @param expire
	 * @return
	 */
	public Long incr(final String key, final int expire) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				Long value = jedis.incr(key);
				jedis.expire(key, expire);
				return value;
			}
		});
	}

	public Long incrBy(final String key, final long integer) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.incrBy(key, integer);
			}
		});
	}

	/**
	 * 递减计数器
	 * 
	 * @param key
	 * @return
	 */
	public Long decr(final String key) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.decr(key);
			}
		});
	}

	/**
	 * 带有效期的递减计数器
	 * 
	 * @param key
	 * @param expire
	 * @return
	 */
	public Long decr(final String key, final int expire) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				Long value = jedis.decr(key);
				jedis.expire(key, expire);
				return value;
			}
		});
	}

	public Long decrBy(final String key, final long integer) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.decrBy(key, integer);
			}
		});
	}

	public Long hincrBy(final String key, final String field, final long value) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.hincrBy(key, field, value);
			}
		});
	}
	
	public Set<String> keys(final String key) {
		return this.execute(new JedisAction<Set<String>>() {
			public Set<String> doAction(Jedis jedis) {
				return jedis.keys(key);
			}
		});
	}

	public Set<byte[]> bKeys(final String key) {
		return this.execute(new JedisAction<Set<byte[]>>() {
			public Set<byte[]> doAction(Jedis jedis) {
				return jedis.keys(key.getBytes());
			}
		});
	}

	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)
	 *
	 * @param key
	 * @return 当 key 不存在时，返回 -2 。当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以秒为单位，返回 key
	 *         的剩余生存时间。
	 * @date 2015-9-22
	 */
	public Long ttl(final String key) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.ttl(key);
			}
		});
	}

	/**
	 * 以秒为单位，对一个已经带有生存时间的 key 执行 EXPIRE 命令，新指定的生存时间会取代旧的生存时间 当 key 过期时(生存时间为 0
	 * )，它会被自动删除
	 *
	 * @param key
	 * @param expire
	 * @return 设置成功返回 1 。当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新
	 *         key 的生存时间)，返回 0 。
	 * @date 2015-9-22
	 */
	public Long expire(final String key, final int expire) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.expire(key, expire);
			}
		});
	}

	/**
	 * @Description: EXPIREAT 的作用和EXPIRE 类似，都用于为key 设置生存时间。 不同在于EXPIREAT
	 *               命令接受的时间参数是UNIX 时间戳(unix timestamp)
	 * @param key
	 * @param unixTimestamp
	 * @return
	 */
	public Long expireAt(final String key, final Long unixTimestamp) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.expireAt(key, unixTimestamp);
			}
		});
	}

	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。假如 key 不存在，则创建一个只包含
	 * member 元素作成员的集合。 当 key 不是集合类型时，返回一个错误。
	 *
	 * @param key
	 * @param members
	 * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。
	 * @author caijiajia
	 * @date 2015-10-12
	 */
	public Long sadd(final String key, final String... members) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.sadd(key, members);
			}
		});
	}

	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
	 *
	 * @param key
	 * @param members
	 * @return 被成功移除的元素的数量，不包括被忽略的元素。
	 * @author caijiajia
	 * @date 2015-10-12
	 */
	public Long srem(final String key, final String... members) {
		return this.execute(new JedisAction<Long>() {
			public Long doAction(Jedis jedis) {
				return jedis.srem(key, members);
			}
		});
	}

	/**
	 * 判断 member 元素是否集合 key 的成员
	 *
	 * @param key
	 * @param member
	 * @return 如果 member 元素是集合的成员 返回true，如果不是成员或KEY不存在返回false
	 * @author caijiajia
	 * @date 2015-10-12
	 */
	public Boolean sismember(final String key, final String member) {
		return this.execute(new JedisAction<Boolean>() {
			public Boolean doAction(Jedis jedis) {
				return jedis.sismember(key, member);
			}
		});
	}

}
