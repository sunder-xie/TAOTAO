package com.taotao.web.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.bean.Item;
import com.taotao.bean.SearchResult;
import com.taotao.common.service.ApiService;

@Service
public class SearchService {
	public static final Integer ROWS = 32;
	@Autowired
	private ApiService apiService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public SearchResult queryByKeyWords(String keyWords, Integer page) throws Exception {
		SearchResult searchResult = new SearchResult();
		String url = "http://search.taotao.com/search/";
		String jsonData = apiService.doGet(url + keyWords + "/" + page + "/" + ROWS);
		if (StringUtils.isNotEmpty(jsonData)) {
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			List<Item> list = null;
			if (data.isArray() && data.size() > 0) {
				list = MAPPER.readValue(data.traverse(),
						MAPPER.getTypeFactory().constructCollectionType(List.class, Item.class));
			}
			searchResult.setData(list);
			searchResult.setTotal(jsonNode.get("total").longValue());
		}
		return searchResult;
	}
}
