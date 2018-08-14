package cn.itcast.store.utils;

import javax.servlet.http.Cookie;

public class CookUtils {
	/**
	 * 根据Cookie名获取Cookie
	 * @param name
	 * @param cookies
	 * @return
	 */
	public static Cookie getCookieByName(String name, Cookie[] cookies) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie;
				}
			}
			return null;
		}
		return null;
	}
	
}
