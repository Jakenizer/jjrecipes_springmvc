package se.jjrecipes.response;

import se.jjrecipes.entity.Ingredient;

public class IngredientResponse {
	private Long id;
	private String content;
	
	public IngredientResponse(Ingredient ir) {
		this.setId(ir.getId());
		this.setContent(ir.getContent());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
