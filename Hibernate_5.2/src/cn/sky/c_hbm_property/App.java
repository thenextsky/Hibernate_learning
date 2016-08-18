package cn.sky.c_hbm_property;

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

	
	
}
