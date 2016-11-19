package se.jjrecipes.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import se.jjrecipes.entity.Recipe;
import se.jjrecipes.hibernate.HibernateUtil;

public class RecipeData extends AbstractData{
	
	public static Recipe addRecipe(Recipe r) throws HibernateException {
		Session ses = null;
		Recipe newRecipe;
		try {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			ses = sf.openSession();
			ses.beginTransaction();

			Long id = (Long) ses.save(r);
			ses.getTransaction().commit();
			newRecipe = (Recipe) ses.get(Recipe.class, id);
		} catch (HibernateException e) {
			throw e;
		} finally {
			if (ses != null) ses.close();
		}
		return newRecipe;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Recipe> findRecipesByTags(List<Long> tagIds) {
		if (CollectionUtils.isEmpty(tagIds))
			return new ArrayList<Recipe>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		

		
		
		//find all recipes that are tagged with at least the tags in the tagsIds list.
		String hql = "select r from Recipe r " +
                "join r.tags t " +
                "where t.id in (:tags) " +
                "group by r " +
                "having count(t)=:tag_count";
		Query query = session.createQuery(hql);
		query.setParameterList("tags", tagIds);
		query.setInteger("tag_count", tagIds.size());
		List<Recipe> recipes = query.list();
		session.close();
		return recipes;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Recipe> findRecipes(String searchString) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query q = session.createQuery("From Recipe re where re.name LIKE CONCAT('%', :searchString, '%'))");
		q.setParameter("searchString", searchString);
		List<Recipe> recipes = q.list();
		session.close();
		return recipes;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Recipe> listRecipes() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("From Recipe re");
		List<Recipe> recipes = q.list();
		session.close();
		return recipes;
	}
	
	public static Set<Recipe> sortedList() {
		return new TreeSet<Recipe>(listRecipes());
	}
	
	public static Recipe updateRecipe(Recipe r) {
		
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			Recipe merged = (Recipe) session.merge(r);
			transaction.commit();
			return merged;
		} finally {
			if (session != null) session.close();
		}
	}
	
	public static void deleteRecipe(Recipe r) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		//TODO: remove favourite objects first
		
		session.beginTransaction();

		session.delete(r);
		session.getTransaction().commit();
		session.close();
	}
}
