package com.ssm;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.ssm.admin.entity.SsmAccount;
import com.ssm.admin.entity.SsmMenu;
import com.ssm.common.entity.ComStudent;
import com.ssm.common.service.TestStudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ssm.base.service.ReflectFieldService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration(locations = {"classpath:jpa-config.xml"})
public class ReflectFieldTest {
	
	@Autowired private ReflectFieldService reflectFieldService;
	@Autowired private TestStudentService testStudentService;
	
	@Test
	public void base() throws Exception{
		//ComStudent student = testStudentService.createSingleObject();
		Class clzz = ComStudent.class;
		System.out.println(ComStudent.class);//class com.ssm.admin.entity.SsmMenu
		System.out.println(clzz.getName());//com.ssm.admin.entity.SsmMenu
		System.out.println(clzz.getClass());//class java.lang.Class

		Class c = Class.forName(clzz.getName());

		Object obj = clzz.newInstance();

		ComStudent menu = (ComStudent) clzz.newInstance();//需要强转
	}

	@Test
	public void manyClass(){
		int seq = 2;
		Class clzz = reflectFieldService.testManyClass(seq, ComStudent.class, SsmMenu.class, SsmAccount.class);
		System.out.println(clzz);
	}

	@Test
	public void Method() throws Exception {
		//test get
		ComStudent student = new ComStudent();
		student.setAge(11);
		student.setName("旺旺");
		Object ob = reflectFieldService.getGetMethod(student, "name");
		System.out.println(ob);

		//test set
		Class clzz = ComStudent.class;//clzz是入参
		Object obj = clzz.newInstance();//ComStudent student2 = new ComStudent();
		String fieldName = "name";//可以从referList里取
		String cellVal = "汪汪";
		reflectFieldService.setValue(obj, obj.getClass(), fieldName, clzz.getDeclaredField(fieldName).getType(), cellVal);
		System.out.println(obj +"》》"+ obj.toString() + "》》》"+ JSON.toJSON(obj));

		//获取某个属性的类型
		System.out.println(clzz.getDeclaredField("createTime").getType());
	}

	@Test
	public void Constructors() throws Exception {
		ComStudent cs = new ComStudent();
		//获得User的字节码
		Class clz = cs.getClass();

		//获取所有公共构造器
		Constructor[] cpublic = clz.getConstructors();
		for (Constructor constructor : cpublic) {
			System.out.println(constructor);
		}

		//获取所有的构造函数
		Constructor [] cAll = clz.getDeclaredConstructors();
		for (Constructor constructor : cAll) {
			System.out.println(constructor);
		}
		//因为是私有的构造函数，要给其访问的权限

		//获取指定的 私有 构造函数
		Constructor<ComStudent> cprivate = clz.getDeclaredConstructor(String.class, Integer.class);
		cprivate.setAccessible(true);//设定权限为true,使其可以访问。如果没有这个权限的话，会出异常
		ComStudent priCst = cprivate.newInstance("张无忌", 26);
		System.out.println(priCst + "》》"+ priCst.getName());

		//获取指定的构造器（配合导出Excel测试用例）
		Constructor<ComStudent> c = clz.getConstructor(String.class, Integer.class, Double.class, String.class, Date.class, int.class);
		//ComStudent speCst = c.newInstance("公构造 ", 23, 56.66, "15600004321", Calendar.getInstance().getTime(), 1);
		//System.out.println(speCst +"》》"+ speCst.getName());

		Object obj = c.newInstance("公构造 ", 23, 56.66, "15600004321", Calendar.getInstance().getTime(), 1);
		System.out.println(JSON.toJSON(obj));

		//简单的创建实例的方法，但是只能用于创建没有参数的公共的。
		clz.newInstance();
	}

}
