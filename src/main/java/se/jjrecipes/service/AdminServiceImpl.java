package se.jjrecipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.jjrecipes.dao.UserDao;
import se.jjrecipes.dao.UserRoleDao;
import se.jjrecipes.entity.User;
import se.jjrecipes.entity.UserRole;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public User createUser(User user, boolean isAdmin) {

		user = userDao.addUser(user);
		
		UserRole ur = new UserRole();
		ur.setRole("ROLE_USER");
		ur.setUsername(user.getUsername());
		userRoleDao.addUserRole(ur);
		
		if (isAdmin) {
			UserRole ur2 = new UserRole();
			ur2.setRole("ROLE_ADMINISTRATOR");
			ur2.setUsername(user.getUsername());
			userRoleDao.addUserRole(ur2);
		}
		
		return user;
	}

	@Override
	public boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
