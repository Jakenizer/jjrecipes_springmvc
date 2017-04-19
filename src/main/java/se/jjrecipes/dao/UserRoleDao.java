package se.jjrecipes.dao;

import se.jjrecipes.entity.User;
import se.jjrecipes.entity.UserRole;

public interface UserRoleDao {
	
	UserRole addUserRole(UserRole role);
	
	boolean delete(UserRole role);

	boolean deleteAllForUser(User user);
}
