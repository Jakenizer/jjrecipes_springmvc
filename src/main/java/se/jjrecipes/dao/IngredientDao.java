package se.jjrecipes.dao;

import se.jjrecipes.entity.Ingredient;

public interface IngredientDao {

	boolean delete(long id);

	Ingredient get(Long id);

}
