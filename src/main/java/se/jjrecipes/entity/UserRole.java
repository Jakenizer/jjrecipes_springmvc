package se.jjrecipes.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import se.jjrecipes.data.AbstractData;

@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity {
	
	private String role;
	private String username;

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
