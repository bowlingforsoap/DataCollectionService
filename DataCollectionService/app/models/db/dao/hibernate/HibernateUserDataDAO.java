package models.db.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import play.db.jpa.JPA;
import models.db.dao.UserDataDAO;
import models.db.entity.UserData;

public class HibernateUserDataDAO extends UserDataDAO {

	@Override
	public void create(UserData entity) {
		EntityManager em = JPA.em();
/*
		try {
			em.getTransaction().begin();
		} catch (IllegalStateException e) {
			em.getTransaction().rollback();
			this.create(entity);
		}

		try {
*/			em.persist(entity);
		/*	em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public UserData read(long id) {
		return JPA.em().find(UserData.class, id);
	}

	@Override
	public boolean update(UserData entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(long id) {
		EntityManager em = JPA.em();

		UserData user = em.find(UserData.class, id);

		if (user != null) {
		/*	try {
				em.getTransaction().begin();
			} catch (IllegalStateException e) {
				em.getTransaction().rollback();
				this.delete(id);
			}
			try {
		*/		em.remove(user);
		/*		em.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}*/
		} else {
			return false;
		}
		return true;
	}

	@Override
	public List<UserData> readAll() {
		return JPA.em().createQuery("SELECT u FROM USERS u", UserData.class)
				.getResultList();
	}

	@Override
	public boolean deleteAll() {
		try {
			JPA.em().createQuery("DELETE FROM VALUES v").executeUpdate();
			JPA.em().createQuery("DELETE FROM USERS u").executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
