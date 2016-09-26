package cn.sky.k_query_hql;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Test;

public class App {

	SessionFactory factory = new Configuration()//
			.configure()//
			.addClass(Department.class)//
			.addClass(Employee.class)//
			.buildSessionFactory();

	// 准备测试数据
	@Test
	public void testSave() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		// ------------------------------------------
		for (int i = 1; i <= 10; i++) {
			Department department = new Department();
			department.setName("部门_" + i);
			session.save(department);
		}

		for (int i = 1; i <= 20; i++) {
			Employee employee = new Employee();
			employee.setName("员工_" + i);
			session.save(employee);
		}

		// ------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

	/**
	 * 使用HQL查询。 HQL：Hibernate Query Language 特点： 1.与SQL相似，语法基本相同
	 * 2.SQL查询的是表和表中订单列；HQL查询的是对象和对象的属性。 3.HQL的关键字不区分大小写，类名和属性名区分大小写。
	 * 4.select可以省略
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Test
	public void testHQL() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		// ------------------------------------------
		String hql = null;

		// 1.简单的查询
		hql = "from Employee";
		hql = "from Employee e";
		hql = "from Employee as e";

		// 2.过滤条件where，order by
		hql = "select e from Employee e where e.id>0 and e.id<10 order by name";

		// 3.指定select字句
		// hql = "selec * from Employee"不能使用select *
		hql = "select e.id from Employee e";// query.list():返回List<Integer>
		hql = "select e.id,e.name from Employee e";// query.list():返回List<Object[]>

		// 4.使用new语法封装部分属性到对象(需要有相应的构造方法)
		hql = "select new Employee(e.id,e.name) from Employee e";

		// 5.执行查询，获得结果（List，uniqueResult、分页）
		hql = "from Employee e";
		Query query = session.createQuery(hql)//
				.setFirstResult(0)//
				.setMaxResults(5);
		
		//结果唯一，也可能不存在，用query.uniqueResult();如果结果不唯一，则抛异常
		hql = "from Employee e where e.id=1000";
		query = session.createQuery(hql);
		Employee employee = (Employee) query.uniqueResult();
		System.out.println(employee);
		

		/**
		 * 
		 * query.list(): Return the query results as a List. If the query
		 * contains multiple results per row, the results are returned in an
		 * instance of Object[].
		 */
		List list = query.list();
		for (Object o : list) {
			if (o.getClass().isArray()) {
				System.out.println(Arrays.toString((Object[]) o));
			} else {
				System.out.println(o);
			}
		}

		// ------------------------------------------
		session.getTransaction().commit();
		session.close();

	}
	
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testHQL2() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		// ------------------------------------------
		String hql = null;
		
		//1.聚集函数：count();max();min();avg();sum();
//		hql = "select count(*) from Employee";//返回Long
//		hql = "select max(id) from Employee";//返回是id的类型：Integer
//		Number count = (Number) session.createQuery(hql).uniqueResult();
//		System.out.println(count);
		
		
		//2.分组 group by ,having
//		hql = "select e.name,count(e.id) as c"//给count(e.id)起别名，要加as
//				+ " from Employee e "//
//				+ "where e.id>0 "//
//				+ "group by e.name "//
//				+ "having count(e.id)>1 "//having不能用列的别名
//				+ "order by c asc";//order by 可以使用列的别名
		
		
		
		
		//3.连接查询/HQL是面向对象的查询
		/**
		 * 内连接：select e.id,e.name,d.name from employee (inner) join department d on e.departmentId=d.id;
		 * 外链接=左连接+右连接
		 * 左连接：select e.id,e.name,d.name from employee left (outer) join department d on e.departmentId=d.id;
		 * 右连接：select e.id,e.name,d.name from employee right (outer) join department d on e.departmentId=d.id;
		 */
//		hql = "select e.id,e.name,d.name from Employee e join e.department d";
//		hql = "select e.id,e.name,d.name from Employee e left join e.department d";
//		hql = "select e.id,e.name,d.name from Employee e right join e.department d";
		
//		hql = "select e.id,e.name,e.department.name from Employee e ";//更方便
		
		
		//4.查询时使用参数
//		hql = "from Employee where id between ? and ? ";
//		List list = session.createQuery(hql)
//				.setParameter(0, 3)
//				.setParameter(1, 5)
//				.list();
		
		//当参数是集合时，用setParameterList
//		hql = "from Employee where id in (:ids) ";
//		List list = session.createQuery(hql)
//				.setParameterList("ids", new Object[]{1,3,5,7,9})
//				.list();
		
		
		//5.使用命名查询(在任何一个hbm.xml配置hql语句)
//		Query query = session.getNamedQuery("queryByIdRange");
		
		
		//6.update与delete，不会通知session缓存，需要手动调用session.refresh(employee);
		hql = "update Employee set name=? where id>15";
		Employee employee = session.get(Employee.class, 16);
		System.out.println(employee.getName());//第1次打印名字
		Query query = session.createQuery(hql);
		query.setParameter(0, "哈哈哈2");
		int result = query.executeUpdate();
		System.out.println(result);
		
		session.refresh(employee);
		employee = session.get(Employee.class, 16);
		System.out.println(employee.getName());//第2次打印名字
		//这里注意：跟事务的隔离级别无关；隔离级别针对多个事务并发，而这里是单个事务。
		
		
		
		
		//执行查询
//		List list = query.list();
//		for (Object o : list) {
//			if (o.getClass().isArray()) {
//				System.out.println(Arrays.toString((Object[]) o));
//			} else {
//				System.out.println(o);
//			}
//		}

		
		// ------------------------------------------
		session.getTransaction().commit();
		session.close();

	}
	
}
