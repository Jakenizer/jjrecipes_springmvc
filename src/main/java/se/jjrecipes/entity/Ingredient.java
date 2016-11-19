package se.jjrecipes.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ingredient")
public class Ingredient extends BaseEntity{
	
	private String content;
	private Recipe recipe;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "recipe_id", nullable = false)
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Ingredient))
			return false;
		
		Ingredient i = (Ingredient) obj;
		if (i.getId() == id)
			return true;
		
		return false;
	}
}
