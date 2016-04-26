package se.jjrecipes.util;

public class IngredientFromJSON {
	
	private String name;
	private Integer amount;
	private Long measureType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Long getMeasureType() {
		return measureType;
	}
	public void setMeasureType(Long measureType) {
		this.measureType = measureType;
	}
	
	
}
