package se.jjrecipes.dao;

import org.springframework.stereotype.Repository;

import se.jjrecipes.entity.Ingredient;

@Repository
public class IngredientDaoBean extends AbstractDaoBean implements IngredientDao {

	@Override
	public boolean delete(long id) {
		return deleteById(Ingredient.class, id);
	}

	@Override
	public Ingredient get(Long id) {
		return get(Ingredient.class, id);
	}
}
