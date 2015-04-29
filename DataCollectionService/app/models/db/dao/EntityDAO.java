package models.db.dao;

import java.util.List;

import play.db.jpa.Transactional;

/**
 * Abstract DAO manipulations class.
 * 
 * @author mightychili
 *
 * @param <T>
 */
public interface EntityDAO<T> {
	void create(T entity);

	T read(long id);

	boolean update(T entity);

	boolean delete(long id);

	List<T> readAll();

	boolean deleteAll();
}
