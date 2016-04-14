package se.jjrecipes.data;

import java.io.Serializable;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.CacheStoreMode;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import se.jjrecipes.entity.Tag;
import se.jjrecipes.hibernate.HibernateUtil;

public class TagData extends AbstractData {
	
	public static Tag addTag(String name) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session ses = sf.openSession();
		ses.beginTransaction();
		
		Tag tag = new Tag();
		tag.setName(name);
		Serializable saved = ses.save(tag);
		
		ses.getTransaction().commit();
		
		tag = (Tag) ses.get(Tag.class, saved);
		ses.close();
		return tag;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Tag> listTags() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("FROM Tag tag"); 
		List<Tag> tags = (List<Tag>) q.list();
		session.close();
		return tags;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Tag> getTagsByNames(List<String> names) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			return session.createCriteria(Tag.class)
					.add(Restrictions.in("name", names))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} finally {
			if (session != null) session.close();
		}
	}
	
	public static TreeSet<Tag> getSortedList() {
		return new TreeSet<Tag>(listTags());
	}
	
	public static Tag getTagByName(String name) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("FROM Tag tag where tag.name = :name"); 
		q.setParameter("name", name);
		Tag tag = (Tag) q.uniqueResult();
		session.close();
		return tag;
	}
}
