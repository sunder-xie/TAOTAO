package com.taotao.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {
	// 导入连接池
	@Autowired(required = false)
	private ShardedJedisPool shardedJedisPool;

	// 通过传入Function参数,返回T,
	private <T> T execute(Function<T, ShardedJedis> fun) {
		ShardedJedis shardedJedis = null;
		// 从连接池中获取到jedis分片对象
		try {
			shardedJedis = shardedJedisPool.getResource();
			return fun.callback(shardedJedis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (shardedJedis != null) {
				shardedJedis.close();
			}
		}
		return null;
	}
	/**
	 * 执行set操作
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(final String key, final String value) {
		return this.execute(new Function<String, ShardedJedis>() {
			@Override
			public String callback(ShardedJedis e) {
				return e.set(key, value);
			}
		});
	}
	/**
	 * 执行GET操作
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		return this.execute(new Function<String, ShardedJedis>() {
			@Override
			public String callback(ShardedJedis e) {
				return e.get(key);
			}
		});
	}
	/**
	 * 执行DEL操作
	 * @param key
	 * @return
	 */
	public Long del(final String key) {
		return this.execute(new Function<Long, ShardedJedis>() {
			@Override
			public Long callback(ShardedJedis e) {
				return e.del(key);
			}
		});
	}
	/**
	 * 设置生存时间，单位为秒
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(final String key, final Integer seconds) {
		return this.execute(new Function<Long, ShardedJedis>() {
			@Override
			public Long callback(ShardedJedis e) {
				return e.expire(key, seconds);
			}
		});
	}
	/**
	 * 执行set操作并且设置生存时间，单位为秒
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(final String key, final String value, final Integer seconds) {
		return this.execute(new Function<String, ShardedJedis>() {
			@Override
			public String callback(ShardedJedis e) {
				String str = e.set(key, value);
				e.expire(key, seconds);
				return str;
			}
		});
	}
}
