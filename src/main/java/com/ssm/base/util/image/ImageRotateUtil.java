package com.ssm.base.util.image;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.gif.GifMetadataReader;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.png.PngMetadataReader;
import com.drew.imaging.png.PngProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

public class ImageRotateUtil {
	private static Logger log = LoggerFactory.getLogger(ImageRotateUtil.class);

	private static String savePath = "C:/production/studio/checkSkin/";
	
	/**
	 * 图片保存操作
	 * 
	 * @param multipartFile 文件流
	 * @param modularName 模块名称
	 * @return 返回图片保存相对路径
	 */
	public static final String saveImage(MultipartFile multipartFile, String modularName) {
		String fileName = null;
		try {
			// 文件保存路径
			String dirname = null;
			/*if (modularName == null || "".equals(modularName.trim())) {
				dirname = "/image";
			} else {
				dirname = "/image/" + modularName;
			}*/
			String filePath = savePath;//savePath + dirname + "/";
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String originalFilename = multipartFile.getOriginalFilename();
			//suffix：后缀
			String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
			if (!fileSuffix.equals(".JPG") && !fileSuffix.equals(".GIF") && !fileSuffix.equals(".JPEG") && !fileSuffix.equals(".PNG")) {
				return fileName;
			} else {
				int angel = getRotateAngleForPhoto(multipartFile.getInputStream(), fileSuffix);
				fileName = UUID.randomUUID().toString().replaceAll("-", "") + "_ck_" + fileSuffix;

				// 获取图片旋转角度
				BufferedImage src = ImageIO.read(multipartFile.getInputStream());
				BufferedImage des = null;
				if (angel != 0) {
					des = rotate(src, angel);
					ImageIO.write(des, fileSuffix.toLowerCase().substring(1, fileSuffix.length()), new File(filePath + fileName));
				} else {
					// 图片正常，不处理图片
					des = src;
				}
				
				ImageIO.write(des, fileSuffix.toLowerCase().substring(1, fileSuffix.length()), new File(filePath + fileName));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return fileName;
	}
	
	public static BufferedImage rotate(Image src, int angel) {
		int src_width = src.getWidth(null);
		int src_height = src.getHeight(null);
		// calculate the new image size
		Rectangle rect_des = calcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

		BufferedImage res = null;
		res = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = res.createGraphics();
		// transform
		g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
		g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

		g2.drawImage(src, null, null);
		return res;
	}

	/**
	 * 图片翻转时，计算图片翻转到正常显示需旋转角度
	 */
	public static int getRotateAngleForPhoto(InputStream inputStream, String fileSuffix) {

		int angel = 0;
		Metadata metadata = null;
		try {
			if (fileSuffix.equals(".JPEG") || fileSuffix.equals(".JPG")) {
				metadata = JpegMetadataReader.readMetadata(inputStream);
			} else if (fileSuffix.equals(".PNG")) {
				metadata = PngMetadataReader.readMetadata(inputStream);
			} else {
				metadata = GifMetadataReader.readMetadata(inputStream);
			}

			Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			int orientation = 0;
			if (directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) { // Exif信息中有保存方向,把信息复制到缩略图
				orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION); // 原图片的方向信息
			}
			// 原图片的方向信息
			if (6 == orientation) {
				// 6旋转90
				angel = 90;
			} else if (3 == orientation) {
				// 3旋转180
				angel = 180;
			} else if (8 == orientation) {
				// 8旋转90
				angel = 270;
			}
		} catch (JpegProcessingException e) {
			log.error(e.getMessage(), e);
		} catch (MetadataException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (PngProcessingException e) {
			log.error(e.getMessage(), e);
		}
		log.info("图片旋转角度：" + angel);
		return angel;
	}

	/**
	 * 计算旋转参数
	 */
	public static Rectangle calcRotatedSize(Rectangle src, int angel) {
		// if angel is greater than 90 degree,we need to do some conversion.
		if (angel > 90) {
			if (angel / 9 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new java.awt.Rectangle(new Dimension(des_width, des_height));
	}

}