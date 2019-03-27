package com.ssm.base.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpHelper {
	private static Logger logger = Logger.getLogger(HttpHelper.class.getName());
	private static String PATTERN_IP = "(\\d*\\.){3}\\d*";
	
	public static HttpServletRequest getRequest()
	{
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static HttpServletResponse getResponse(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	public static String basePath()
	{
		HttpServletRequest request =  getRequest();	
		Pattern ipPattern = Pattern.compile(PATTERN_IP);
	    Matcher matcher = ipPattern.matcher(request.getRequestURL());
		String host = matcher.find() ? request.getLocalAddr() : request.getServerName();
	    
	    StringBuilder builder = new StringBuilder(request.getScheme())
				.append("://").append(host)
				.append(":").append(request.getServerPort())
				.append(request.getContextPath()).append("/");
	
		return builder.toString();
	}
	
	public static String UTF8(String para){
		try {
			para = para == null ? "" : para;
			return new String(para.getBytes("ISO-8859-1"), "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	// HTTP GET request
	public static String SendGet(String url) {
	    HttpClient client = new HttpClient();
	    GetMethod method = new GetMethod(url);
	    
	    try {
	        int statusCode = client.executeMethod(method);

	        if (statusCode != HttpStatus.SC_OK) {
	        	logger.error("发送GET请求失败:" + method.getStatusLine());
	        }
	        
	        return method.getResponseBodyAsString();
	    } catch (HttpException e) {
	    	logger.error("发送GET请求出现异常: Fatal protocol violation", e);
	    } catch (IOException e) {
	    	logger.error("发送GET请求出现异常: transport error", e);
	    } finally {
	    	  method.releaseConnection();
	    }
	    
	    return "";
	}
	
	public static String GetIP(HttpServletRequest request){
		 String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		return ip;
	}
}
