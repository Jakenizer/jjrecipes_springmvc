package se.jjrecipes.data;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.User;
import se.jjrecipes.hibernate.HibernateUtil;

public class RecipeData {
	
	public static void addRecipe(Recipe r) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		//session.save(r);
		System.out.println(session.save(r).getClass().getName());

		session.getTransaction().commit();
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
