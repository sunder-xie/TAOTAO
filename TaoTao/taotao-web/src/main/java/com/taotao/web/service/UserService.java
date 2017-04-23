package com.taotao.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.bean.User;
import com.taotao.common.service.RedisService;

@Service
public class UserService {
	@Autowired
	private RedisService redis;
	public static final ObjectMapper MAPPER = new ObjectMapper();

	public User queryByToken(String token) {
		User user = null;
		try {
			String jsonData = redis.get( token);
			if (StringUtils.isNotEmpty(jsonData)) {
				user = MAPPER.readValue(jsonData, User.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
