package org.springframeworkcore.mvc.javaannotationbased.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Base64;

public class CookieServiceUtil{
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static void add(HttpServletResponse response, String name, Object value, int maxAge) throws Exception {
        String json = objectMapper.writeValueAsString(value);
        String encoded = Base64.getUrlEncoder().encodeToString(json.getBytes());
        Cookie cookie = new Cookie(name, encoded);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

	public static <T> T cookieToObject(String value, Class<T> clazz) throws Exception {
		String decoded = new String(Base64.getUrlDecoder().decode(value));
		return objectMapper.readValue(decoded, clazz);
	}
	
	public static void delete(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
	}

}
