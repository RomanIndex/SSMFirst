package com.ssm.base.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssm.base.util.FileUtil;
import com.ssm.base.view.Result;

@Controller
public class FileInputController {

	/**
	 * 图片上传(可多个)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/file/addPic", method = RequestMethod.POST)
	public Result<?> addPicture(HttpServletRequest request, @RequestParam MultipartFile[] file) {
		if(file == null || file.length == 0){
			return new Result<String>(Result.FAIL, "没有检测到文件！", null, null);
		}
		
		Result<Map<String, Object>> result = new Result<>();
		
        Map<String, Object> map = new HashMap<>();
		
		//组合image名称，“;隔开”
		List<String> fileNameList =new ArrayList<String>();
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateSection = sdf.format(new Date());
			String customPath = "/upload/"+ dateSection + "/";
			for (int i = 0; i < file.length; i++) {
				if (!file[i].isEmpty()) {
					//上传文件，随机名称，";"分号隔开
					String fileName = FileUtil.uploadImage(request, customPath, file[i], true);
					fileNameList.add(fileName);
					//首先保存图片地址（然后再有savaForm.action保存图片）
					//entityObj obj = entityObjService.save(entityObj);
					String randomId = "imitateId"+ (int)(Math.random() * 10000);
					//及其不严谨，xxx
					if(map.containsKey(randomId)){
						randomId = "imitateId"+ (int)(Math.random() * 10000);
					}
					map.put(randomId, fileName);
					/*map.put("id", randomId);
					map.put("fileName", fileName);*/
					result.setData(map);
				}
			}
			//上传成功
			if(fileNameList != null && fileNameList.size() > 0){
				result.setCode(0);
				result.setMsg("上传成功！");
			}else {
				result.setMsg("上传失败！文件格式错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("上传出现异常！异常出现在：FileInputController.addPicture()");
		}
		
		return result;
	}

	//其实这个方法不应该放这里
	@ResponseBody
	@RequestMapping(value = "/admin/file/saveForm", method = RequestMethod.POST)
	public Result<?> saveForm(String formStr) {
		System.out.println(formStr);
		return new Result<String>(Result.SUCCESS, "已成功保存，上传成功！", null, null);
	}
	
}