package se.jjrecipes.backing;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import se.jjrecipes.entity.Ingredient;

public class RecipeBacking {
	private String name;
	private String content;
	private String[] ingredients;
	private MultipartFile fileUpload;
	
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

	public String[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}

	public MultipartFile getImage() {
		return fileUpload;
	}

	public void setImage(MultipartFile fileUpload) {
		this.fileUpload = fileUpload;
	}
}
