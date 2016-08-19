package cn.sky.d_hbm_id;

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
		user.setId(1);//如果不设置，会抛异常
		user.setName("heheda");
		session.save(user);		
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

}
