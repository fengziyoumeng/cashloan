package com.rongdu.cashloan.core.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ShardedJedisClient {
	public void destory();

	public <T> T execute(ShardedJedisAction<T> paramShardedJedisAction);

	public <T> T execute(JedisAction<T> jedisAction);

	/**
	 * 将序列化对象值value关联到key，如果key已经持有其他值，SET就覆写旧值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(final String key, final String value);
	
	public String set(final byte[] key, final Object value);

	/**
	 * 保存序列化的对象
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public String setObject(final String key, final Object value);

	/**
	 * 将序列化对象值value关联到key，如果key已经持有其他值，SET就覆写旧值，有效时间为expire秒
	 *
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public String set(final String key, final String value, final int expire);

	public String set(final byte[] key, final byte[] value, final int expire);

	public boolean setnx(final String key, final Object value);

	/**
	 * 保存序列化的对象（带有效时间）
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public String setObject(final String key, final Object value, final int expire);

	/**
	 * 返回key所关联的序列化对象。如果key不存在则返回null。
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key);

	public Object getObject(final String key);
	
	public Object get(final byte[] key);

	/**
	 * 移除给定的key。如果key不存在，则忽略该命令
	 * 
	 * @param key
	 * @return
	 */
	public long del(final String key);
	
	public long del(final byte[] key);

	/**
	 * 删除缓存的对象
	 * 
	 * @param key
	 * @return
	 */
	public long delObject(final String key);

	/**
	 * 将哈希表key中的域field的值设为value。如果key不存在，一个新的哈希表被创建并进行HSET操作。如果域field已经存在于哈希表中，
	 * 旧值将被覆盖。
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public long hset(final String key, final String field, final String value);

	public long hsetObject(final String key, final String field, final Object value);

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
	public long hset(final String key, final String field, final String value, final int expire);

	public long hsetObject(final String key, final String field, final Object value, final int expire);

	/**
	 * 返回哈希表key中给定域field的值。
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(final String key, final String field);

	public Object hgetObject(final String key, final String field);

	/**
	 * 返回哈希表 key 中，所有的域和值。
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(final String key);

	public Map<String, Object> hgetAllObject(final String key);

	/**
	 * 同时将多个域-值对设置到哈希表key中。如果key不存在，一个空哈希表被创建并执行HMSET操作。
	 * 
	 * @param key
	 * @param hash
	 * @return
	 */
	public String hmset(final String key, final Map<String, String> hash);

	/**
	 * 同时将多个域-值对设置到哈希表key中。如果key不存在，一个空哈希表被创建并执行HMSET操作。expire秒后失效
	 * 
	 * @param key
	 * @param hash
	 * @param expire
	 * @return
	 */
	public String hmset(final String key, final Map<String, String> hash, final int expire);

	/**
	 * 删除哈希表key中的指定域，不存在的域将被忽略。
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public long hdel(final String key, final String... field);

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key);

	/**
	 * 在指定Key所关联的List
	 * Value的头部插入参数中给出的所有Values。如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，
	 * 之后再将数据从链表的头部插入。如果该键的Value不是链表类型，该命令将返回相关的错误信息。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lpush(final String key, final Object value);

	/**
	 * 在指定Key所关联的List
	 * Value的尾部插入参数中给出的所有Values。如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，
	 * 之后再将数据从链表的尾部插入。如果该键的Value不是链表类型，该命令将返回相关的错误信息。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long rpush(final String key, final Object value);

	/**
	 * 返回指定Key关联的链表中元素的数量，如果该Key不存在，则返回0。如果与该Key关联的Value的类型不是链表，则返回相关的错误信息。
	 * 
	 * @param key
	 * @return
	 */
	public Long llen(final String key);

	/**
	 * 返回并弹出指定Key关联的链表中的第一个元素，即头部元素。如果该Key不存，返回null。
	 * 
	 * @param key
	 * @return
	 */
	public Object lpop(final String key);

	/**
	 * 返回并弹出指定Key关联的链表中的最后一个元素，即尾部元素。如果该Key不存，返回nil。
	 * 
	 * @param key
	 * @return
	 */
	public Object rpop(final String key);


	/**
	 * 返回指定Key关联的链表中start到end范围内的元素
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(final String key, final Long start, final Long end );

	/**
	 * 递增计时器
	 * 
	 * @param key
	 * @return
	 */
	public Long incr(final String key);

	/**
	 * 带有效期的递增计数器
	 * 
	 * @param key
	 * @param expire
	 * @return
	 */
	public Long incr(final String key, final int expire);

	/**
	 * @Description: 递增指定额度
	 * @param key
	 * @param integer
	 * @return
	 */
	public Long incrBy(final String key, final long integer);

	/**
	 * 递减计数器
	 * 
	 * @param key
	 * @return
	 */
	public Long decr(final String key);

	/**
	 * 带有效期的递减计数器
	 * 
	 * @param key
	 * @param expire
	 * @return
	 */
	public Long decr(final String key, final int expire);

	/**
	 * @Description: 递减指定额度
	 * @param key
	 * @param integer
	 * @return
	 */
	public Long decrBy(final String key, final long integer);

	/**
	 * 为哈希表key 中的域field 的值加上增量increment。增量也可以为负数，相当于对给定域进行减法操作。 如果key
	 * 不存在，一个新的哈希表被创建并执行HINCRBY 命令。 如果域field 不存在，那么在执行命令前，域的值被初始化为0 。
	 * 对一个储存字符串值的域field 执行HINCRBY 命令将造成一个错误。
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hincrBy(String key, String field, long value);

	public Set<String> keys(final String key);
	
	public Set<byte[]> bKeys(final String key);
	
	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)
	 *
	 * @param key
	 * @return 当 key 不存在时，返回 -2 。当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以秒为单位，返回 key
	 *         的剩余生存时间。
	 */
	public Long ttl(final String key);

	/**
	 * 以秒为单位，对一个已经带有生存时间的 key 执行 EXPIRE 命令，新指定的生存时间会取代旧的生存时间 当 key 过期时(生存时间为 0
	 * )，它会被自动删除
	 *
	 * @param key
	 * @param expire
	 * @return 设置成功返回 1 。当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新
	 *         key 的生存时间)，返回 0 。
	 */
	public Long expire(final String key, final int expire);

	/**
	 * @Description: EXPIREAT 的作用和EXPIRE 类似，都用于为key 设置生存时间。 不同在于EXPIREAT
	 *               命令接受的时间参数是UNIX 时间戳(unix timestamp)
	 * @param key
	 * @param unixTimestamp
	 * @return
	 */
	public Long expireAt(final String key, final Long unixTimestamp);

	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。假如 key 不存在，则创建一个只包含
	 * member 元素作成员的集合。 当 key 不是集合类型时，返回一个错误。
	 *
	 * @param key
	 * @param members
	 * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。
	 */
	public Long sadd(final String key, final String... members);

	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
	 *
	 * @param key
	 * @param members
	 * @return 被成功移除的元素的数量，不包括被忽略的元素。
	 */
	public Long srem(final String key, final String... members);

	/**
	 * 判断 member 元素是否集合 key 的成员
	 *
	 * @param key
	 * @param member
	 * @return 如果 member 元素是集合的成员 返回true，如果不是成员或KEY不存在返回false
	 */
	public Boolean sismember(final String key, final String member);
}
