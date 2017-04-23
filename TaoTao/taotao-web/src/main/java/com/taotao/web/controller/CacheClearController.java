package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.common.service.RedisService;
import com.taotao.web.service.ItemService;

/**
 * 后端系统更新缓存后调用该接口,通知缓存刷新
 * @author xieshengrong
 */
@Controller
@RequestMapping("/cache/clear")
public class CacheClearController {
	@Autowired
	private RedisService redis;

	@RequestMapping(value = "{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> itemCacheClear(@PathVariable("itemId") Long itemId) {
		try {
			redis.del(ItemService.TAOTAO_WEB_ITEM + itemId);
			redis.del(ItemService.TAOTAO_WEB_ITEM_DESC + itemId);
			redis.del(ItemService.TAOTAO_WEB_ITEM_PARAM + itemId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
