package com.ssm.base.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.ssm.base.view.Result;

import net.sf.json.JSONObject;

public class JavaSmsApi {
	public static Logger logger = Logger.getLogger(JavaSmsApi.class.getName());
	
	// 查账户信息的http地址
	private static String URI_GET_USER_INFO = "https://sms.yunpian.com/v2/user/get.json";
	// 智能匹配模板发送接口的http地址
	private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";
	// 指定模板单发接口的http地址
	private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";
	// 编码格式。发送编码格式统一用UTF-8
	private static String ENCODING = "UTF-8";
	private static String APIKEY = "538e1e2351278c786dc0a974fef51870";

	/**
	 * @param tpl_id	模板ID
	 * @param mobile	手机号
	 * @return Result
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static Result sendMsg(long tpl_id, String mobile) throws IOException, URISyntaxException {

		Result result = new Result();
		
		mobile = URLEncoder.encode(mobile, ENCODING);
		int randomCode = (int)((Math.random()*9+1)*100000);	//要发送的验证码
	    String verifyCode = String.valueOf(randomCode);
		String tpl_value = URLEncoder.encode("#code#", ENCODING) + "=" + URLEncoder.encode(verifyCode, ENCODING);
		
		String jsonString = JavaSmsApi.tplSendSms(APIKEY, tpl_id, tpl_value, mobile);
		logger.info("发送短信验证码返回信息：" + jsonString);
		
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		
		result.setCode(jsonObject.getInt("code"));
		result.setMsg(jsonObject.getString("msg"));
		result.setData(verifyCode);
		
		return result;
	}

	/**
	 * 取账户信息
	 *
	 * @return json格式字符串
	 * @throws java.io.IOException
	 */
	public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		return post(URI_GET_USER_INFO, params);
	}

	/**
	 * 智能匹配模板接口发短信
	 *
	 * @param apikey apikey
	 * @param text 短信内容
	 * @param mobile 接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */
	public static String sendSms(String apikey, String text, String mobile) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("text", text);
		params.put("mobile", mobile);
		return post(URI_SEND_SMS, params);
	}

	/**
	 * 通过模板发送短信(不推荐)
	 *
	 * @param apikey apikey
	 * @param tpl_id 模板id
	 * @param tpl_value 模板变量值
	 * @param mobile 接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */
	public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("tpl_id", String.valueOf(tpl_id));
		params.put("tpl_value", tpl_value);
		params.put("mobile", mobile);
		return post(URI_TPL_SEND_SMS, params);
	}

	/**
	 * 基于HttpClient 4.3的通用POST方法
	 *
	 * @param url 提交的URL
	 * @param paramsMap 提交<参数，值>Map
	 * @return 提交响应
	 */
	public static String post(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity, ENCODING);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}
}
