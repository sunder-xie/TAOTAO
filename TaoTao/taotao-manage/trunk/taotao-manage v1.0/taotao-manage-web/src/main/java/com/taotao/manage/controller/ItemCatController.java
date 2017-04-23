package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

/**
 * springmvc 4.0 restful 实现action访问
 * @author xieshengrong
 */
@Controller
@RequestMapping("item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	/**
	 * 根据父类id加载商品类目
	 * @param pid
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(
			@RequestParam(value = "id", defaultValue = "0") Long pid) {
		try {
			ItemCat record = new ItemCat();
			record.setParentId(pid);
			List<ItemCat> itemCats = itemCatService.queryListByWhere(record);
			if (itemCats != null && !itemCats.isEmpty()) {
				return new ResponseEntity<List<ItemCat>>(itemCats, HttpStatus.OK);
			} else
				return new ResponseEntity<List<ItemCat>>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ItemCat>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
