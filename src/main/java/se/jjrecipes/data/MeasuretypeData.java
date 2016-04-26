package se.jjrecipes.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import se.jjrecipes.entity.Measuretype;
import se.jjrecipes.hibernate.HibernateUtil;

public class MeasuretypeData extends AbstractData {
	
	public static Measuretype get(long id) {
		return get(Measuretype.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Measuretype> all() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createCriteria(Measuretype.class).list();
	}
	
	public static void add(String name, String shortName) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session ses = sf.openSession();
		ses.beginTransaction();
		
		Measuretype t = new Measuretype();
		t.setName(name);
		t.setShortname(shortName);
		ses.save(t);
		
		ses.getTransaction().commit();
	}
}
