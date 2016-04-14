package se.jjrecipes.data;

import org.hibernate.Session;
import org.hibernate.Transaction;

import se.jjrecipes.entity.BaseEntity;
import se.jjrecipes.hibernate.HibernateUtil;

public class AbstractData {
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<? extends BaseEntity> clazz, Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return (T) session.get(clazz, id);
		} finally {
			session.close();
		}
	}
	
	public static <T> boolean deleteById(Class<? extends BaseEntity> clazz, Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			//Transaction transaction = session.beginTransaction();
			T e = get(clazz, id);
			if (e != null) { 
				session.delete(e);
				session.flush();
				session.beginTransaction().commit();
				return true;
			} else {
				return false;
			}
		} finally {
			session.close();
		}
	}
	
}
