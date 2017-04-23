package com.taotao.web.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.EasyUIPage;
import com.taotao.common.service.ApiService;
import com.taotao.manage.pojo.Content;

@Service
public class IndexService {
	@Value("${TAOTAO_MANAGE_URL}")
	private String TAOTAO_MANAGE_URL;
	@Value("${INDEX_AD1_URL}")
	private String INDEX_AD1_URL;
	@Value("${INDEX_AD2_URL}")
	private String INDEX_AD2_URL;
//	@Autowired
//	private PropertieService propertieService;
	@Autowired
	private ApiService apiService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public String queryAd1() throws Exception {
		String url = TAOTAO_MANAGE_URL + INDEX_AD1_URL;
		// TODO 读取配置出错.待修复缺陷
		url = "http://manage.taotao.com/rest/content?categoryId=12&page=1&rows=6";
		// 调用远程接口,返回返回json数据
		String data = apiService.doGet(url);
		if (data == null) {
			return null;
		}
		// 解析json数据,把查询的json数据分装到list中
		EasyUIPage formatToList = EasyUIPage.formatToList(data, Content.class);
		@SuppressWarnings("unchecked")
		List<Content> rows = (List<Content>) formatToList.getRows();
		List<Map<String, Object>> adList = new ArrayList<Map<String, Object>>();
		for (Content row : rows) {
			// 需要保证数据的顺序,保证显示的数据格式
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("srcB", row.getPic());
			map.put("height", 240);
			map.put("alt", row.getTitle());
			map.put("width", 670);
			map.put("src", row.getPic());
			map.put("widthB", 550);
			map.put("href", row.getUrl());
			map.put("heightB", 240);
			adList.add(map);
		}
		if (StringUtils.isEmpty(adList))
			return null;
		return MAPPER.writeValueAsString(adList);
	}
	public String queryAd2() throws Exception {
		String url = TAOTAO_MANAGE_URL + INDEX_AD1_URL;
		// TODO 读取配置出错.待修复缺陷
		url = "http://manage.taotao.com/rest/content?categoryId=13&page=1&rows=1";
		// 调用远程接口,返回返回json数据
		String data = apiService.doGet(url);
		if (data == null) {
			return null;
		}
		// 解析json数据,把查询的json数据分装到list中
		// JsonNode nodes = MAPPER.readTree(data);
		// ArrayNode rows = (ArrayNode) nodes.get("rows");
		// if (rows == null)
		// return null;
		// 优化
		EasyUIPage formatToList = EasyUIPage.formatToList(data, Content.class);
		@SuppressWarnings("unchecked")
		List<Content> rows = (List<Content>) formatToList.getRows();
		List<Map<String, Object>> adList = new ArrayList<Map<String, Object>>();
		for (Content row : rows) {
			// 需要保证数据的顺序,保证显示的数据格式
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("width", 310);
			map.put("height", 70);
			map.put("src", row.getPic());
			map.put("href", row.getUrl());
			map.put("alt", row.getTitle());
			map.put("widthB", 310);
			map.put("heightB", 70);
			map.put("srcB", row.getPic2());
			adList.add(map);
		}
		if (StringUtils.isEmpty(adList))
			return null;
		return MAPPER.writeValueAsString(adList);
	}
}
