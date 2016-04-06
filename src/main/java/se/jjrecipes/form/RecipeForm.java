package se.jjrecipes.form;

import org.springframework.web.multipart.MultipartFile;

public class RecipeForm {
	private String name;
	private String[] ingredient;
	private String content;
	private MultipartFile file;
	private String[] tags;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getIngredients() {
		return ingredient;
	}
	public void setIngredients(String[] ingredients) {
		this.ingredient = ingredients;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
}
