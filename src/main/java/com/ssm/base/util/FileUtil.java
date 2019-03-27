package com.ssm.base.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ssm.base.view.Config;

public class FileUtil {
	
	/**
	 * 
	 * @param request 请求
     * @param customPath 存放位置(路径)
     * @param file 文件
     * @param isRandomName 是否随机名称
     * @return 完整文件路径
	 * @return
	 */
	public static String uploadImage(HttpServletRequest request,String customPath, MultipartFile file,boolean isRandomName) {
        //上传
        try {
            String[] typeImg={"gif","png","jpg"};

            if(file != null){
                String origName = file.getOriginalFilename();// 文件原名称
                System.out.println("上传的文件原名称:"+origName);
                // 判断文件类型
                String type = origName.indexOf(".") != -1 ? origName.substring(origName.lastIndexOf(".") + 1, origName.length()) : null;
                if (type != null) {
                    boolean booIsType = false;
                    for (int i = 0; i < typeImg.length; i++) {
                        if (typeImg[i].equals(type.toLowerCase())) {
                            booIsType=true;
                            break;
                        }
                    }
                    //类型正确
                    if (booIsType) {
                        //（存放）图片文件的路径
                    	String rootDir = Config.web_module_file_base;//是我们实际本地磁盘需要被映射的路径
                    	String path = rootDir + customPath;
                    	//String path = checkFilePath(customPath);
                    	//String path = request.getSession().getServletContext().getRealPath(customPath);
                        /**
                         * 形如：E:\Tomcat\apache-tomcat-9.0.0.M17-windows-x64\apache-tomcat-9.0.0.M17\webapps\SSMMaven\\upload\2018-08-07\
                         * 上全部为单划线
                         * 一重启tomcat，原来生成的upload文件夹就会消失（tomcat没有配置相对路径引起的吗）
                         */
                        System.out.println("存放图片文件的路径："+ path);
                        //组合名称
                        String fileSrc="";
                        //是否随机名称
                        if(isRandomName){
                        	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                            String ymd = formatDate.format(new Date());
                            String uuid = UUID.randomUUID().toString().replace("-", "");
                            origName = ymd + "--" + uuid + origName.substring(origName.lastIndexOf("."));
                        }
                        //判断是否存在目录
                        File targetFile = new File(path, origName);
                        if(!targetFile.exists()){
                            targetFile.mkdirs();//创建目录
                        }
                        //上传
                        file.transferTo(targetFile);
                        //（访问图片）完整路径，也是保存数据库的值
                        String serverDomain = "http://localhost:8080"+ Config.web_module_file_path;
                        fileSrc = serverDomain + customPath + origName;
                        //fileSrc = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() + customPath + origName;
                        /**
                         * 形如：http://localhost:8080/upload/2018-08-07/2018-08-077b1c38f3-0e41-4299-90e8-7625c7c2f423.jpg
                         */
                        System.out.println("图片上传成功:"+ fileSrc);
                        return fileSrc;
                    }
                }
            }
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	//生成对应的文件夹（可参考如何生成文件夹）
	private static String checkFilePath(String savaPath){
		
		//保存图片的文件夹路径(tomcat的项目目录下的-->.../自定义文件夹名称 )
        String rootPath = "D:\\LocalPicDev\\";//保存的跟路径，配置文件写死
        File rootFile = new File(rootPath);
        if (!rootFile.exists()) {
        	rootFile.mkdir();
        }
        
        //savaPath = "/自定义文件夹名称/yyyy-MM-dd/";  //yyyy-MM-dd可替换成各种类型的，如头像、banner、封面等
		
        String[] arr = savaPath.split("\\/");
        String prePath = arr[1] == "" ? "TEMP" : arr[1];//因为以/开始，arr[0] = ""
        String customFilePath = rootPath + "\\" + prePath; //自定义文件夹
        
        String doublePath = savaPath.replace("/", "\\\\");//（坑点！）将 / 换成 \\
        String subPath = doublePath.substring(0, doublePath.length() - 2);//最后一个多余的 \\ 要去掉
        String path = rootPath + subPath;
        System.out.println("上传图片保存位置："+ path);
        
        //要生成的文件夹，1：用双\\，单杠会被转义；2：父文件夹必须存在，即不能自动生成中间文件夹
        File customFile = new File(customFilePath);
      	//先创建 自定义 文件夹
        if (!customFile.exists()) {
        	customFile.mkdir();
        }
        
        File tempfile = new File(path);
      	//然后创建以 日期 为文件夹名的文件夹
        if (!tempfile.exists()) {
            tempfile.mkdir();
        }
        
        return path;
		
	}

}
