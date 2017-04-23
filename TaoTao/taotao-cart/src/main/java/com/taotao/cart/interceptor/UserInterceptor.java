package com.taotao.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.taotao.cart.bean.User;
import com.taotao.cart.bean.UserThreadLocal;
import com.taotao.cart.service.UserService;
import com.taotao.common.utils.CookieUtils;

public class UserInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserService userService;
	public static final String COOKIE_NAME = "TT_TOKEN";

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception err)
			throws Exception {
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		// 绑定到当前线程中
		UserThreadLocal.set(null);
		String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
		if (StringUtils.isEmpty(token)) {
			return true;
		}
		User user = userService.queryByToken(token);
		if (null == user) {
			return true;
		}
		// 绑定到当前线程中
		UserThreadLocal.set(user);
		return true;
	}
}
