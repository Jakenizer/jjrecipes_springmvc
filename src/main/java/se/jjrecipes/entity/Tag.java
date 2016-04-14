package se.jjrecipes.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "tag")
public class Tag extends BaseEntity implements Comparable<Tag> {
	
	private String name;
	private Set<Recipe> recipes;
	
	public Tag(String name) {
		this.name = name;
	}
	
	public Tag() {}

	@Column(name="name", unique=true, length = 45)
	@Length(min = 2, max = 45, message = "The field must be between 2 and 45 characters")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tag_recipe", joinColumns = { 
			@JoinColumn(name = "tag_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "recipe_id", 
					nullable = false, updatable = false) })
	public Set<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}

	@Override
	public int compareTo(Tag otherTag) {
		return name.compareToIgnoreCase(otherTag.getName());
	}
}
