package com.taotao.web.mq.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.web.service.ItemService;

/**
 * 消息监听
 * @author xieshengrong
 */
public class ItemWebHandler {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Autowired
	private RedisService redis;

	/**
	 * 消息监听方法
	 * @param msg
	 * @throws Exception
	 * @throws JsonProcessingException
	 */
	public void excute(String msg) {
		try {
			JsonNode jsonNode = MAPPER.readTree(msg);
			String type = null != jsonNode.get("type") ? jsonNode.get("type").asText() : null;
			Long itemId = null != jsonNode.get("itemId") ? jsonNode.get("itemId").asLong() : null;
			if (StringUtils.isNotEmpty(type) && (StringUtils.equals(type, "更新") || StringUtils.equals(type, "删除"))) {
				redis.del(ItemService.TAOTAO_WEB_ITEM + itemId);
				redis.del(ItemService.TAOTAO_WEB_ITEM_DESC + itemId);
				redis.del(ItemService.TAOTAO_WEB_ITEM_PARAM + itemId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
