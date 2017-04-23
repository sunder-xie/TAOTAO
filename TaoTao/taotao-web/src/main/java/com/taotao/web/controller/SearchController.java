package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.bean.SearchResult;
import com.taotao.web.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;

	@RequestMapping("search")
	public ModelAndView search(@RequestParam("q") String keyWords,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		ModelAndView mv = null;
		try {
			// 处理get乱码
			keyWords = new String(keyWords.getBytes("ISO-8859-1"), "UTF-8");
			mv = new ModelAndView("search");
			SearchResult searchResult = searchService.queryByKeyWords(keyWords, page);
			mv.addObject("itemList", searchResult.getData());
			mv.addObject("query", keyWords);
			mv.addObject("page", page);// 总页数
			int total = searchResult.getTotal().intValue();
			Integer pages = total % SearchService.ROWS == 0 ? total / SearchService.ROWS
					: total / SearchService.ROWS + 1;
			mv.addObject("pages", pages);// 总页数
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
