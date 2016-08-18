package cn.sky.a_helloworld;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.sky.a_helloworld.User;


public class App {
	
	@Test
	public void testSave() throws Exception {
		User user = new User();
		user.setName("guizi1");
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
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

}
