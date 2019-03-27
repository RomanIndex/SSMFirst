package com.ssm.base.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.springframework.stereotype.Service;
@Service
public class RandomHanZiService {
	private Random ran = new Random();
    private final static int delta = 0x9fa5-0x4e00+1;//0x9fa5 - 0x4e00 + 1
    
    //随机生成汉字
    public char getRandomHan() {
        return (char) (0x4e00 + ran.nextInt(delta));
    }
    
    public String getRandomChar() {
    	String str = "";

        Random random = new Random();
        /**
         * 根据编码规则，汉字分为高低位字节，分别代表区与位
         * 所以可以定义两个变量，记录随机的区号和随机的位号，组合起来就是随机的汉字啦
         */
        int highCode = (176 + Math.abs(random.nextInt(39))); //B0 + 0~39(16~55) 一级汉字所占区
        int lowCode = (161 + Math.abs(random.nextInt(93))); //A1 + 0~93 每区有94个汉字

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(highCode)).byteValue();
        b[1] = (Integer.valueOf(lowCode)).byteValue();

        try {
            str = new String(b, "GBK");//gb2312
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
	}
    
    //需要，指定生成2-4位（随机），且1-2位（姓氏）在指定范围内的汉字
    
    //生成指定 位数 的汉字
    public String getRandomChar2(int len) {
    	String ret="";
        for(int i=0;i<len;i++){
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            //System.out.println(b);
            try{
                str = new String(b, "GBk"); //转成中文
            }catch (UnsupportedEncodingException ex){
                ex.printStackTrace();
            }
            ret+=str;
        }
        return ret;
	}
    
    //将区位码转换为汉字的方法  
    public String codeToChinese(String code) {
            String Chinese = "";  
            for (int i = 0; i < code.length(); i += 4) {
                byte[] bytes = new byte[2];	// 存储区位码的字节数组  
                String highCode = code.substring(i, i + 2);	// 获得高位  
                int tempHigh = Integer.parseInt(highCode);	// 将高位转换为整数  
                tempHigh += 160;	// 计算出区号  
                bytes[0] = (byte) tempHigh;// 将区号存储到字节数组  
                String lowCode = code.substring(i + 2, code.length());	// 获得低位  
                int tempLow = Integer.parseInt(lowCode);	// 将低位转换为整数  
                tempLow += 160;	// 计算出位号  
                bytes[1] = (byte) tempLow;	// 将位号存储到字节数组  
                String singleChar="";	// 存储转换的单个字符  
                try {
                    singleChar = new String(bytes,"GB2312");	// 通过GB2312编码进行转换  
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Chinese += singleChar;	// 存储转换后的结果  
            }  
            return Chinese;
        } 
    
    //汉字转区位码
    public String bytes2HexString(byte b) {
        return bytes2HexString(new byte[] { b });
    }
    
    public String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }
    
    // （单个）汉字转换成区位码（有问题，错的）
    public static String bytes2HexString(String one) throws UnsupportedEncodingException {
        byte[] b = one.getBytes("GB2312");
        String jnm = "";
        for (int j = 0; j < b.length; j++) {
            String hex = Integer.toHexString(b[j] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            jnm = jnm + hex.toUpperCase();
        }
        return jnm;
    }

}