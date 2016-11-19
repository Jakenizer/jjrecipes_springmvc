package se.jjrecipes.dao;

import java.util.List;

import se.jjrecipes.entity.Recipe;

public interface RecipeDao {
	
	Recipe getRecipe(Long id);
	
	public List<Recipe> listRecipes();

	Recipe updateRecipe(Recipe r);

	List<Recipe> findRecipesByTags(List<Long> tagIds);

	Recipe add(Recipe recipe);

	List<Recipe> findRecipes(String searchString);

}
