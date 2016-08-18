package cn.sky.b_dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import cn.sky.a_helloworld.User;

public class UserDao {
	
	public void save(User user){
		Session session = HibernateUtils.openSession();
		Transaction ts = session.beginTransaction();
		try{
			session.save(user);
			ts.commit();
		}catch(RuntimeException e){
			ts.rollback();
			throw e;
		}finally{
			session.close();
		}
	}
	
	public void delete(int id){
		Session session = HibernateUtils.openSession();
		Transaction ts = session.beginTransaction();
		try{
			User user = session.get(User.class, id);
			if(user==null){
				return;
			}
			session.delete(user);
			ts.commit();
		}catch(RuntimeException e){
			ts.rollback();
			throw e;
		}finally{
			session.close();
		}
	}
	
	public void update(User user){
		Session session = HibernateUtils.openSession();
		Transaction ts = session.beginTransaction();
		try{
			session.update(user);
			ts.commit();
		}catch(RuntimeException e){
			ts.rollback();
			throw e;
		}finally{
			session.close();
		}
	}
	
	public User getById(int id){
		Session session = HibernateUtils.openSession();
		Transaction ts = session.beginTransaction();
		try{
			User user = session.get(User.class, id);
			ts.commit();
			return user;
		}catch(RuntimeException e){
			ts.rollback();
			throw e;
		}finally{
			session.close();
		}
	}
	
	public List<User> getAll(){
		Session session = HibernateUtils.openSession();
		Transaction ts = session.beginTransaction();
		try{
			List<User> list = session.createQuery("from User").list();//hql,关键字不区分大小写
//			session.createSQLQuery("");sql
			
//			Criteria c = session.createCriteria(User.class);
//			c.add(Restrictions.eq("id", 1));
//			c.add(Restrictions.gt("id", 2));
			ts.commit();
			return list;
		}catch(RuntimeException e){
			ts.rollback();
			throw e;
		}finally{
			session.close();
		}
	}
	
	/**
	 * 分页查询
	 * @param beginIndex 开始索引，0-*
	 * @param maxSize 每页记录数
	 * @return
	 */
	public QueryResult queryPage(int beginIndex,int maxSize){
		Session session = HibernateUtils.openSession();
		Transaction ts = session.beginTransaction();
		try{
			List<User> list = null;
			
			Query query =  session.createQuery("from User");
			query.setFirstResult(beginIndex);
			query.setMaxResults(maxSize);
			list = query.list();
			QueryResult result = new QueryResult();
//			int totalRecord = (int) session.createQuery("select count(*) from User").list().get(0);
			Long totalRecord = (Long) session.createQuery("select count(*) from User").uniqueResult();
			result.setList(list);
			result.setPageSize(maxSize);
			result.setCurrentIndex(beginIndex);
			result.setTotalRecord(totalRecord.intValue());
			ts.commit();
			return result;
		}catch(RuntimeException e){
			ts.rollback();
			throw e;
		}finally{
			session.close();
		}
	}
	
}
