package com.taotao.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;

@Controller
@RequestMapping("/api/item/cat")
public class ApiItemCat {
	@Autowired
	private ItemCatService itemCatService;

	/**
	 * 查询itemcat 列表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ItemCatResult> queryItemCatList() {
		try {
			ItemCatResult itemCatResult = this.itemCatService.queryItemCatList();
			if (itemCatResult == null || itemCatResult.getItemCats() == null || itemCatResult.getItemCats().size() == 0)
				return new ResponseEntity<ItemCatResult>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<ItemCatResult>(itemCatResult, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ItemCatResult>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
