package se.jjrecipes.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="measuretype")
public class Measuretype extends BaseEntity {
	private String name;
	private String shortname;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
}
