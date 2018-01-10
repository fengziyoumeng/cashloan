package com.rongdu.cashloan.core.redis;

import redis.clients.jedis.Jedis;

public interface JedisAction<T> {
	public T doAction(Jedis jedis);
}
