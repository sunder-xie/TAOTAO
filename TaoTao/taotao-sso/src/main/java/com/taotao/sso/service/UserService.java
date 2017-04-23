package com.taotao.sso.service;

import java.sql.Timestamp;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Autowired
	private RedisService redis;
	public static final Integer seconds = 60 * 30;
	public static final String JSESSIONID = "JSESSIONID";

	/**
	 * 注册信息校验
	 * @param param
	 * @param type
	 * @return
	 */
	public Boolean check(String param, String type) {
		User record = new User();
		if ("1".equalsIgnoreCase(type)) {
			record.setUsername(param);
		} else if ("2".equalsIgnoreCase(type)) {
			record.setPhone(param);
		} else {
			record.setPhone(param);
		}
		User user = userMapper.selectOne(record);
		return null != user;
	}
	/**
	 * 用户登录
	 * @param form
	 * @return
	 */
	public String logon(User form) {
		String token = null;
		if (StringUtils.isNotEmpty(form.getUsername()) && StringUtils.isNotEmpty(form.getPassword())) {
			form.setPassword(DigestUtils.md5Hex(form.getPassword()));
			User user = userMapper.selectOne(form);
			try {
				if (null != user) {
					token = "token_" + DigestUtils.md5Hex(JSESSIONID + form.getUsername() + form.getPassword());
					redis.set(token, MAPPER.writeValueAsString(user), seconds);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return token;
	}
	/**
	 * 用户注册
	 * @param form
	 * @return
	 */
	public Boolean doRegister(User form) {
		Integer result = 0;
		if (StringUtils.isNotEmpty(form.getUsername()) && StringUtils.isNotEmpty(form.getPassword())) {
			form.setPassword(DigestUtils.md5Hex(form.getPassword()));
			// 通过用户名校验 ,如果用户存在,则注册失败
			if (null == userMapper.selectOne(form)) {
				form.setId(null);
				form.setCreated(new Timestamp(System.currentTimeMillis()));
				form.setUpdated(form.getCreated());
				result = userMapper.insert(form);
			}
		}
		return 1 == result;
	}
	public User queryUserByToken(String token) {
		// 查询缓存
		try {
			String cacheData = redis.get(token);
			if (StringUtils.isNotEmpty(cacheData)) {
				// 刷新缓存时间
				redis.expire( token, seconds);
				return MAPPER.readValue(cacheData, User.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 退出登录
	 */
	public void logout(String token) {
		redis.del("token_" + token);
	}
}
