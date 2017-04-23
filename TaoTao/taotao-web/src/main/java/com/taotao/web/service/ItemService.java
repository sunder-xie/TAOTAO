package com.taotao.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.service.ApiService;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;

@Service
public class ItemService {
	@Autowired
	private ApiService apiService;
	@Autowired
	private RedisService redis;
	private static final Integer seconds = 60 * 60 * 24 * 100;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	public static final String TAOTAO_WEB_ITEM_DESC = "TAOTAO_WEB_ITEM_DESC";
	public static final String TAOTAO_WEB_ITEM = "TAOTAO_WEB_ITEM";
	public static final String TAOTAO_WEB_ITEM_PARAM = "TAOTAO_WEB_ITEM_PARAM";

	public ItemDesc queryItemDescByItemId(Long itemId) {
		// 添加缓存
		try {
			String cacheData = redis.get(TAOTAO_WEB_ITEM_DESC + itemId);
			if (StringUtils.isNotEmpty(cacheData)) {
				return MAPPER.readValue(cacheData, ItemDesc.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "http://manage.taotao.com/rest/item/desc/" + itemId;
		try {
			String jsonData = apiService.doGet(url);
			if (StringUtils.isNotEmpty(jsonData)) {
				// 添加缓存
				try {
					redis.set(TAOTAO_WEB_ITEM_DESC + itemId, jsonData, seconds);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return MAPPER.readValue(jsonData, ItemDesc.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Item queryByItemId(Long itemId) {
		// 添加缓存
		try {
			String cacheData = redis.get(TAOTAO_WEB_ITEM + itemId);
			if (StringUtils.isNotEmpty(cacheData)) {
				return MAPPER.readValue(cacheData, Item.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO 待优化
		String url = "http://manage.taotao.com/rest/item/" + itemId;
		try {
			String jsonData = apiService.doGet(url);
			if (StringUtils.isNotEmpty(jsonData)) {
				// 添加缓存
				try {
					redis.set(TAOTAO_WEB_ITEM + itemId, jsonData, seconds);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return MAPPER.readValue(jsonData, Item.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String queryItemParamByItemId(Long itemId) {
		// 添加缓存
		try {
			String cacheData = redis.get(TAOTAO_WEB_ITEM_PARAM + itemId);
			if (StringUtils.isNotEmpty(cacheData)) {
				return cacheData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String url = "http://manage.taotao.com/rest/item/param/item/" + itemId;
			String jsonData = this.apiService.doGet(url);
			if (StringUtils.isEmpty(jsonData)) {
				return null;
			}
			// 解析JSON
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			ArrayNode paramData = (ArrayNode) MAPPER.readTree(jsonNode.get("paramData").asText());
			StringBuilder sb = new StringBuilder();
			sb.append(
					"<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"><tbody>");
			for (JsonNode param : paramData) {
				sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">" + param.get("group").asText() + "</th></tr>");
				ArrayNode params = (ArrayNode) param.get("params");
				for (JsonNode p : params) {
					sb.append("<tr><td class=\"tdTitle\">" + p.get("k").asText() + "</td><td>" + p.get("v").asText()
							+ "</td></tr>");
				}
			}
			sb.append("</tbody></table>");
			// 添加缓存
			try {
				redis.set(TAOTAO_WEB_ITEM_PARAM + itemId, sb.toString(), seconds);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
