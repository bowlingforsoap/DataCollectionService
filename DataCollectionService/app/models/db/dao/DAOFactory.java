package models.db.dao;

public abstract class DAOFactory {
	/**
	 * Singleton instance of a DAOFactory.
	 */
	private static DAOFactory instance;
	/**
	 * DAOFactory name.
	 */
	private static String daoFactoryFCN;

	protected DAOFactory() {}
	
	public static synchronized DAOFactory getInstance() throws Exception {
		if (instance == null) {
			Class<?> clazz = Class.forName(DAOFactory.daoFactoryFCN);
			instance = (DAOFactory) clazz.newInstance();
		}
		return instance;
	}


	/**
	 * Reset factory for another source. 
	 * @param daoFactoryFCN
	 */
	public static void setDaoFactoryFCN(String daoFactoryFCN) {
		if (daoFactoryFCN != null && daoFactoryFCN.length() != 0) {
			instance = null;
			DAOFactory.daoFactoryFCN = daoFactoryFCN;
		}
	}

	public abstract FieldDAO getFieldDAO();
	public abstract ValueDAO getValueDAO();
	public abstract UserDataDAO getUserDataDAO();
}
