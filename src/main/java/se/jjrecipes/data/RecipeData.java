package se.jjrecipes.data;

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
	
	public static Recipe findRecipe(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("FROM Recipe re where re.id=:recipe_id");
		query.setParameter("recipe_id", id);
		Recipe recipe = (Recipe) query.uniqueResult();
		session.close();
		return recipe;
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
