package cn.sky.b_dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	private static SessionFactory factory = null;//线程安全
	static{
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		factory = cfg.buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory(){
		return factory;
	}
	
	public static Session openSession(){
		return factory.openSession();
	}
}
