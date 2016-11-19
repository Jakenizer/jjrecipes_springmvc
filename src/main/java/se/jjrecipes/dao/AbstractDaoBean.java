package se.jjrecipes.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import se.jjrecipes.entity.BaseEntity;
import se.jjrecipes.hibernate.HibernateUtil;

public class AbstractDaoBean {
	
	@PersistenceContext
    private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<? extends BaseEntity> clazz, Long id) {
		return (T) manager.find(clazz, id);
	}
	
	public <T> boolean deleteById(Class<? extends BaseEntity> clazz, Long id) {
		T e = get(clazz, id);
		if (e != null) { 
			manager.remove(e);
			return true;
		} else {
			return false;
		}
	}
}
