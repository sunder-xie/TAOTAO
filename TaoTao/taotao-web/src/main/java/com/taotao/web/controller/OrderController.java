package com.taotao.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.bean.Order;
import com.taotao.bean.User;
import com.taotao.manage.pojo.Item;
import com.taotao.web.interceptor.LoginInterceptor;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;

@Controller
@RequestMapping("order")
public class OrderController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "success", method = RequestMethod.GET)
	private ModelAndView success(@RequestParam("id") String orderId) {
		ModelAndView mv = new ModelAndView("success");
		Order order = orderService.queryOrderById(orderId);
		mv.addObject("order", order);
		mv.addObject("date", new DateTime().plusDays(2).toString("MM月dd日"));
		return mv;
	}
	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ModelAndView toOrder(@PathVariable("itemId") Long itemId) {
		ModelAndView mv = new ModelAndView("order");
		Item item = itemService.queryByItemId(itemId);
		mv.addObject("item", item);
		return mv;
	}
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submit(Order order, @CookieValue(LoginInterceptor.COOKIE_NAME) String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 用户下单,校验登录状态
		User user = userService.queryByToken(token);
		if (null != user) {
			String orderId = orderService.create(order, user);
			if (StringUtils.isNotEmpty(orderId)) {
				map.put("status", 200);
				map.put("data", orderId);
				return map;
			}
		}
		map.put("status", 400);
		return map;
	}
}
