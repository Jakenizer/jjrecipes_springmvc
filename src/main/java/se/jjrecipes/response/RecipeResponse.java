package se.jjrecipes.response;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

import se.jjrecipes.entity.Ingredient;
import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.Tag;

public class RecipeResponse {
	
	private Long id;
	private String name;
	private String content;
	private Set<IngredientResponse> ingredients;
	private Set<TagResponse> tags;
	private String image;
	
	public RecipeResponse(Recipe r) {
		this.id = r.getId();
		this.name = r.getName();
		this.content = r.getContent();
		ingredients = new HashSet<IngredientResponse>();
		for (Ingredient ir : r.getIngredients()) {
			ingredients.add(new IngredientResponse(ir));
		}
		tags = new HashSet<TagResponse>();
		for (Tag tr : r.getTags()) {
			tags.add(new TagResponse(tr));
		}
		
		byte[] im = r.getImage();
		if (im != null)
			this.image = Base64.encodeBase64String(im);
	}
	
	public Long getId() {
		return id;
	}

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
	public Set<IngredientResponse> getIngredients() {
		return ingredients;
	}
	public void setIngredients(Set<IngredientResponse> ingredients) {
		this.ingredients = ingredients;
	}
	public Set<TagResponse> getTags() {
		return tags;
	}
	public void setTags(Set<TagResponse> tags) {
		this.tags = tags;
	}
	public String getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = Base64.encodeBase64String(image);
	}
}
