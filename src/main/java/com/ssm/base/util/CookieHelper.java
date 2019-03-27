package com.ssm.base.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {
	
	/**
	 * 设置cookie
	 * 
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期  以秒为单位
	 * @throws UnsupportedEncodingException 
	 */
	public static void addCookie(String name, String value, int maxAge){
		    Cookie cookie = new Cookie(name, value);
		    cookie.setPath("/"); //设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie的路径及其子路径可以访问
		    cookie.setMaxAge(maxAge);
		    HttpHelper.getResponse().addCookie(cookie);
	
	}
	
	/**
	 * 设置cookie
	 * 
	 * @param name  cookie名字
	 * @param value cookie值
	 * @throws UnsupportedEncodingException 
	 */
	public static void addCookie(String name, String value){
	    Cookie cookie = new Cookie(name, value);
	    cookie.setPath("/"); //设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie的路径及其子路径可以访问
	    HttpHelper.getResponse().addCookie(cookie);
	}
	
	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期 ，以秒为单位
	 * @throws UnsupportedEncodingException 
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath("/"); //设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie的路径及其子路径可以访问
	    if(maxAge > 0) cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}
	
	/**
	 * 清除cookie
	 * 
	 * @param name  cookie名字
	 * @throws UnsupportedEncodingException 
	 */
	public static void eraseCookie(String name) {
		Map<String, Cookie> cookieMap = readCookieMap();
		
		if(cookieMap.containsKey(name)){
			Cookie cookie = cookieMap.get(name);
			cookie.setValue("");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			HttpHelper.getResponse().addCookie(cookie);
		}
	}
	
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name cookie名字
	 * @return Cookie
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name){
	    Map<String, Cookie> cookieMap = readCookieMap(request);
	    
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	
	/**
	 * 根据名字获取cookie
	 * @param name cookie名字
	 * @return Cookie
	 */
	public static Cookie getCookieByName(String name){
	    Map<String, Cookie> cookieMap = readCookieMap();
	    
	    if(cookieMap.containsKey(name)){
	        return cookieMap.get(name);
	    }else{
	        return null;
	    }
	}
	
	/**
	 * 根据名字获取cookie
	 * @param name cookie名字
	 * @return String cookie值
	 */
	public static String getValueByName(String name){
	    Map<String, Cookie> cookieMap = readCookieMap();
	    
	    if(cookieMap.containsKey(name)){
	        return cookieMap.get(name).getValue();
	    }else{
	        return "";
	    }
	}
		
	/**
	 * 将cookie封装到Map里面
	 * @return
	 */
	private static Map<String,Cookie> readCookieMap(){  
	    return readCookieMap(HttpHelper.getRequest());
	}
	
	private static Map<String,Cookie> readCookieMap(HttpServletRequest request){ 
	    Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
	    
	    Cookie[] cookies = request.getCookies();
	    if(null != cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
}
