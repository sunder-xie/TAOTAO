package com.taotao.search.mq.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.search.bean.Item;

/**
 * 消息监听
 * @author xieshengrong
 */
public class ItemSearchHandler {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Autowired
	private ApiService apiService;
	@Autowired
	private HttpSolrServer httpSolrServer;

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
			if (null != itemId && StringUtils.isNotEmpty(type)
					&& (StringUtils.equals(type, "更新") || StringUtils.equals(type, "保存"))) {
				String url = "http://manage.taotao.com/rest/item/";
				String jsonData = apiService.doGet(url + itemId);
				if (StringUtils.isNotEmpty(jsonData)) {
					Item item = MAPPER.readValue(jsonData, Item.class);
					if (null != item) {
						httpSolrServer.addBean(item);
						httpSolrServer.commit();
					}
				}
			} else if (null != itemId && (StringUtils.equals(type, "删除"))) {
				httpSolrServer.deleteById(String.valueOf(itemId));
				httpSolrServer.commit();
			} else
				return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
