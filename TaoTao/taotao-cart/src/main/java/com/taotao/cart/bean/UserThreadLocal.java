package com.taotao.cart.bean;

public class UserThreadLocal {
	private static final ThreadLocal<User> THREADLOAD = new ThreadLocal<User>();

	public static User get() {
		return THREADLOAD.get();
	}
	public static void set(User user) {
		THREADLOAD.set(user);
	}
}
