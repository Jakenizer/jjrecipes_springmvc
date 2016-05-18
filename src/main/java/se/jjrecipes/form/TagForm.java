package se.jjrecipes.form;

import org.hibernate.validator.constraints.Length;

public class TagForm {
	
	@Length(min = 2, max = 45, message = "Taggens namn maste vara mellan 2 och 45 tecken langt.")
	private String newTag;

	public String getNewTag() {
		return newTag;
	}

	public void setNewTag(String newTag) {
		this.newTag = newTag;
	}


}
