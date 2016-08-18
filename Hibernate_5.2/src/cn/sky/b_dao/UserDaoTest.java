package cn.sky.b_dao;

import java.util.List;

import org.junit.Test;

import cn.sky.a_helloworld.User;

public class UserDaoTest {

	private UserDao dao = new UserDao();
	@Test
	public void testSave() {
		User user = new User();
//		user.setId(222);没效果
		user.setName("guizi2");
		dao.save(user);
	}

	@Test
	public void testDelete() {
		dao.delete(3);
	}

	@Test
	public void testUpdate() {
		User user = dao.getById(4);
		user.setName("guizi4update");
		dao.update(user);
	}

	@Test
	public void testGetById() {
		User user = dao.getById(5);
		System.out.println(user);
	}

	@Test
	public void testGetAll() {
		List<User> list = dao.getAll();
		System.out.println(list);
	}
	@Test
	public void testAddMany(){
		for(int i=1;i<=20;i++){
			User user = new User();
			user.setName("guizi"+i);
			dao.save(user);
		}
	}

	@Test
	public void testQueryPage() {
		QueryResult r = dao.queryPage(10, 20);
		System.out.println(r.getList());
	}

}
