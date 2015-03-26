package se.jjrecipes.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="recipe")
public class Recipe extends BaseEntity {

	private String name;
	private String content;
	private Set<Ingredient> ingredients;
	private byte[] image;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
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
//	
//	public void setUsersThatFav(Set<User> usersThatFav) {
//		this.usersThatFav = usersThatFav;
//	}
	
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
