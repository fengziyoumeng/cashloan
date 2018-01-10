package com.rongdu.cashloan.core.redis.exception;

public class RedisClientException extends RuntimeException {
	private static final long serialVersionUID = -8276557316859207932L;

	public RedisClientException(String msg) {
		super(msg);
	}

	public RedisClientException(Throwable exception) {
		super(exception);
	}

	public RedisClientException(String mag, Exception exception) {
		super(mag, exception);
	}
}
