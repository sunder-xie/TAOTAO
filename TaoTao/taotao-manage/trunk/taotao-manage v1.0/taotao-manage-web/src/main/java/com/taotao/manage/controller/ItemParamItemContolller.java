package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemParamItemService;

@Controller
@RequestMapping("/item/param/item")
public class ItemParamItemContolller {
	@Autowired
	private ItemParamItemService itemParamItemService;

	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ResponseEntity<ItemParamItem> queryByItemId(@PathVariable("itemId") Long itemId) {
		try {
			ItemParamItem itemParamItem = new ItemParamItem();
			itemParamItem.setItemId(itemId);
			ItemParamItem paramItem = itemParamItemService.queryOne(itemParamItem);
			if (null != paramItem) {
				return new ResponseEntity<ItemParamItem>(paramItem, HttpStatus.OK);
			}
			return new ResponseEntity<ItemParamItem>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ItemParamItem>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
