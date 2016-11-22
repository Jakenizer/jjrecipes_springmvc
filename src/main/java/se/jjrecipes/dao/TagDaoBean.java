package se.jjrecipes.dao;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.Tag;

@Repository
public class TagDaoBean extends AbstractDaoBean implements TagDao {

	@PersistenceContext
    private EntityManager manager;
	
	@Override
	public Tag getTag(Long id) {
		return get(Tag.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Tag> listTags() {
		Query query = manager.createQuery("From Tag");
		return query.getResultList();
	}
	
	@Override
	public TreeSet<Tag> getSortedList() {
		return new TreeSet<Tag>(listTags());
	}

	@Override
	public Tag addTag(String tagName) {
		Tag t = new Tag(tagName);
		manager.persist(t);
		manager.flush();
		return t;
	}
	
	@Override
	public List<Tag> getTagsByNames(List<String> names) {
		Query q = manager.createQuery("FROM Tag t WHERE t.name IN ('" + StringUtils.join(names.toArray(), "','") + "')");
		return q.getResultList();
	}

	@Override
	public List<Tag> getTagsByIds(List<Long> names) {
		Query q = manager.createQuery("FROM Tag t WHERE t.id IN ('" + StringUtils.join(names.toArray(), "','") + "')");
		return q.getResultList();
	}

	@Override
	public boolean delete(Long id) {
		Tag t = get(Tag.class, id);
		Set<Recipe> recipes = t.getRecipes();
		for (Recipe recipe : recipes) {
			recipe.getTags().remove(t);
		}
		return deleteById(Tag.class, id);
	}
}
