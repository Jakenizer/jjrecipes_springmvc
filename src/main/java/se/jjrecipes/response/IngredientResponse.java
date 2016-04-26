package se.jjrecipes.response;

import se.jjrecipes.entity.Ingredient;
//import se.jjrecipes.entity.Ingredient.MeasureType;

public class IngredientResponse {
	private Long id;
	private String name;
	private int amount;
	//private MeasureType measureType;
	
	public IngredientResponse(Ingredient ir) {
		this.setId(ir.getId());
		this.name = ir.getName();
		this.amount = ir.getAmount();
	//	this.measureType = ir.getMeasureType();
	}

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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/*public MeasureType getMeasureType() {
		return measureType;
	}
	public void setMeasureType(MeasureType measureType) {
		this.measureType = measureType;
	}*/

}
