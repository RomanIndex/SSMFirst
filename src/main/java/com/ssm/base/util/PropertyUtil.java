package com.ssm.base.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class PropertyUtil {
	//private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	private static Logger logger = Logger.getLogger(PropertyUtil.class);

    private static Properties props;
	
    public static String WX_OPEN_ID = "openId";

    public static String IMPORT_EXCEL_STUDENT = "import.excel.student";

    public static String IMPORT_EXCEL_MODULE = "import.excel.module";

    static{
        loadProps();
    }

    synchronized static private void loadProps(){
        logger.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
        	/*第一种，通过类加载器进行获取properties文件流*/
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("properties/config.properties");
            /*第二种，通过类进行获取properties文件流*/
            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
        logger.info("properties文件内容：" + props);
    }

    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }

}