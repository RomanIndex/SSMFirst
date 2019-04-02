package com.ssm.base.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.ssm.common.util.StringUtil;
import org.springframework.stereotype.Service;

@Service
public class ReflectFieldService {
	
	/**
     * 单个对象的所有键值
     * @param obj 单个对象
     * @return Map<String, Object> map
     */
    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 得到类对象
        Class<?> clzz = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = clzz.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val = new Object();
            try {
                val = f.get(obj);
                //得到此属性的值
                map.put(f.getName(), val);

                //得到此属性的类型（）
                /*String type = f.getType().toString();
                if (type.endsWith("String")) {
                    System.out.println(f.getType() + "\t是String");
                    f.set(obj, "12");
                } else if (type.endsWith("int") || type.endsWith("Integer")) {
                    System.out.println(f.getType() + "\t是int");
                    f.set(obj, 12);
                } else {
                    System.out.println(f.getType() + "\t");
                }*/

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        System.out.println("反射遍历单个对象的所有键值》》" + map.toString());
        return map;
    }

    /**
     * 单个对象的某个键的值
     * @param obj
     * @param key
     * @return Object 键在对象中所对应得值 没有查到时返回空字符串
     */
    public static Object getValueByKey(Object obj, String key) {
        // 得到类对象
        Class<?> clzz = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = clzz.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); //设置些属性是可以访问的
            try {
                if (f.getName().endsWith(key)) {
                    System.out.println(f.getName()+ " >> " + f.get(obj));
                    return f.get(obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 没有查到时返回空字符串
        return "";
    }

    //--------------------------------根据 属性名 获取对象的get或set--------------------------------------------

    /**
     * 根据属性，获取get方法
     * @param ob 对象
     * @param name 属性名
     * @return
     * @throws Exception
     */
    public static Object getGetMethod(Object ob , String name){
        Method[] m = ob.getClass().getMethods();
        try{
            for(int i = 0; i < m.length; i++){
                //boolean类型的值生成的get方法并不是getField的方式。而是isField的方式
                if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
                    return m[i].invoke(ob);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 根据属性，拿到set方法，并把值set到对象中
     * @param obj 对象
     * @param clazz 对象的class
     * @param filedName 需要设置值得属性
     * @param typeClass set方法入参的类型？
     * @param value set的值？
     */
    public static void setValue(Object obj, Class<?> clazz, String filedName, Class<?> typeClass, Object value){
        filedName = StringUtil.removeLine(filedName);
        String methodName = "set" + filedName.substring(0,1).toUpperCase() + filedName.substring(1);
        try{
            Method method =  clazz.getDeclaredMethod(methodName, new Class[]{typeClass});
            method.invoke(obj, new Object[]{getClassTypeValue(typeClass, value)});//要把value类型 转为 typeClass类型
        }catch(Exception ex){
            System.out.println("【"+ methodName +" = "+ value.toString()+"】value："+ value.getClass()+ "（"+ value.getClass().getName()+"）》》"
                    + "typeClass："+typeClass+ "（"+ typeClass.getName()+ "）");
            ex.printStackTrace();
        }
    }

    /**
     * 通过class类型获取获取对应类型的值
     * @param typeClass class类型
     * @param value 值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value){
        if(typeClass == int.class  || value instanceof Integer){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == short.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == byte.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == double.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == long.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == String.class){
            if(null == value){
                return "";
            }
            return value;
        }else if(typeClass == boolean.class){
            if(null == value){
                return true;
            }
            return value;
        }else if(typeClass == BigDecimal.class){
            if(null == value){
                return new BigDecimal(0);
            }
            return new BigDecimal(value+"");
        }else {
            System.out.println("set入参类型"+ typeClass + "<-->准备set入参传入的值的类型："+ value.getClass());
            return typeClass.cast(value);
        }
    }

}
