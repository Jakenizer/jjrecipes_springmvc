package se.jjrecipes.data;

import org.hibernate.Query;
import org.hibernate.Session;

import se.jjrecipes.entity.User;
import se.jjrecipes.hibernate.HibernateUtil;

public class AbstractData {
	
	public static <T> T find(Long entityId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("FROM User us where id=:userid"); 
		q.setParameter("userid", entityId);
		return (T) q.uniqueResult();
	}
	
}
