package cn.sky.h_hbm_session_method;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	/**
	 * 对象的状态： 临时状态： 与数据库没有对应，跟session没有关联 一般是新new出来的对象 持久化状态：
	 * 对象在session的管理之中，最终会有对应的数据库记录 特点：1.有OID；2.对对象的修改会同步到数据库。 游离状态：
	 * 数据库有对应记录，但对象不在session的管理之中 修改此状态的对象，数据库不会有变化。 删除状态： 执行了delete()后的对象。
	 * 
	 * 
	 */

	SessionFactory factory = new Configuration()//
			.configure()//
			.addClass(User.class)//
			.buildSessionFactory();

	/**
	 * save：临时状态-->持久化状态（交给session管理，因此多次save同一个对象，只会一次有效）
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSave() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		// ------------------------------------------
		User user = new User();// 临时状态
		user.setName("用户1");
		session.save(user); // 持久化状态
		session.save(user);
		session.save(user);
		session.save(user); // save后可能需要用到ID，save会生成ID，所以save会马上执行，update则不一样，update在commit执行
		// 多次save，没效果

		user.setName("aaa");

		// session.update(user);//不需要update也行，多次update也没效果
		// session.update(user);
		// session.update(user);
		// ------------------------------------------
		session.getTransaction().commit();
		session.close();
		user.setName("bbb");// 游离状态

	}

	/**
	 * update：游离状态-->持久化状态 对象不存在就抛异常
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdate() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		// ------------------------------------------
		User user = session.get(User.class, 5);// 持久化状态
		// user.setName("aaaupdate");

		// session.clear();//清空session的缓存使其游离（所有）
		// session.evict(user);//使user游离（一个）
		// user.setName("aaaclearupdate");//游离状态

		// user.setName("beforeclear");
		// session.flush();//刷出到数据库
		// session.clear();//先清空session缓存使其游离，再执行update（update在commit时执行），
		// 除非flush：立即执行sql；或者执行update，在commit执行。

		// ------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

	/**
	 * 临时状态或者游离状态-->持久化状态：insert into ……;update…… 更新时，对象不存在就抛异常，保存时就不会
	 * 本方法会根据ID判断对象状态：id为原始值（对象为null，数字为0），则临时状态；否则，游离状态
	 * 
	 * 
	 */
	@Test
	public void testSaveOrUpdate() {

		Session session = factory.openSession();
		session.beginTransaction();
		// --------------------------------------------------
		User user = new User();
		user.setId(211111);// 因为id不是原始值，所以属于游离状态，会执行update，如果数据库没有该对象，则抛异常
		user.setName("update222333");
		// 自己生成一个游离状态的对象（需要保证数据库有）
		// 本方法会根据ID判断对象状态：id为原始值（对象为null，数字为0），则临时状态；否则，游离状态
		session.saveOrUpdate(user);// 会更新到数据库

		// --------------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * 持久化状态或者游离状态-->删除状态
	 */
	@Test
	public void testDelete() {
		Session session = factory.openSession();
		session.beginTransaction();
		// --------------------------------------------------
		// User user = session.get(User.class, 1);//持久化
		User user = new User();
		user.setId(111);// 游离状态
		session.delete(user);// 如果对象不存在，则抛异常；在commit执行，除非用flush()

		// --------------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

	/**
	 * 获取数据，得到持久化状态：select……where id=? 马上执行sql 数据不存在则返回null
	 */
	@Test
	public void testGet() {
		Session session = factory.openSession();
		session.beginTransaction();
		// --------------------------------------------------
		User user = session.get(User.class, 322);// 立即执行，因为get后面可能会操作该对象；不存在则返回null
		System.out.println(user.getId());

		// --------------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

	/**
	 * 获取数据，得到持久化状态：select…… load()返回一个代理对象，要求类不能是final，否则不能生成子类代理，就不能使用懒加载功能
	 * 让懒加载失效：1.把实体改成final；2.在hbm.xml写<class...lazzy="false">
	 * 不会马上执行sql，而是在第一次使用非id或class属性时执行sql 数据不存在则抛异常
	 */
	@Test
	public void testLoad() {
		Session session = factory.openSession();
		session.beginTransaction();
		// --------------------------------------------------
		User user = session.load(User.class, 3);// 没有执行sql
		user.getClass();// 没有执行sql
		user.getId();// 没有执行sql

		user.getName();// 马上执行sql
		System.out.println("------");

		// --------------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

	@Test
	public void testSaveBigData() {
		Session session = factory.openSession();
		session.beginTransaction();
		// --------------------------------------------------
		for (int i = 0; i < 1003; i++) {// 161个死掉
			User user = new User();
			session.save(user);
			if (i % 100 == 0) {
				session.flush();// 必须先执行这个，否则缓存没了，数据还没写入
				session.clear();// 清空session缓存
			}

		}

		// --------------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void test2() {
		Session session = factory.openSession();
		session.beginTransaction();
		// --------------------------------------------------
		
//		for (int i = 0; i < 10; i++) {
//			User user = session.get(User.class, 2);//只有一个sql语句，因为有session缓存
//		}
		
//		for(int i=0;i<3;i++){
//			User user = session.get(User.class, 200000);//有3次数据库查询，因为第一次获取不到，后面判断缓存没有，则继续从数据库中获取
//		}
		
		//事务隔离级别
		/**
		 * 1.读未提交
		 * 2.读已提交
		 * 4.可重复读
		 * 8.不可并发（串行化）
		 */
		
		User user = session.get(User.class, 2);
		
		
//		System.out.println(user.getName());
//		//断点，然后去修改数据库的name值
//		System.out.println(user.getName());//取的是session缓存

//		user = session.get(User.class, 2);//再取一遍，或者refresh()，就会从数据库中再取出来
		session.refresh(user);//刷新缓存对象的状态
//		System.out.println(user.getName());
//		//断点，然后去修改数据库的name值
//		System.out.println(user.getName());//mysql默认隔离级别：可重复读，跟第一次读的一致
		
		
		// --------------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

}
