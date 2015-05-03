package se.jjrecipes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "appuser")
public class User extends BaseEntity{
	
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	//private Set<UserRole> userRole;

	
	@Column(name = "firstname")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "lastname")
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUserName() {
		return username;
	}
	
	public void setUserName(String userName) {
		this.username = userName;
	}
	
	public String getPassword() {
		return this.password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
	/*
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = { 
			@JoinColumn(name = "user_id", nullable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false) })
	public Set<UserRole> getUserRole() {
		return userRole;
	}
	
	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}*/
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
			return false;
		
		User u = (User) obj;
		if (u.getId() == id)
			return true;
		
		return false;
	}
}
