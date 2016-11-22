package se.jjrecipes.service;

import se.jjrecipes.entity.User;

public interface AdminService {

	User createUser(User user, boolean isAdmin);
	
	boolean deleteUser(User user);
	
	User updateUser(User user);
}
