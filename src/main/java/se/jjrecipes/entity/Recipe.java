package se.jjrecipes.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="recipe")
public class Recipe extends BaseEntity {

	private String name;
	private String content;
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();
	private Set<Tag> tags = new HashSet<Tag>();
	private byte[] image;
	
	@Column(length = 100)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length = 1000)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Set<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
//	@ManyToMany(mappedBy = "favourites", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//	public Set<User> getUsersThatFav() {
//		return usersThatFav;
//	}

		
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "tag_recipe", joinColumns = { 
			@JoinColumn(name = "recipe_id", nullable = false, updatable = true) }, 
			inverseJoinColumns = { @JoinColumn(name = "tag_id", 
					nullable = false, updatable = true) })
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Recipe))
			return false;
		
		Recipe r = (Recipe) obj;
		if (r.getId() == id)
			return true;
		
		return false;
	}
}
