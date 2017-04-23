package com.taotao.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.bean.EasyUIPage;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.web.service.ItemService;

/**
 * springmvc 4.0 restful 实现action访问
 * @author xieshengrong
 */
@Controller
@RequestMapping("/item")
@SuppressWarnings("all")
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ModelAndView showDetail(@PathVariable("itemId") Long itemId) {
		ModelAndView mv = new ModelAndView("item");
		Item item = itemService.queryByItemId(itemId);
		if (null != item)
			mv.addObject("item", item);
		ItemDesc desc = itemService.queryItemDescByItemId(itemId);
		if (null != desc)
			mv.addObject("itemDesc", desc);
		String itemParam = itemService.queryItemParamByItemId(itemId);
		mv.addObject("itemParam", itemParam);
		return mv;
	}
}
