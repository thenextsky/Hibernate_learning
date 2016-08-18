package cn.sky.c_hbm_property;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	
	@Test
	public void testSave() throws Exception {
		User user = new User();
		user.setName("guizi1");
		user.setAge(20);
		user.setBirthday(new Date());
		user.setDescription("此处省略1000字");
		FileInputStream is = new FileInputStream("C:/1.jpg");
		System.out.println("available:"+is.available());
		byte[] photo = new byte[is.available()];
		is.read(photo);
		System.out.println("photo.length:"+photo.length);
		is.close();
		user.setPhoto(photo);
		/**
		 * 这里遇到了问题：
		 * 一开始建表的时候photo用了binary，photo类型是tinyblod。
		 * 后来更新映射文件的时候，加上了length=102400，结果没变，导致插入数据过长。
		 * 原因：更新映射文件后，表中字段属性没变。
		 * 删除表重建即可，photo类型变成了mediumblob。
		 * 
		 */
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		cfg.addClass(User.class);
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction ts = session.beginTransaction();
		try{
			session.save(user);
			ts.commit();
		}catch(Exception e){
			ts.rollback();
		}finally{
			session.close();
		}
	}

	@Test
	public void testGet(){
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		cfg.addClass(User.class);
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction ts = session.beginTransaction();
		try{
			User user = session.get(User.class, 1);
			Date date = user.getBirthday();
			byte[] photo = user.getPhoto();
			System.out.println(date);
			FileOutputStream o = new FileOutputStream("C:/1_2.jpg");
			o.write(photo);
			o.close();
			ts.commit();
		}catch(Exception e){
			ts.rollback();
		}finally{
			session.close();
		}
		
	}
	
	
}
