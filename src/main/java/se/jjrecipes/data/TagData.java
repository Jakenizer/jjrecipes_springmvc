package se.jjrecipes.data;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import se.jjrecipes.entity.Tag;
import se.jjrecipes.hibernate.HibernateUtil;

public class TagData extends AbstractData {
	
	@SuppressWarnings("unchecked")
	public static List<Tag> listTags() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("FROM Tag tag"); 
		List<Tag> tags = (List<Tag>) q.list();
		return tags;
	}
}
