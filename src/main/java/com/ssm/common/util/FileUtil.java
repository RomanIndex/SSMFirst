package com.ssm.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     * 获取包下的所有类名称,获取的结果类似于 XXX.java
     * @author
     * @date    2018年4月11日
     * @param packageName
     * @return
     */
    public static List<String> getAllClasses(String packageName){
        List<String> classList = new ArrayList<String>();
        String className="";
        File f = new File(packageName);
        if(f.exists() && f.isDirectory()){
            File[] files = f.listFiles();
            for (File file : files) {
                className = file.getName();
                classList.add(className);
            }
            return classList;
        }else{
            return null;
        }
    }
}
