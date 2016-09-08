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
		employee2.setName("张三");
		
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
		
		//------------------------------------------
		session.getTransaction().commit();
		session.close();

	}
	@Test
	public void testdelete() throws Exception {
		
		Session session = factory.openSession();
		session.beginTransaction();
		//------------------------------------------
		
		//------------------------------------------
		session.getTransaction().commit();
		session.close();
		
	}

}
