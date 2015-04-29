package models.db.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;

import play.db.jpa.JPA;
import models.db.dao.ValueDAO;
import models.db.entity.Value;

public class HibernateValueDAO extends ValueDAO {

	@Override
	public void create(Value entity) {
		EntityManager em = JPA.em();

	/*	try {
			em.getTransaction().begin();
		} catch (IllegalStateException e) {
			em.getTransaction().rollback();
			this.create(entity);
		}
		
		try {
	*/		em.persist(entity);
	/*		em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public Value read(long id) {
		return JPA.em().find(Value.class, id);
	}

	@Override
	public boolean update(Value entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Value> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return false;
	}

}
