package se.jjrecipes.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import se.jjrecipes.entity.User;
import se.jjrecipes.entity.UserRole;

@Repository
public class UserRoleDaoBean extends AbstractDaoBean implements UserRoleDao {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public UserRole addUserRole(UserRole role) {
		manager.persist(role);
		manager.flush();
		return role;
	}

	@Override
	public boolean deleteAllForUser(User user) {
		Query query = manager.createQuery("DELETE UserRole WHERE username = :username");
		query.setParameter("username", user.getUsername());
		int deletedRows = query.executeUpdate();
		if (deletedRows == 0)
			return false;
		
		return true;
	}

	@Override
	public boolean delete(UserRole role) {
		return deleteById(UserRole.class, role.getId());
	}
}
