package com.taotao.cart.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.taotao.cart.bean.Item;
import com.taotao.cart.bean.User;
import com.taotao.cart.bean.UserThreadLocal;
import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.common.service.ApiService;
import com.taotao.common.utils.CookieUtils;

@Service
public class CartService {
	@Autowired
	private ApiService apiService;
	@Autowired
	private CartMapper cartMapper;
	public static final String TT_CART = "TT_CART";
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final int COOKIEMAXAGE = 60 * 60 * 24 * 30 * 12;

	/**
	 * 添加商品到购物车
	 * @param itemId
	 * @param request
	 * @param response
	 * @param num 当前添加购物车的商品数量
	 */
	public void addItemToCart(Long itemId, HttpServletRequest request, HttpServletResponse response, Integer num) {
		try {
			// 添加商品到购物车
			User user = UserThreadLocal.get();
			Cart record = new Cart();
			String url = "http://manage.taotao.com/rest/item/" + itemId;
			String jsonData = apiService.doGet(url);
			Item item = StringUtils.isNotEmpty(jsonData) ? MAPPER.readValue(jsonData, Item.class) : null;
			if (null != user) {
				record.setItemId(itemId);
				record.setUserId(user.getId());
				// 登录状态,查询当前用户的额购物车然后会写数量加1
				Cart cart = cartMapper.selectOne(record);
				if (null != cart) {
					cart.setNum(cart.getNum() + num); // 当前数量
					cartMapper.updateByPrimaryKeySelective(cart);
				} else {
					record.setCreated(new Date());
					record.setUpdated(record.getCreated());
					record.setNum(num);
					record.setItemImage(item.getImage());
					record.setItemPrice(item.getPrice());
					record.setItemTitle(item.getTitle());
					cartMapper.insert(record);
				}
			} else {
				// 未登录状态,获取cookie,会写数量加1,同时刷新cookie的生命时间
				Map<String, Cart> map = null;
				String cookieValue = CookieUtils.getCookieValue(request, TT_CART, true);
				if (StringUtils.isNotEmpty(cookieValue)) {
					map = MAPPER.readValue(cookieValue,
							MAPPER.getTypeFactory().constructMapLikeType(Map.class, String.class, Cart.class));
				} else
					map = new HashMap<String, Cart>();
				if (map.size() != 0) {
					Cart cart = map.get(String.valueOf(itemId));
					cart.setNum(num + cart.getNum());
					map.put(String.valueOf(itemId), cart);
				} else {
					record.setItemId(itemId);
					if (item != null) {
						record.setCreated(new Date());
						record.setUpdated(record.getCreated());
						record.setNum(num);
						record.setItemImage(item.getImage());
						record.setItemPrice(item.getPrice());
						record.setItemTitle(item.getTitle());
					}
					map.put(String.valueOf(itemId), record);
				}
				CookieUtils.setCookie(request, response, TT_CART, MAPPER.writeValueAsString(map), COOKIEMAXAGE, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * TODO 添加分页逻辑
	 * @return
	 */
	public List<Cart> queryCartList() {
		Example example = new Example(Cart.class);
		example.createCriteria().andEqualTo("userId",
				UserThreadLocal.get() != null ? UserThreadLocal.get().getId() : null);
		example.setOrderByClause(" created DESC");
		return cartMapper.selectByExample(example);
	}
}
