package com.ssm.base.view;

import java.io.IOException;
import java.util.Properties;

public final class Config {
	private static final Object lock = new Object();

	public static String web_module_file_path = "/ssmFile/";//tomcat的虚拟访问路径
	public static String web_module_file_base = "D:\\LocalPicDev\\";//Documnet Base是我们实际本地磁盘需要被映射的路径
	//
	public static final String SESSION_OPEN_ID_KEY = "open_id_key";//自动提示生成
	
	public static String SECURITY_LOGIN_KEY = "SSM_MAVEN";  //标识是否登录的Session
	public static String SECURITY_IS_LOGIN = "SSM_MAVEN_Login";
	public static String MEMBER_ID = "memberId";
	public static String USER_ACCOUNT = "userAccount";
	public static String WX_OPEN_ID = "openId";
	
	
	public static int COOKIE_AGE = 60*60*24*60;	//cookie有效时间(7天)
	
	private static volatile Properties config;
	
	private Config(){}
    
	private static Properties getInstance(){
		if (config == null) {
			//double lock check
			synchronized (lock) { // declare a private static Object to use for mutex
				if(config == null) {	 // have to do this inside the sync
					config = new Properties();	
				}
			}
			try {
				config.load(Config.class.getClassLoader().getResourceAsStream("properties/config.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return config;
	}
	
	public static String baseScore(){
		return getInstance().getProperty("base_score");
	}
	
	public static String domain(){
		return getInstance().getProperty("domain");
	}

	public static String ACCOUNT_IMG_PATH() {
		return getInstance().getProperty("account_img_path");
	}
	
}
