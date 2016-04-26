package se.jjrecipes.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ingredient")
public class Ingredient extends BaseEntity{
	/*
	public enum MeasureType {
		TSK("tsk"),
		MSK("msk"),
		KRM("krm"),
		MILLI("ml"),
		CENTI("cl"),
		DECI("dl"),
		LITRE("liter"),
		GRAMS("gram"),
		KILO("kilo"),
		STYCKEN("stycken"),
		NONE("");
		
		private String text;
		
		private MeasureType(String text) {
			this.text = text;
		}
		
		public String getText() {
			return text;
		}
		
		public static MeasureType fromString(String in) {
			if (in != null) {
				for (MeasureType mt : MeasureType.values()) {
					if (mt.text.equalsIgnoreCase(in)) {
						return mt;
					}
				}
			} 
			throw new IllegalArgumentException("null or faulty value");
		}
	}*/
	
	private String name;
	private int amount;
	//private MeasureType measureType;
	private Measuretype measuretype;
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
	/*
	@Enumerated(EnumType.STRING)	
	public MeasureType getMeasureType() {
		return measureType;
	}
	
	public void setMeasureType(MeasureType measureType) {
		this.measureType = measureType;
	}*/
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "measuretype_id")
	public Measuretype getMeasuretype() {
		return measuretype;
	}
	public void setMeasuretype(Measuretype measure) {
		this.measuretype = measure;
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
