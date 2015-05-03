package se.jjrecipes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
public class Tag extends BaseEntity implements Comparable<Tag> {
	
	private String name;
	//private Set<Recipe> recipes;

	@Column(name="name", unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/*
	@OneToMany(mappedBy = "tags")
	public Set<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}
*/
	@Override
	public int compareTo(Tag otherTag) {
		return name.compareToIgnoreCase(otherTag.getName());
	}
}
