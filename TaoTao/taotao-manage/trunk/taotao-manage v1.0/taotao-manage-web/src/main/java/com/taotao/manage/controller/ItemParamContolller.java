package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.bean.EasyUIPage;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamContolller {
	@Autowired
	private ItemParamService itemParamService;

	/**
	 * 加载商品列表 TODO 加载列表显示,关联查询商品条目
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public EasyUIPage queryItemCatListByParentId(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "rows", defaultValue = "20") Integer rows) {
		try {
			EasyUIPage page = itemParamService.queryPageListByWhere(pageNum, rows);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestParam("paramData") String paramData, @RequestParam("cid") Long cid) {
		try {
			// 任何crud都需要添加事务管理
			Boolean falg = itemParamService.save(paramData, cid);
			if (falg)
				return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
	public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCatId") Long itemCatId) {
		try {
			ItemParam itemParam = new ItemParam();
			itemParam.setItemCatId(itemCatId);
			ItemParam queryOne = itemParamService.queryOne(itemParam);
			if (null == queryOne)
				return new ResponseEntity<ItemParam>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<ItemParam>(queryOne, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ItemParam>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
