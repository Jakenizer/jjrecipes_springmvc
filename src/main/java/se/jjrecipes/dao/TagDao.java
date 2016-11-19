package se.jjrecipes.dao;

import java.util.List;
import java.util.TreeSet;

import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.Tag;

public interface TagDao {

	Tag getTag(Long id);

	TreeSet<Tag> getSortedList();

	Tag addTag(String tagName);

	List<Tag> getTagsByNames(List<String> names);

	List<Tag> getTagsByIds(List<Long> names);

	boolean delete(Long id);
}
