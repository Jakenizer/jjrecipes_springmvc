package se.jjrecipes.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.jjrecipes.entity.User;

@Repository
public class UserDaoBean extends AbstractDaoBean implements UserDao {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public User findUser(String username) {
		TypedQuery<User> query = manager.createQuery("FROM User WHERE username = " + username, User.class);
		return (User) query.getSingleResult();
	}

	@Override
	public List<User> listUsers() {
		TypedQuery<User> query = manager.createQuery("FROM User", User.class);
		return query.getResultList();
	}

	@Override
	public User addUser(User user) {
		manager.persist(user);
		manager.flush();
		return user;
	}

	@Override
	public boolean delete(Long userId) {		
		return deleteById(User.class, userId);
	}

	@Override
	public User get(Long id) {
		return get(User.class, id);
	}

}
