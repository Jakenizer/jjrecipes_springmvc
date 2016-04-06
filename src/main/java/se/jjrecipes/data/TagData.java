package se.jjrecipes.data;

import java.io.Serializable;
import java.util.List;
import java.util.TreeSet;

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
		Session session = HibernateUtil.getSessionFactory().openSession();
	    return (List<Tag>) session.createCriteria(Tag.class).add(Restrictions.in("name", names)).list();
	}
	
	public static TreeSet<Tag> getSortedList() {
		return new TreeSet<Tag>(listTags());
	}
	
	public static Tag getTag(long id) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session ses = sf.openSession();
		ses.beginTransaction();
		Tag tag = (Tag)ses.get(Tag.class, id);
		return tag;
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
