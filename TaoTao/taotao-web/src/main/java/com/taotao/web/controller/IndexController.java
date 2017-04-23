package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.service.IndexService;

@Controller
public class IndexController {
	@Autowired
	private IndexService indexService;

	// 首页
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		try {
			String indexAD1 = indexService.queryAd1();
			String indexAD2 = indexService.queryAd2();
			// 在页面上用el表达式去获取ad1
			mv.addObject("indexAD1", indexAD1);
			mv.addObject("indexAD2", indexAD2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
