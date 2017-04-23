package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;

@Controller
@RequestMapping("/item/desc")
public class ItemDescController {
	@Autowired
	private ItemDescService itemDescService;

	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ResponseEntity<ItemDesc> queryByItemId(@PathVariable("itemId") Long itemId) {
		try {
			ItemDesc queryById = itemDescService.queryById(itemId);
			if (null != queryById)
				return new ResponseEntity<ItemDesc>(queryById, HttpStatus.OK);
			return new ResponseEntity<ItemDesc>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ItemDesc>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
