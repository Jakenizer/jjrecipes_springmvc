package se.jjrecipes.form;

import org.hibernate.validator.constraints.Length;

public class TagForm {
	
	@Length(min = 2, max = 45, message = "Taggens namn m�ste vara mellan 2 och 25 tecken l�ngt.")
	private String newTag;

	public String getNewTag() {
		return newTag;
	}

	public void setNewTag(String newTag) {
		this.newTag = newTag;
	}


}
