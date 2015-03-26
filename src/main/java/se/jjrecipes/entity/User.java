package se.jjrecipes.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "appuser")
public class User extends BaseEntity{
	
	private String firstName;
	private String lastName;
	
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
	
	/*@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_recipe", joinColumns = { 
			@JoinColumn(name = "user_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "recipe_id", 
					nullable = false, updatable = false) })
	public Set<Recipe> getFavourites() {
		return favourites;
	}

	public void setFavourites(Set<Recipe> favourites) {
		this.favourites = favourites;
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
