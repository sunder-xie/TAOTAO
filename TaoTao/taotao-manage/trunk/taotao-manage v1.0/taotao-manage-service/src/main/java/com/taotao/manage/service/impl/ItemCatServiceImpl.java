package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatData;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

/**
 * 商品类目
 * @author xieshengrong
 */
@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {
	@Autowired
	private RedisService redis;
	private static final String TAOTAO_MANAGE_ITEM_CAT_ALL = "TAOTAO_MANAGE_ITEM_CAT_ALL";
	private static final Integer seconds = 60 * 60 * 24 * 100;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 前台系统查询,itemCat列表
	 */
	public ItemCatResult queryItemCatList() {
		ItemCatResult result = new ItemCatResult();
		// 先查询缓存,命中缓存直接返回
		// TODO 缓存添加一定不能影响原本逻辑,,,需要在更新itemCat的业务逻辑中进行刷出缓存
		String cacheData = redis.get(TAOTAO_MANAGE_ITEM_CAT_ALL);
		if (StringUtils.isNotEmpty(cacheData)) {
			try {
				return MAPPER.readValue(cacheData, ItemCatResult.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<ItemCat> cats = super.queryAll();
		// 保存商品类目
		// 遍历商品,按照父节点id存入商品map
		// 全部查出，并且在内存中生成树形结构
		// 转为map存储，key为父节点ID，value为数据集合
		Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
		for (ItemCat itemCat : cats) {
			if (!itemCatMap.containsKey(itemCat.getParentId())) {
				itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
			}
			itemCatMap.get(itemCat.getParentId()).add(itemCat);
		}
		// 封装一级对象
		List<ItemCat> itemCatList1 = itemCatMap.get(0L);
		for (ItemCat itemCat : itemCatList1) {
			ItemCatData itemCatData = new ItemCatData();
			itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
			itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
			result.getItemCats().add(itemCatData);
			if (!itemCat.getIsParent()) {
				continue;
			}
			// 封装二级对象
			List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
			List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
			itemCatData.setItems(itemCatData2);
			for (ItemCat itemCat2 : itemCatList2) {
				ItemCatData id2 = new ItemCatData();
				id2.setName(itemCat2.getName());
				id2.setUrl("/products/" + itemCat2.getId() + ".html");
				itemCatData2.add(id2);
				if (itemCat2.getIsParent()) {
					// 封装三级对象
					List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
					List<String> itemCatData3 = new ArrayList<String>();
					id2.setItems(itemCatData3);
					for (ItemCat itemCat3 : itemCatList3) {
						itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
					}
				}
			}
			if (result.getItemCats().size() >= 14) {
				break;
			}
		}
		// TODO 缓存添加原则,不能影响原先逻辑
		try {
			redis.set(TAOTAO_MANAGE_ITEM_CAT_ALL, MAPPER.writeValueAsString(result), seconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
