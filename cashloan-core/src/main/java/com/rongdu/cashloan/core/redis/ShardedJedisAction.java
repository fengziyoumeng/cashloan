package com.rongdu.cashloan.core.redis;

import redis.clients.jedis.ShardedJedis;

public interface ShardedJedisAction<T> {
	public T doAction(ShardedJedis apramShardedJedis);
}
