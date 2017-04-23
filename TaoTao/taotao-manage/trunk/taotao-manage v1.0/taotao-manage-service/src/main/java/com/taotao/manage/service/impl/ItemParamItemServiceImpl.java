package com.taotao.manage.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemParamItemService;

@Service
public class ItemParamItemServiceImpl extends BaseServiceImpl<ItemParamItem> implements ItemParamItemService {
	@Autowired
	private ItemParamItemMapper itemParamItemMapper;

	/**
	 * 更新商品时候关联更新
	 * @param id
	 * @param itemParams
	 * @return
	 */
	public int updateByItemId(Long id, String itemParams) {
		ItemParamItem itemParam = new ItemParamItem();
		itemParam.setParamData(itemParams);
		itemParam.setCreated(new Timestamp(System.currentTimeMillis()));
		Example example = new Example(ItemParamItem.class);
		example.createCriteria().andEqualTo("itemId", id);
		return this.itemParamItemMapper.updateByExampleSelective(itemParam, example);
	}
	/**
	 * 保存商品时候,关联保存
	 * @param id
	 * @param itemParams
	 * @return
	 */
	public int saveByItemId(Long id, String itemParams) {
		ItemParamItem itemParam = new ItemParamItem();
		itemParam.setParamData(itemParams);
		itemParam.setCreated(new Timestamp(System.currentTimeMillis()));
		Example example = new Example(ItemParamItem.class);
		example.createCriteria().andEqualTo("itemId", id);
		return this.itemParamItemMapper.updateByExampleSelective(itemParam, example);
	}
}
