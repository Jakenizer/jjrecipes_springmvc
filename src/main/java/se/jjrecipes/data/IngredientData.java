package se.jjrecipes.data;

import org.hibernate.Session;

import se.jjrecipes.entity.Ingredient;
import se.jjrecipes.hibernate.HibernateUtil;

public class IngredientData extends AbstractData {
	
	public static Ingredient get(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Ingredient in = (Ingredient) session.get(Ingredient.class, id);
		session.close();
		return in;
	}
}
