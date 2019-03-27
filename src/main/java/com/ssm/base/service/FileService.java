package com.ssm.base.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class FileService {

	// 只得到当前文件中的文件名
	public String[] getFileName(String path) {
		File file = new File(path);
		String[] fileName = file.list();
		return fileName;
	}

	// 包括当前文件及其子文件的文件名
	public void getAllFileName(String path, ArrayList<String> fileName) {
		File file = new File(path);
		System.out.println(file.getName() + " " + file.getAbsolutePath());
		File[] files = file.listFiles();
		String[] names = file.list();
		if (names != null) {
			fileName.addAll(Arrays.asList(names));
		}
		for (File a : files) {
			if (a.isDirectory()) {
				getAllFileName(a.getAbsolutePath(), fileName);// 递归调用
			}
		}
	}
	
	/**
	 * 将一个文件 复制 到指定的目录下
	 * 都用绝对路径
	 */
	public void copyFile2Path(File file, File toFile) throws IOException {
        byte[] b = new byte[1024];
		int a;
		FileInputStream fis;
		FileOutputStream fos;
		if (file.isDirectory()) {
			String filepath = file.getAbsolutePath();
			filepath=filepath.replaceAll("\\\\", "/");
			String toFilepath = toFile.getAbsolutePath();
			toFilepath=toFilepath.replaceAll("\\\\", "/");
			int lastIndexOf = filepath.lastIndexOf("/");
			toFilepath = toFilepath + filepath.substring(lastIndexOf ,filepath.length());
			File copy=new File(toFilepath);
			//复制文件夹
			if (!copy.exists()) {
				copy.mkdir();
			}
			//遍历文件夹
			for (File f : file.listFiles()) {
				copyFile2Path(f, copy);
			}
		} else {
			if (toFile.isDirectory()) {
				String filepath = file.getAbsolutePath();
				filepath=filepath.replaceAll("\\\\", "/");
				String toFilepath = toFile.getAbsolutePath();
				toFilepath=toFilepath.replaceAll("\\\\", "/");
				int lastIndexOf = filepath.lastIndexOf("/");
				toFilepath = toFilepath + filepath.substring(lastIndexOf ,filepath.length());
				
				//写文件
				File newFile = new File(toFilepath);
				fis = new FileInputStream(file);
				fos = new FileOutputStream(newFile);
				while ((a = fis.read(b)) != -1) {
					fos.write(b, 0, a);
				}
			} else {
				//写文件
				fis = new FileInputStream(file);
				fos = new FileOutputStream(toFile);
				while ((a = fis.read(b)) != -1) {
					fos.write(b, 0, a);
				}
			}
 
		}

	}
	
	//复制文件到指定目录，要先创建指定目录和存放文件夹，详细如下：
	public void copyFile2Path_error() throws IOException {
		String from = "E:\\TestFileE\\D.txt";
        String to = "E:\\copySingleFile\\copyFile2Path";
        File exist = new File(from);
        System.out.println(exist.getAbsolutePath());
		File wjj = new File(to);
		System.out.println("是否存在？："+ wjj.exists());
		System.out.println("是目录？："+ wjj.isDirectory());
		System.out.println("是文件夹？："+ wjj.isFile());
        if (!wjj.exists()) {
        	wjj.mkdirs();//创建目录（加s的可多级）
        	//Files.createDirectories(Paths.get("E://copySingleFile//copyFile2Path"));//创建目录（同mkdir()）（可多级）
        	if(wjj.exists()){
        		Files.createFile(Paths.get(to +"\\receive.jpg"));
        		//wjj.createNewFile();//创建文件夹（切记：先有 目录 再有 文件夹）
        		Files.copy(Paths.get(from), Paths.get(to +"\\receive.jpg"), StandardCopyOption.REPLACE_EXISTING);
        	}
        }else{
        	Files.copy(Paths.get(from), Paths.get(to +"\\error.png" ), StandardCopyOption.REPLACE_EXISTING);
        }
	}

	/** 复制文件的 4种 方法 **/
	//1、使用FileStreams复制
	public void copyFileUsingFileStreams(File source, File dest) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
	}
	
	//2、使用FileChannel复制
	public void copyFileUsingFileChannels(File source, File dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}
	
	/**
	 * directory：目录
	 * instance：实例
	 * denote：指代; 预示; 代表; 意思是
	 */
	
	//3、使用Java7的Files类复制（唯一一个，不能覆盖，dest文件已存在会报错）
	public void copyFileUsingJava7Files(String strSource, String strDest) throws IOException {
		try {
			File source = new File(strSource);
			File dest = new File(strDest);
			/*if(dest.exists()){
				System.out.println("copy存放的文件已存在！");
			}else{
				Files.copy(source.toPath(), dest.toPath());
			}*/
			if(dest.exists()){
				System.out.println("copy存放的文件已存在！执行删除。。。");
				dest.delete();
			}
			Files.copy(source.toPath(), dest.toPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//4、使用Commons IO复制
	public void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
	    FileUtils.copyFile(source, dest);
	}
	
	//5、使用BufferedReader和BufferedWriter,一次读取一行（！！有乱码！！）
	public void copyFileUsingReadLine(String source, String dest) throws IOException {
		//BufferedReader br = new BufferedReader(new FileReader("D:\\a.txt"));
		//BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\b.txt"));
		BufferedReader br = new BufferedReader(new FileReader(source));
		BufferedWriter bw = new BufferedWriter(new FileWriter(dest));
		String line;
		while((line = br.readLine()) != null) {
			bw.write(line);
			bw.newLine();
			bw.flush();
		}
		bw.close();
		br.close();
	}
	
	/** 同copyDir + copyFile方法 **/
	public void copyTotalFile(File src, File dest) throws IOException {
		// 在目的地创建文件夹 c:\\Test\\day09\\avi
		File newFile = new File(dest, src.getName());
		// 判断拼接成的路径是否存在
		if (!newFile.exists()) {
			//新file不存在，则创建
			newFile.mkdirs();
		}
		// 获取源目录中的所有的文件和文件夹
		File[] files = src.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				// 开始复制文件
				FileInputStream fis = new FileInputStream(file);//输入流
				// c:\\Test\\day09\\ppt
				FileOutputStream fos = new FileOutputStream(new File(newFile, file.getName()));//输出流
				byte[] b = new byte[1024];
				int len;
				while ((len = fis.read(b)) != -1) {
					fos.write(b, 0, len);
				}
				fos.close();
				fis.close();

			} else if (file.isDirectory()) {
				copyTotalFile(file, newFile);
			}
		}
	}

	// 复制文件夹
	public void copyDir(String sourcePath, String newPath) throws IOException {
		File file = new File(sourcePath);
		String[] filePath = file.list();

		if (!(new File(newPath)).exists()) {
			(new File(newPath)).mkdir();
		}

		for (int i = 0; i < filePath.length; i++) {
			if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
				copyDir(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
			}

			if (new File(sourcePath + file.separator + filePath[i]).isFile()) {
				copyFile(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
			}

		}
	}

	// 复制文件
	public void copyFile(String oldPath, String newPath) throws IOException {
		File oldFile = new File(oldPath);
		File file = new File(newPath);
		
		FileInputStream in = new FileInputStream(oldFile);//input，输入流，要获取的“流”
		FileOutputStream out = new FileOutputStream(file);//output，输出流，新生成的“流”

		byte[] buffer = new byte[1024];
		int readByte = 0;
		while ((readByte = in.read(buffer)) != -1) {
			out.write(buffer, 0, readByte);
		}
		in.close();
		out.close();
	}

}