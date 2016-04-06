package se.jjrecipes.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.User;
import se.jjrecipes.hibernate.HibernateUtil;

public class RecipeData {
	
	public static Recipe addRecipe(Recipe r) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session ses = sf.openSession();
		ses.beginTransaction();
		
		Long id = (Long) ses.save(r);
		ses.getTransaction().commit();
		Recipe newRecipe = (Recipe) ses.get(Recipe.class, id);
		ses.close();
		return newRecipe;
	}
	
	public static Recipe getRecipe(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Recipe recipe = (Recipe) session.get(Recipe.class, id);
		session.close();
		return recipe;
	}
	
	public static Recipe findRecipe(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("FROM Recipe re where re.id=:recipe_id");
		query.setParameter("recipe_id", id);
		Recipe recipe = (Recipe) query.uniqueResult();
		session.close();
		return recipe;
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
		List<Recipe> recipes = session.createCriteria(Recipe.class).list();

		
		/*Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("From Recipe re where re.name LIKE :=searchString");
		q.setParameter("searchString", searchString);
		List<Recipe> recipes = q.list();*/
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
	
	public static void deleteRecipe(Recipe r) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		//TODO: remove favourite objects first
		
		session.beginTransaction();

		session.delete(r);
		session.getTransaction().commit();
		session.close();
	}
}
