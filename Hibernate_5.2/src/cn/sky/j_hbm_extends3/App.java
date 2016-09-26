package cn.sky.j_hbm_extends3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	SessionFactory factory = new Configuration()//
			.configure()//
			.addClass(Article.class)//
			.buildSessionFactory();

	@Test
	public void testSave(){
		
		Session session = factory.openSession();
		session.beginTransaction();
		//-----------------------------------------------
		Article article = new Article();
		article.setTitle("这是Article");
		
		Topic topic= new Topic();
		topic.setTitle("这是Topic");
		
		Reply reply = new Reply();
		reply.setTitle("这是Reply");
		
		session.save(article);
		session.save(topic);
		session.save(reply);
		
		//-----------------------------------------------
		session.getTransaction().commit();
		session.close();
		
	}
	
	
	@Test
	public void testGet(){
		
		Session session = factory.openSession();
		session.beginTransaction();
		//-----------------------------------------------
		Article article = session.get(Article.class, "8a999a9157656a8c0157656a90f10000");
		Topic topic = session.get(Topic.class, "8a999a9157656a8c0157656a91010001");
		Reply reply = session.get(Reply.class, "8a999a9157656a8c0157656a91100002");
		System.out.println(article);
		System.out.println(topic);
		System.out.println(reply);
		System.out.println("----------------------------");
		
		
		Article article2 = session.get(Article.class, "8a999a9157656a8c0157656a90f10000");
		Article topic2 = session.get(Article.class, "8a999a9157656a8c0157656a91010001");
		Article reply2 = session.get(Article.class, "8a999a9157656a8c0157656a91100002");
		System.out.println(article2);
		System.out.println(topic2);
		System.out.println(reply2);
		
		
		
		//-----------------------------------------------
		session.getTransaction().commit();
		session.close();
		
	}
	
}
