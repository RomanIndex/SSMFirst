package com.ssm.base.web;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssm.base.view.Config;
import com.ssm.base.view.Result;

@Controller
public class UploadImgController {
	
	/**
	 * 公共保存图片方法，暂时放在这里
	 * @param files
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "admin/uploadImg/{type}", method = RequestMethod.POST)
    public Result<String> uploadImg(@RequestParam(value="files",required=false)MultipartFile files, @PathVariable("type")String type) throws IOException
    {
		Result<String> result = new Result<String>();
		String path = "";
		switch (type) {
		case "accountImg":
			path = Config.ACCOUNT_IMG_PATH();
			break;
		case "studioCoveraaddPic":
			path = "";//Config.StudioCoverPath();
			break;
		default:
			break;
		}
		try {
			System.out.println("物理绝对路径："+ path);
			String imgPath = Config.domain()+ "/"+ type +"/"+ uploadFile(files, path);
			System.out.println("（DB存值）服务器相对路径："+ imgPath);
			
			result.setCode(0);
			result.setData(imgPath);
		} catch (Exception e) {
			result.setMsg("上传图片异常，请稍后重试！");
			e.printStackTrace();
		}
		return result;
    }
	
	private String uploadFile(MultipartFile file, String path) throws IOException {
		String name = file.getOriginalFilename();
		String suffixName = name.substring(name.lastIndexOf("."));
		String hash = Integer.toHexString(new Random().nextInt());
		String fileName = hash +suffixName;
		File tempFile = new File(path, fileName);
		
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdirs();
		}
		
		if (tempFile.exists()) {
			tempFile.delete();
		}
		tempFile.createNewFile();
		file.transferTo(tempFile);
		
		return tempFile.getName();
	}

}