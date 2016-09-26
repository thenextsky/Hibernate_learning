package cn.sky.i_hbm_one2one;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	SessionFactory factory = new Configuration()//
			.configure()//
			.addClass(Person.class)//
			.addClass(IdCard.class)//
			.buildSessionFactory();

	@Test
	public void testSave(){
		
		Session session = factory.openSession();
		session.beginTransaction();
		//-----------------------------------------------
		Person person = new Person();
		person.setName("肖贵资");
		
		IdCard idCard = new IdCard();
		idCard.setNumber(System.currentTimeMillis()+"");
		
		//关联
		person.setIdCard(idCard);
		idCard.setPerson(person);
		
		//保存
		session.save(person);
		session.save(idCard);
		
		//-----------------------------------------------
		session.getTransaction().commit();
		session.close();
		
	}
	
	
	@Test
	public void testGet(){
		
		Session session = factory.openSession();
		session.beginTransaction();
		//-----------------------------------------------
//		Person person = session.get(Person.class, 1);
//		System.out.println(person.getName());
//		System.out.println(person.getIdCard().getNumber());
		
		IdCard idCard = session.get(IdCard.class, 1);
		System.out.println(idCard.getNumber());
		System.out.println(idCard.getPerson().getName());
		
		
		//-----------------------------------------------
		session.getTransaction().commit();
		session.close();
		
	}
	
	@Test
	public void testRemoveRelation(){
		
		Session session = factory.openSession();
		session.beginTransaction();
		//-----------------------------------------------
		
//		从无外键方解除，不可以。
//		Person person = session.get(Person.class, 1);
//		person.setIdCard(null);
		
//		从有外键方解除，可以。
//		IdCard idCard = session.get(IdCard.class, 1);
//		idCard.setPerson(null);
		
		//-----------------------------------------------
		session.getTransaction().commit();
		session.close();
		
	}
	
	@Test
	public void testDelete(){
		
		Session session = factory.openSession();
		session.beginTransaction();
		//-----------------------------------------------
		
//		1.无关联关系，直接删除
		
		
//		2.有关联关系，若能维护关联关系（有外键方），则直接删除自己（外键在本方，也跟着删除了）
		IdCard idCard = session.get(IdCard.class, 1);
		session.delete(idCard);
		
		
//		3.有关联关系，若不能维护关联关系（无外键方），则直接删除自己，会因为有外键约束而抛异常
//		Person person = session.get(Person.class, 1);
//		session.delete(person);
		
		
		
		//-----------------------------------------------
		session.getTransaction().commit();
		session.close();
		
	}
	

}
