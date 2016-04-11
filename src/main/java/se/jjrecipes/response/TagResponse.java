package se.jjrecipes.response;

import se.jjrecipes.entity.Tag;

public class TagResponse {
	private long id;
	private String name;

	public TagResponse(Tag tr) {
		this.id = tr.getId();
		this.name = tr.getName();
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
