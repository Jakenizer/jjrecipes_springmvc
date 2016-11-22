package se.jjrecipes.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.jjrecipes.entity.Recipe;
import se.jjrecipes.hibernate.HibernateUtil;

@Repository
public class RecipeDaoBean extends AbstractDaoBean implements RecipeDao {

	@PersistenceContext
    private EntityManager manager;
	
	@Override
	public Recipe getRecipe(Long id) {
		return get(Recipe.class, id);
	}
	
	@Override
	public Recipe add(Recipe recipe) {
		manager.persist(recipe);
		manager.flush();
		return recipe;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Recipe> listRecipes() {
		Query query = manager.createQuery("From Recipe");
		return query.getResultList();
	}
	
	public Set<Recipe> sortedList() {
		return new TreeSet<Recipe>(listRecipes());
	}

	@Override
	public Recipe updateRecipe(Recipe r) {
		return manager.merge(r);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Recipe> findRecipes(String searchString) {
		Query q = manager.createQuery("From Recipe re where re.name LIKE CONCAT('%'," + searchString + ", '%'))");
		return q.getResultList();
	}

		
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Recipe> findRecipesByTags(List<Long> tagIds) {
		if (CollectionUtils.isEmpty(tagIds))
			return new ArrayList<Recipe>();
		
		//find all recipes that are tagged with at least the tags in the tagsIds list.
		String sql = "select r from Recipe r " +
                "join r.tags t " +
                "where t.id in ("+ StringUtils.join(tagIds.toArray(), ",") +") " +
                "group by r " +
                "having count(t)=" + tagIds.size();
		Query query = manager.createQuery(sql);
		//query.setParameterList("tags", tagIds);
		//query.setInteger("tag_count", tagIds.size());
		List<Recipe> recipes = query.getResultList();
		return recipes;
	}
}
