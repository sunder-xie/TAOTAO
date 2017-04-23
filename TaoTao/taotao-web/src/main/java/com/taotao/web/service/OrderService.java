package com.taotao.web.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.bean.Order;
import com.taotao.bean.User;
import com.taotao.common.httpclient.HttpResult;
import com.taotao.common.service.ApiService;

@Service
public class OrderService {
	@Autowired
	private ApiService apiService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 创建订单
	 * @param order
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String create(Order order, User user) {
		// 创建订单
		order.setBuyerNick(user.getUsername());
		order.setUserId(user.getId());
		order.setPostFee("1");
		order.setCreateTime(new Date(System.currentTimeMillis()));
		try {
			String url = "http://order.taotao.com/order/create";
			HttpResult httpResult = apiService.doPostJson(url, MAPPER.writeValueAsString(order));
			if (null != httpResult) {
				if (httpResult.getCode().intValue() == 200) {
					String jsonData = httpResult.getData();
					JsonNode jsonNode = MAPPER.readTree(jsonData);
					if (jsonNode.get("status").intValue() == 200) {
						return jsonNode.get("data").asText();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据订单id调用接口查询
	 * @param orderId
	 * @return
	 */
	public Order queryOrderById(String orderId) {
		try {
			String url = "http://order.taotao.com/order/query/";
			String jsonData = apiService.doGet(url + orderId + ".html");
			if (StringUtils.isNotEmpty(jsonData)) {
				return MAPPER.readValue(jsonData, Order.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
