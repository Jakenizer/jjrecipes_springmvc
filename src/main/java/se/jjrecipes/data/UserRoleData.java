package se.jjrecipes.data;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import se.jjrecipes.entity.UserRole;
import se.jjrecipes.hibernate.HibernateUtil;

public class UserRoleData extends AbstractData {
	
	public static UserRole addUserRole(UserRole r) throws HibernateException {
		Session ses = null;
		UserRole newRecipe;
		try {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			ses = sf.openSession();
			ses.beginTransaction();

			Long id = (Long) ses.save(r);
			ses.getTransaction().commit();
			newRecipe = (UserRole) ses.get(UserRole.class, id);
		} catch (HibernateException e) {
			throw e;
		} finally {
			if (ses != null) ses.close();
		}
		return newRecipe;
	}

}
