package cn.sky.e_hbm_collection;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	SessionFactory factory = new Configuration()//
			.configure()//
			.addClass(User.class)//
			.buildSessionFactory();

	@Test
	public void testSave() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		//------------------------------------------
		
		User user = new User();
		user.setName("aaa");
		Set<String> addressSet = new HashSet<String>();
		addressSet.add("address111");
		addressSet.add("address222");
		user.setAddressSet(addressSet);
		
		session.save(user);
		
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}
	
	@Test
	public void testGet() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		//------------------------------------------
		
		User user = session.get(User.class, 1);
		
		System.out.println(user.getName());
		System.out.println(user.getAddressSet());
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

}
