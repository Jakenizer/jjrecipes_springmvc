package se.jjrecipes.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ingredient")
public class Ingredient extends BaseEntity{
	
	public enum MeasureType {
		TSK("tsk"),
		MSK("msk"),
		KRM("krm"),
		MILLI("ml"),
		CENTI("cl"),
		DECI("dl"),
		LITRE("liter"),
		GRAMS("gram"),
		KILO("kg"),
		NONE("-");
		
		private String text;
		
		private MeasureType(String text) {
			this.text = text;
		}
		
		public String getText() {
			return text;
		}
	}
	
	private String name;
	private int amount;
	private MeasureType measureType;
	private Recipe recipe;
	
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
	
	@Enumerated(EnumType.STRING)	
	public MeasureType getMeasureType() {
		return measureType;
	}
	
	public void setMeasureType(MeasureType measureType) {
		this.measureType = measureType;
	}
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", nullable = false)
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
