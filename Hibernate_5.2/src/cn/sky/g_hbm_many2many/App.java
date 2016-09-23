package cn.sky.g_hbm_many2many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;


public class App {

	SessionFactory factory = new Configuration()//
			.configure()//
			.addClass(Teacher.class)//
			.addClass(Student.class)//
			.buildSessionFactory();

	@Test
	public void testSave() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		//------------------------------------------
		
		
		//创建对象
		Teacher t = new Teacher();
		t.setName("张老师");
		
		Student s = new Student();
		s.setName("学生1");
		
		//关联关系(如果2方同时维护关联关系，则会因为插入中间表的时候重复主键抛异常)
		/**
		 * 1.Java代码只维护一个关系
		 * 2.在其中一个实体表中的set的属性inverse="true"，表示本方不维护关联关系
		 */
		t.getStudents().add(s);
		s.getTeachers().add(t);
		
		//保存
		session.save(t);
		session.save(s);
		
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}
	
	@Test
	public void testGet() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		//------------------------------------------
		
		Teacher t = session.get(Teacher.class, 2);
		System.out.println(t);
		
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}
	
	@Test
	public void testremoveralation() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		//------------------------------------------
		
		Teacher t = session.get(Teacher.class, 2);
		t.getStudents().clear();//需要Teacher维护关联关系
		
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}
	
	
	@Test
	public void testdelete() throws Exception {
		
		Session session = factory.openSession();
		session.beginTransaction();
		
		Teacher t = session.get(Teacher.class, 3);
		session.delete(t);
		/**
		 * 1.t没有学生，直接删除自己
		 * 2.t有学生：
		 * 		如果t能维护关联关系，则先删除关联关系，再删除自己
		 * 		如果t不能维护关联关系，则直接删除自己，会因为外键约束导致抛异常
		 * 			
		 * 
		 */
		
		//------------------------------------------
		session.getTransaction().commit();
		session.close();
		
	}

}
