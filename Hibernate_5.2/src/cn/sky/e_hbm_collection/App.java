package cn.sky.e_hbm_collection;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

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
//		Set<String> addressSet = new HashSet<String>();
		Set<String> addressSet = new TreeSet<String>();//当set元素指定sort属性后，set必须是可排序的；
		addressSet.add("1address111");
		addressSet.add("3address222");
		addressSet.add("2address222");
		user.setAddressSet(addressSet);
		//////////
		user.getAddressList().add("地址111");
		user.getAddressList().add("地址222");
		user.getAddressList().add("地址111");
		//////////
		user.setAddressArray(new String[]{"aaaaaaaa","bbbbbbbbbbbbb"});
		//////////
		user.getAddressMap().put("家庭地址", "XXX");
		user.getAddressMap().put("常住地址", "YYY");
		//////////
		user.getAddressBag().add("111111");
		user.getAddressBag().add("22222222222");
		user.getAddressBag().add("111111");
		//////////
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
		
		User user = session.get(User.class, 6);
		
		System.out.println(user.getName());
		System.out.println(user.getAddressSet());
		System.out.println(user.getAddressList());
		System.out.println(Arrays.toString(user.getAddressArray()));
		System.out.println(user.getAddressMap());
		System.out.println(user.getAddressBag());
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}
	
	@Test
	public void testUpdateArray() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		//------------------------------------------
		
		User user = session.get(User.class, 3);
		user.getAddressArray()[0] = "change0000000";
		session.save(user);
		System.out.println(Arrays.toString(user.getAddressArray()));
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

}
