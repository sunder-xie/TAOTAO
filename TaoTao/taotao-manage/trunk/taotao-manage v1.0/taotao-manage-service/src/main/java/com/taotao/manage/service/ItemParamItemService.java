package com.taotao.manage.service;

import org.springframework.stereotype.Service;

import com.taotao.manage.pojo.ItemParamItem;
@Service
public interface ItemParamItemService extends BaseService<ItemParamItem> {
	public int updateByItemId(Long id, String itemParams);

	public int saveByItemId(Long id, String itemParams);
}
