package models.db.dao.hibernate;

import models.db.dao.DAOFactory;
import models.db.dao.FieldDAO;
import models.db.dao.UserDataDAO;
import models.db.dao.ValueDAO;

public class HibernateDAOFactory extends DAOFactory {

	@Override
	public FieldDAO getFieldDAO() {
		return new HibernateFieldDAO();
	}

	@Override
	public ValueDAO getValueDAO() {
		return new HibernateValueDAO();
	}

	@Override
	public UserDataDAO getUserDataDAO() {
		return new HibernateUserDataDAO();
	}

}
