package se.jjrecipes.form;

import org.hibernate.validator.constraints.Length;

public class UserForm {

	@Length(min = 2, max = 20, message = "A firstname must be between 2 and 20 long")
	private String firstname;
	@Length(min = 2, max = 20, message = "A lastname must be between 2 and 20 long")
	private String lastname;
	@Length(min = 5, max = 20, message = "A firstname must be between 5 and 20 long")
	private String username;
	@Length(min = 6, max = 20, message = "A password must be between 6 and 20 long")
	private String password;
	private boolean admin;
	private boolean isenabled;
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public byte getIsenabled() {
		return (byte) (isenabled ? 1 : 0);
	}
	public void setIsenabled(boolean isenabled) {
		this.isenabled = isenabled;
	}
}
