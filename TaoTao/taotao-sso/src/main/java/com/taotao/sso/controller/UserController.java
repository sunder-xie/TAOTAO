package com.taotao.sso.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	public static String COOKIE_NAME = "TT_TOKEN";

	/**
	 * @param param
	 * @param type 取值为12 3, username phone email
	 * @return
	 */
	@RequestMapping("check/{param}/{type}")
	public ResponseEntity<Boolean> check(@PathVariable("param") String param, @PathVariable("type") String type) {
		try {
			if (!(StringUtils.equalsIgnoreCase(type, "1") || StringUtils.equalsIgnoreCase(type, "2")
					|| StringUtils.equalsIgnoreCase(type, "3"))) {
				return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
			}
			Boolean result = userService.check(param, type);
			if (null != result)
				return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String toRegister() {
		return "register";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLogin() {
		return "login";
	}
	@RequestMapping(value = "logout/{token}", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("token") String token) {
		userService.logout(token);
		CookieUtils.setCookie(request, response, COOKIE_NAME, null);
		return "redirect:http://sso.taotao.com/user/login.html";
	}
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logon(@Valid User form, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (null == form) {
			result.put("status", "400");
			result.put("data", "表单");
		} else {
			String token = userService.logon(form);
			if (StringUtils.isNotEmpty(token)) {
				result.put("status", "200");
				result.put("data", "登录成功");
				CookieUtils.setCookie(request, response, COOKIE_NAME, token);
			} else {
				result.put("status", "400");
				result.put("data", "用户名或密码错误");
			}
		}
		return result;
	}
	@RequestMapping(value = "doRegister", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doRegister(@Valid User form, BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (null == form) {
			result.put("status", "201");
			result.put("data", "表单参数异常");
		} else {
			// 跳过前端的校验进行的登录
			Boolean register = userService.doRegister(form);
			if (null != register && register) {
				result.put("status", "200");
				result.put("data", "登录成功");
			} else {
				result.put("status", "400");
				result.put("data", "");
			}
		}
		return result;
	}
	@RequestMapping(value = "{token}", method = RequestMethod.GET)
	public ResponseEntity<User> queryUserByToken(@PathVariable("token") String token) {
		try {
			User user = this.userService.queryUserByToken(token);
			if (null == user) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
