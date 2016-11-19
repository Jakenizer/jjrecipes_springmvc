package se.jjrecipes.data;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import se.jjrecipes.entity.User;
import se.jjrecipes.hibernate.HibernateUtil;

public class UserData extends AbstractData {
	
	@SuppressWarnings("unchecked")
	public static List<User> listUsers() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> users = session.createCriteria(User.class).list();
		return users;
	}
	
	public static User get(long id) {
		return get(User.class, id);
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
	
	public static User addUser(User user) throws HibernateException {
		Session ses = null;
		User newUser;
		try {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			ses = sf.openSession();
			ses.beginTransaction();

			Long id = (Long) ses.save(user);
			ses.getTransaction().commit();
			newUser = (User) get(User.class, id);
		} catch (HibernateException e) {
			throw e;
		} finally {
			if (ses != null) ses.close();
		}
		return newUser;
	}
}
