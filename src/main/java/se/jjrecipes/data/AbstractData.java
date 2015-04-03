package se.jjrecipes.data;

import org.hibernate.Query;
import org.hibernate.Session;

import se.jjrecipes.entity.BaseEntity;
import se.jjrecipes.entity.Tag;
import se.jjrecipes.entity.User;
import se.jjrecipes.hibernate.HibernateUtil;

public class AbstractData {
	
	public static <T> T find(Long entityId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("FROM User us where id=:userid"); 
		q.setParameter("userid", entityId);
		return (T) q.uniqueResult();
	}
	
	public static boolean deleteById(Class<? extends BaseEntity> clazz, Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			BaseEntity e = (BaseEntity) session.get(clazz, id);
			if (e != null) { 
				session.delete(e);
				session.getTransaction().commit();
				return true;
			} else {
				return false;
			}
		} finally {
			session.close();
		}
	}
	
}
