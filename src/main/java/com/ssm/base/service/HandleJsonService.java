package com.ssm.base.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.ssm.common.entity.ValidateAccount;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Service
public class HandleJsonService {

	public String handleJson(ValidateAccount validateAccount) {
		
		// 读取nameID.txt文件中的NAMEID字段（key）对应值（value）并存储
		/*ArrayList<String> list = new ArrayList<String>();
		BufferedReader addOrUpdate;
		try {
			addOrUpdate = new BufferedReader(new FileReader("D:/eclipse-neon/workspace/SSMMaven/src/main/webapp/json/nameID.txt"));// 要更新的数据
			String sname = null;
			while ((sname = addOrUpdate.readLine()) != null) {
				System.out.println("nameID.txt："+ sname);
				list.add(sname);// 将对应value添加到链表存储
			}
			addOrUpdate.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		// 读取原始json文件并进行操作和输出
		try {
			BufferedReader reader = new BufferedReader(new FileReader("D:/eclipse-neon/workspace/SSMMaven/src/main/webapp/json/common_9_column_data.json"));// 读取原始json文件
			BufferedWriter print = new BufferedWriter(new FileWriter("D:/eclipse-neon/workspace/SSMMaven/src/main/webapp/json/HK_new.json"));// 输出新的json文件
			String lastStr = "";
			String tempString  = null, outStrint = null;
			while ((tempString  = reader.readLine()) != null) {
				lastStr += tempString ;
			}
			System.out.println("原始JSON数据："+ lastStr);
			try {
				//JSONObject dataJson = new JSONObject(lastStr);// 创建一个包含原始json串的json对象
				JSONObject dataJson = JSONObject.fromObject(lastStr);
				JSONArray accounts = dataJson.getJSONArray("data");// 找到data的json数组
				for (int i = 0; i < accounts.size(); i++) {
					JSONObject each = accounts.getJSONObject(i);// 获取accounts数组的第i个json对象
					//JSONObject properties = info.getJSONObject("properties");//复杂json处理
					String name = each.getString("name");// 读取properties对象里的name字段值
					System.out.println(name);
					//each.put("name", list.get(i));//添加name字段
					each.put("name", "改变了啊");
					//each.append("name", list.get(i));
					System.out.println(each.getString("id"));
					each.remove("department");// 删除department字段
				}
				//String add = JSONObject.wrap(account).toString();
				//JSONObject.fromObject();
				String add = JSONObject.fromObject(validateAccount).toString();
				//String quXieGang = add.replace("\\", "");
				System.out.println("add："+ add);
				accounts.add(add);
				outStrint = dataJson.toString();
				System.out.println("新的JSON文件："+ outStrint);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			print.write(outStrint);
			print.newLine();
			print.flush();
			reader.close();
			print.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}