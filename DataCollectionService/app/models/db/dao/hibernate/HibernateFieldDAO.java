package models.db.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import play.Logger;
import play.db.jpa.JPA;
import models.db.dao.FieldDAO;
import models.db.entity.Field;
import models.db.entity.Util;

public class HibernateFieldDAO extends FieldDAO {

	@Override
	public void create(Field entity) {
		EntityManager em = JPA.em();

//		try {
//			em.getTransaction().begin();
/*		} catch (IllegalStateException e) {
			em.getTransaction().rollback();
			this.create(entity);
		}
*/
//		try {
			em.persist(entity);
//			em.getTransaction().commit();
		/*
		 * } catch (Exception e) { e.printStackTrace(); }
		 */	}

	@Override
	public Field read(long id) {
		return JPA.em().find(Field.class, id);
	}

	@Override
	public boolean update(Field entity) {
		EntityManager em = JPA.em();
		Field inDBField = em.find(Field.class, entity.getId());

//		try {
//			em.getTransaction().begin();
/*		} catch (IllegalStateException e) {
			em.getTransaction().rollback();
			this.update(entity);
		}

		try {
*/			Util.updateField(entity, inDBField);
/*			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
*/		return true;
	}

	@Override
	public boolean delete(long id) {
		EntityManager em = JPA.em();
		Field field = em.find(Field.class, id);
		
		if (field != null) {
			Query deleteFieldValues = em
					.createQuery("DELETE FROM VALUES v WHERE v.field.id = :p");

//			try {
//				em.getTransaction().begin();
/*			} catch (IllegalStateException e) {
				em.getTransaction().rollback();
				this.delete(id);
			}
			
			try {
*/				deleteFieldValues.setParameter("p", id).executeUpdate();
				em.remove(field);
//				em.getTransaction().commit();
/*			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
*/		} else {
			return false;
		}

		return true;
	}

	@Override
	public List<Field> readAll() {
		return JPA.em().createQuery("SELECT f FROM FIELDS f", Field.class)
				.getResultList();
	}

	@Override
	public boolean deleteAll() {
		try {
			//JPA.em().createQuery("DELETE FROM FIELD_OPTIONS fo").executeUpdate();
			JPA.em().createQuery("DELETE FROM FIELDS f").executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
