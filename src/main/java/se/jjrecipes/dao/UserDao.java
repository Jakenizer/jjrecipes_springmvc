package se.jjrecipes.dao;

import java.util.List;

import se.jjrecipes.entity.User;

public interface UserDao {
	
	User get(Long id);
	
	User findUser(String username);
	
	List<User> listUsers();
	
	User addUser(User user);

	boolean delete(Long userId);
}
