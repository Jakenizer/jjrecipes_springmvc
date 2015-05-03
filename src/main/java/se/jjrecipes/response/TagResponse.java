package se.jjrecipes.response;

import se.jjrecipes.entity.Tag;

public class TagResponse {
	private String name;

	public TagResponse(Tag tr) {
		this.name = tr.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
