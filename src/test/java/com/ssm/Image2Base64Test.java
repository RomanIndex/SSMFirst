package com.ssm;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ssm.base.util.Base64Util;
import com.ssm.base.util.image.ImageUrl2Base64Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class Image2Base64Test {
	@Resource private ImageUrl2Base64Util imageUrl2Base64Service;
	
	@Test
	public void getBase64ByImgUrl(){
		String url = "http://fqjytest.winwayworld.com/media/checkSkin/d57970d5.jpg";
        String suffix = url.substring(url.lastIndexOf(".") + 1);
        try {
            /*URL urls = new URL(url);
            ByteArrayOutputStream  baos = new ByteArrayOutputStream();
            Image image = Toolkit.getDefaultToolkit().getImage(urls);
            BufferedImage biOut = imageUrl2Base64Service.toBufferedImage(image);
            ImageIO.write(biOut, suffix, baos);
            String base64Str = Base64Util.encode(baos.toByteArray());
            String baseHead = "data:image/gif;base64,";
            System.out.println(baseHead + base64Str);*/
            System.out.println(imageUrl2Base64Service.image2Base64(url));
        } catch (Exception e) {
            System.out.println("ono! Exception");
        }
        
    }

}
