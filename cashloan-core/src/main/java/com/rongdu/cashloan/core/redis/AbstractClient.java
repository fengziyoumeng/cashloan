package com.rongdu.cashloan.core.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public abstract class AbstractClient {
	private String host;
	private int port;
	private String passwd;
	private JedisPool jedisPool;

	protected AbstractClient(String host, int port) {
		this.host = host;
		this.port = port;
		initJedisPool();
	}

	protected AbstractClient(String host, int port,String passwd) {
		this.host = host;
		this.port = port;
		this.passwd = passwd;
		initJedisPoolWithPasswd();
	}

	public JedisPool getJedisPool() {
		return this.jedisPool;
	}

	/**
	 * 无密码连接
	 */
	private void initJedisPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(10);
		config.setMaxTotal(10);
		config.setMaxWaitMillis(60000);
		this.jedisPool = new JedisPool(config, this.host, this.port,60000);
	}

	/**
	 * 密码认证连接
	 */
	private void initJedisPoolWithPasswd() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(10);
		config.setMaxTotal(10);
		config.setMaxWaitMillis(60000);
		this.jedisPool = new JedisPool(config, this.host, this.port,60000,this.passwd,0);
	}
}
