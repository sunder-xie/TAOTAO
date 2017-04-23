package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;

	/**
	 * 根据父类id加载内容类目
	 * @param pid
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ContentCategory>> queryContentCategoryListByParentId(
			@RequestParam(value = "id", defaultValue = "0") Long pid) {
		try {
			ContentCategory record = new ContentCategory();
			record.setParentId(pid);
			List<ContentCategory> list = contentCategoryService.queryListByWhere(record);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<ContentCategory>>(list, HttpStatus.OK);
			} else
				return new ResponseEntity<List<ContentCategory>>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ContentCategory>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 * 添加内容分类表
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveContentCategoryListByParentId(
			@RequestParam(value = "parentId", defaultValue = "0") Long parentId,
			@RequestParam(value = "name") String name) {
		try {
			Boolean flag = contentCategoryService.save(parentId, name); // 利用事务的传播特性
			if (flag)
				return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 * 更新内容分类表
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateContentCategoryListByParentId(@RequestParam(value = "id") Long id,
			@RequestParam(value = "name") String name) {
		try {
			Boolean flag = contentCategoryService.update(id, name); // 利用事务的传播特性
			if (flag)
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 * 删除内容分类表
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteContentCategoryListByParentId(@RequestParam(value = "parentId") Long parentId,
			@RequestParam(value = "id") Long id) {
		try {
			contentCategoryService.delete(parentId, id); // 利用事务的传播特性
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
