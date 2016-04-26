package se.jjrecipes.form;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import se.jjrecipes.validation.StringArrayInputFormat;
import se.jjrecipes.validation.IngredientValidationDoer;
import se.jjrecipes.validation.TagValidationDoer;

public class RecipeForm {
	@Min(1)
	private Long id;
	
	@NotNull
	@Length(min = 3, max = 50, message = "Receptets namn måste vara mellan 3 och 50 tecken långt.")
	private String name;
	
	@StringArrayInputFormat(message = "Fel format på ingredienslistan.", classForValidation = IngredientValidationDoer.class)
	private String[] ingredient;
	
	@Length(max = 1000, message = "Beskrivningen får innehålla max 1000 tecken.")
	private String content;
	
	private MultipartFile file;
	
	@StringArrayInputFormat(message = "Fel format på tag-listan.", classForValidation = TagValidationDoer.class)
	private String[] tags;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*public List<Object> getIngredient() {
		return ingredient;
	}
	public void setIngredient(List<Object> ingredient) {
		this.ingredient = ingredient;
	}*/
	public String[] getIngredients() {
		return ingredient == null ? new String[0] : ingredient;
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
		return tags == null ? new String[0] : tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
}
