package se.jjrecipes.response;

import se.jjrecipes.entity.Ingredient;
import se.jjrecipes.entity.Measuretype;

public class IngredientResponse {
	private Long id;
	private String name;
	private int amount;
	private Measuretype measureType;
	
	public IngredientResponse(Ingredient ir) {
		this.setId(ir.getId());
		this.name = ir.getName();
		this.amount = ir.getAmount();
		this.measureType = ir.getMeasuretype();
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
	public Measuretype getMeasureType() {
		return measureType;
	}
	public void setMeasureType(Measuretype measureType) {
		this.measureType = measureType;
	}

}
