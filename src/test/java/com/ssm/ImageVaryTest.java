package com.ssm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = "file:D:/eclipse-neon/workspace/SSMMaven/src/main/resources/spring-core-config.xml")
public class ImageVaryTest {
	
	private String THUMBNAILS_IMG_DIR = "D:/LocalPicDev/ImageVary/";
	private String ORIGIN_IMG_NAME = "origin.jpg";
	private String WATERMARK_IMG_NAME = "watermark.jpg";
	
	@Test
	public void test(){
		System.out.println("can i do Thumbnails!!!");
	}
	
	@Test
	public void size() throws IOException{
		//按指定大小把图片进行缩和放（会遵循原图宽高比例） 
		Thumbnails.of(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME).size(200, 300).toFile(THUMBNAILS_IMG_DIR + "遵循原图比例大小变换.jpg");
	}
	
	@Test
	public void forceSize() throws IOException{
		Thumbnails.of(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME).forceSize(200, 300).toFile(THUMBNAILS_IMG_DIR + "不遵循原图比例大小变换.jpg");
		//或者Thumbnails.of(fromPic).size(100, 100).keepAspectRatio(false).toFile(toPic);
	}
	
	@Test
	public void forceSizeToOutputStream() throws IOException{
		OutputStream os = new FileOutputStream(THUMBNAILS_IMG_DIR + "不遵循原图比例OutputStream.jpg");//输出图片位置
		Thumbnails.of(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME).forceSize(200, 300).toOutputStream(os);
	}
	
	@Test
	public void scale() throws IOException{
		Thumbnails.of(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME).scale(0.25f).toFile(THUMBNAILS_IMG_DIR + "缩放变换.jpg");
	}
	
	@Test
	public void rotate() throws IOException{
		Thumbnails.of(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME).size(1280,1024).rotate(90).toFile(THUMBNAILS_IMG_DIR + "旋转变换.jpg");
	}
	
	@Test
	//watermark(位置，水印图，透明度)
	public void watermark() throws IOException{
		Thumbnails.of(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME)
		.size(1280,1024)
		.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(THUMBNAILS_IMG_DIR + WATERMARK_IMG_NAME)), 0.3f)
		.outputQuality(0.8f)
		.toFile(THUMBNAILS_IMG_DIR + "右下水印.jpg");
	}
	
	/*@Test
	public void tmpHandler() throws IOException{
		Thumbnails.of(THUMBNAILS_IMG_DIR + "watermark.png").size(80, 50).toFile(THUMBNAILS_IMG_DIR + "watermark.jpg");
	}*/
	
	@Test
	public void sourceRegion() throws IOException{
		Thumbnails.of(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME)
		.sourceRegion(Positions.CENTER, 400, 400)
		.size(200,200)
		.keepAspectRatio(false)
		.toFile(THUMBNAILS_IMG_DIR + "剪裁中心.jpg");
	}
	
	@Test
	public void outputFormat() throws IOException{
		Thumbnails.of(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME).size(640, 512).outputFormat("png").toFile(THUMBNAILS_IMG_DIR + "格式转换.png");
	}
	
	@Test
	//可用of下面的图片，覆盖os里面的（也就是将of里面的图片 已 流的形式 输出到os里面）
	public void toOutputStream() throws IOException{
		OutputStream os = new FileOutputStream(THUMBNAILS_IMG_DIR + "OutputStream.jpg");//输出图片位置
		Thumbnails.of(THUMBNAILS_IMG_DIR + "覆盖os的原图片.jpg").size(1280,1024).toOutputStream(os);
	}
	
	@Test
	public void asBufferedImage() throws IOException{
		//转换成BufferedImage类型，可用给ImageIO调用
		BufferedImage thumbnail = Thumbnails.of(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME).size(1280,1024).asBufferedImage();
		ImageIO.write(thumbnail, "jpg", new File(THUMBNAILS_IMG_DIR + "BufferedImage.jpg"));//输出图片位置
	}
	
	@Test
	public void example() throws IOException{
		//压缩至指定图片尺寸（例如：横400高300），保持图片不变形，多余部分裁剪掉(这个是引的网友的代码)
		BufferedImage image = ImageIO.read(new File(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME));
		int imageWidth = image.getWidth();
		int imageHeitht = image.getHeight();
		
		Builder<BufferedImage> builder;
		if ((float)300 / 400 != (float)imageWidth / imageHeitht) {
			if (imageWidth > imageHeitht) {
				image = Thumbnails.of(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME).height(300).asBufferedImage();
			} else {
				image = Thumbnails.of(THUMBNAILS_IMG_DIR + ORIGIN_IMG_NAME).width(400).asBufferedImage();
			}
			builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, 400, 300).size(400, 300);
		} else {
			builder = Thumbnails.of(image).size(400, 300);
		}
		builder.outputFormat("jpg").toFile(THUMBNAILS_IMG_DIR + "压缩至指定400x300.jpg");
	}

}
