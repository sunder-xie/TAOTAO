package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.EasyUIPage;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	/**查询内容列表
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIPage> queryContentListByParentId(
			@RequestParam(value = "categoryId") Long categoryId,
			@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "rows", defaultValue = "20") Integer rows) {
		try {
			EasyUIPage page = contentService.queryPageListByWhere(categoryId,pageNum, rows);
			return new ResponseEntity<EasyUIPage>(page, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<EasyUIPage>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**添加
	 * @param parentId
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveContent(Content content) {
		try {
			content.setId(null);
			int save = contentService.save(content); // 利用事务的传播特性
			if(save==1)
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**删除
	 * @param parentId
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteContentById(@RequestParam(value = "ids") List<Object> ids) {
		try {
			contentService.delete(ids); // 利用事务的传播特性
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
