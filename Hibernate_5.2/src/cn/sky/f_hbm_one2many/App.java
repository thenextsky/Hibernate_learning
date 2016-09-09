package cn.sky.f_hbm_one2many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	SessionFactory factory = new Configuration()//
			.configure()//
			.addClass(Department.class)//
			.addClass(Employee.class)//
			.buildSessionFactory();

	@Test
	public void testSave() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		//------------------------------------------
		//创建部门
		Department department = new Department();
		department.setName("开发部");
		//创建员工
		Employee employee1 = new Employee();
		employee1.setName("张三");
		Employee employee2 = new Employee();
		employee2.setName("李四");
		
		//关联部门与员工的关系
		employee1.setDepartment(department);
		employee2.setDepartment(department);
		department.getEmployees().add(employee1);
		department.getEmployees().add(employee2);
		
		//保存
		session.save(department);
		session.save(employee1);
		session.save(employee2);
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}
	
	@Test
	public void testGet() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		//------------------------------------------
		Department department = session.get(Department.class, 1);
		System.out.println("部门："+department.getName()+"："+department.getEmployees());
		
		
		Employee e1 = session.get(Employee.class, 1);
		System.out.println("员工："+e1.getName()+"在部门："+e1.getDepartment().getName());
		Employee e2 = session.get(Employee.class, 2);
		System.out.println("员工："+e2.getName()+"在部门："+e2.getDepartment().getName());
		
		
		
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}
	
	@Test
	public void testremoveralation() throws Exception {

		Session session = factory.openSession();
		session.beginTransaction();
		//------------------------------------------
		//从员工解除关系
//		Employee e = session.get(Employee.class, 9);
//		e.setDepartment(session.get(Department.class, 4));
//		session.update(e);不用这句话也能更新
		
		//从部门解除，跟inverse属性有关
		Department d = session.get(Department.class, 4);
		d.getEmployees().clear();
		
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}
	
	
	@Test
	public void testdelete() throws Exception {
		
		Session session = factory.openSession();
		session.beginTransaction();
		//------------------------------------------
//		session.delete(session.get(Employee.class, 16));随便删
		
//		Department d = session.get(Department.class, 4);//部门没有员工，随便删
		
		/**
		 * 部门有员工：
		 * 	1.部门inverse=false，部门要维护关系，会先设置员工的部门id=null
		 * 	2.部门inverse=true，部门不维护关系，直接删除部门，会报错
		 */
		
//		Department d = session.get(Department.class, 6);
//		session.delete(d);
		
		
		//------------------------------------------
		session.getTransaction().commit();
		session.close();
		
	}

}
