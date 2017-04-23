package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.bean.User;
import com.taotao.cart.bean.UserThreadLocal;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartService;

@Controller
@RequestMapping("cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@RequestMapping(value = "show", method = RequestMethod.GET)
	public ModelAndView showCartList() {
		User user = UserThreadLocal.get();
		ModelAndView mv = new ModelAndView("cart");
		List<Cart> cartList = cartService.queryCartList();
		mv.addObject("cartList", null != cartList ? cartList : new ArrayList<Cart>());
		mv.addObject("user", user);
		return mv;
	}
	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public String addItemToCart(@PathVariable("itemId") Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		Integer num = 1;
		this.cartService.addItemToCart(itemId, request, response, num);
		return "redirect:/cart/show.html";
	}
}
