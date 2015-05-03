package se.jjrecipes.data;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import se.jjrecipes.entity.User;
import se.jjrecipes.hibernate.HibernateUtil;

public class UserData {
	
	@SuppressWarnings("unchecked")
	public static List<User> listUsers() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("FROM User us"); 
		List<User> users = (List<User>) q.list();
		return users;
	}
	
	public static User findUser(Long userId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("FROM User us where id=:userid"); 
		q.setParameter("userid", userId);
		return (User) q.uniqueResult();
	}
	
	public static User findUser(String username) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("FROM User us where username=:username"); 
		q.setParameter("username", username);
		return (User) q.uniqueResult();
	}
	
}
