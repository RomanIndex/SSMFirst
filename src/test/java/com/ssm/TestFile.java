package com.ssm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ssm.base.service.FileService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = "file:D:/eclipse-neon/workspace/SSMMaven/src/main/resources/spring-core-config.xml")
public class TestFile {
	@Resource private FileService fileService;

	@Test
	public void test() {
		System.out.println(".....");
	}

	@Test
	public void copy() throws IOException {
		/*
		 * String[] fileName = getFileName("E:\\TestFileE".trim()); for (String
		 * name : fileName) { System.out.println(name); }
		 * 
		 * System.out.println("--------------------------------");
		 * 
		 * ArrayList<String> listFileName = new ArrayList<String>();
		 * getAllFileName("E:\\TestFileE", listFileName); listFileName.forEach(a
		 * -> System.out.println(a));
		 */

		// fileService.copyTotalFile2Anathor("E:\\TestFileE", "D:/TestFileD");

		Scanner sc = new Scanner(System.in);
		System.out.println("请输入源目录：");
		String sourcePath = sc.nextLine();
		System.out.println("请输入新目录：");
		String path = sc.nextLine();

		fileService.copyDir(sourcePath, path);
	}

	@Test
	public void copyTotalFile() throws IOException {
		// 源目录
		File src = new File("E:\\TestFileE");
		// 目的地
		File dest = new File("E:\\copySingleFile");
		fileService.copyTotalFile(src, dest);
	}
	
	@Test
	public void copyFile2Path_error() throws IOException {
		fileService.copyFile2Path_error();
	}
	
	@Test
	public void copyFile2Path() throws IOException {
		//需要复制的目标文件或目标文件夹
		String from = "E:/TestFileE/一级文件夹/二级文件夹/三级空文件夹";
		File fromFile = new File(from);
		
		//复制到的位置
		String to = "E:/copySingleFile/copyFile2Path";//目前这个文件夹必须存在，且是 左斜杠 /
		File toFile = new File(to);
		
		//String from = "E:\\TestFileE\\测试图片.jpg";
        //String to = "E:\\copySingleFile\\copyFile2Path";
		
		try {
			fileService.copyFile2Path(fromFile, toFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void sysoFile(){
		//以下三个public构造方法
        //构造函数File(String pathname)
        File f1 = new File("E:/copySingleFile/输出.txt");
        System.out.println(f1);
        //File(String parent,String child)
        File f2 = new File("E:/copySingleFile", "输出.txt");
        System.out.println(f2);
        //File(File parent,String child)
        File f3 = new File("E:/copySingleFile");//separator 跨平台分隔符
        File f4 = new File(f3, "输出.txt");
        System.out.println(f4);
	}

	@Test
	public void copySingleFile() throws IOException {
		File source = new File("E:\\TestFileE\\测试图片.jpg");
		File dest = null;
		
		//String strSource = "E:\\TestFileE\\单一文件.txt";
		String strSource = "E:\\TestFileE\\测试图片.jpg";
 
		long end;
		long start;
		
		dest = new File("E:\\copySingleFile\\destfile");
		start = System.nanoTime();
		fileService.copyFileUsingFileStreams(source, dest);
		end = System.nanoTime();
		System.out.println("Time taken by FileStreams Copy = "+ (end - start));
 
		dest = new File("E:\\copySingleFile");
		start = System.nanoTime();
		fileService.copyFileUsingFileChannels(source, dest);
		end = System.nanoTime();
		System.out.println("Time taken by FileChannels Copy = "+ (end - start));
 
		start = System.nanoTime();
		fileService.copyFileUsingJava7Files(strSource, "E:\\copySingleFile\\destfile3.txt");
		end = System.nanoTime();
		System.out.println("Time taken by Java7 Files Copy = "+ (end - start));
 
		dest = new File("E:\\copySingleFile\\destfile4.txt");
		start = System.nanoTime();
		fileService.copyFileUsingApacheCommonsIO(source, dest);
		end = System.nanoTime();
		System.out.println("Time taken by Apache Commons IO Copy = "+ (end - start));
		
		start = System.nanoTime();
		fileService.copyFileUsingReadLine(strSource, "E:\\copySingleFile\\destfile5.txt");
		end = System.nanoTime();
		System.out.println("Time taken by ReadLine Copy = "+ (end - start));

	}

}