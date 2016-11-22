package se.jjrecipes.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

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
	public boolean delete(UserRole role) {
		return deleteById(UserRole.class, role.getId());
	}

}
