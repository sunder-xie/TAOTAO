package com.taotao.manage.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIPage;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemService;

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
	public ResponseEntity<Item> showDetail(@PathVariable("itemId") Long itemId) {
		try {
			Item item = itemService.showDetail(itemId);
			if (null == item)
				return new ResponseEntity<Item>(item, HttpStatus.NOT_FOUND);
			return new ResponseEntity<Item>(item, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Item>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 * 加载商品列表
	 * @param pid
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIPage> queryItemCatListByParentId(
			@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "rows", defaultValue = "20") Integer rows) {
		try {
			EasyUIPage page = itemService.queryPageListByWhere(pageNum, rows);
			return new ResponseEntity<EasyUIPage>(page, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<EasyUIPage>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 * 保存商品
	 * @param pid
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item record, @RequestParam("desc") String desc,
			@RequestParam("itemParams") String itemParams) {
		try {
			record.setId(null); // 防止安全问题
			Boolean flag = itemService.save(record, desc, itemParams); // 利用事务的传播特性
			if (flag)
				return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 * 删除商品 TODO 缺陷未修复
	 * @param record
	 * @param desc
	 * @return
	 */
	@RequestMapping( method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteByItemId(@RequestParam("ids") List<Object> ids) {
		try {
			if (null == ids || ids.isEmpty())
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			Boolean flag = itemService.delete(ids); // 利用事务的传播特性
			if (flag)
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 * 更新商品
	 * @param record
	 * @param desc
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> update(Item record, @RequestParam("desc") String desc,
			@RequestParam("itemParams") String itemParams) {
		try {
			// 标题不能为空
			if (record == null || record.getTitle().isEmpty())
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			Boolean flag = itemService.update(record, desc, itemParams); // 利用事务的传播特性
			if (flag)
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
