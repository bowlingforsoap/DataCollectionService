package controllers.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import play.Logger;
import models.db.dao.DAOFactory;
import models.db.dao.FieldDAO;
import models.db.dao.UserDataDAO;
import models.db.entity.Field;
import models.db.entity.UserData;
import models.db.entity.Value;

public class Util {
	public static void fillDB() throws Exception {
		DAOFactory daoFactory = DAOFactory.getInstance();
		FieldDAO fieldDAO = daoFactory.getFieldDAO();
		UserDataDAO userDataDAO = daoFactory.getUserDataDAO();

		Field nameField = new Field();
		Field surnameField = new Field();
		UserData user1 = new UserData();
		UserData user2 = new UserData();
		Value nameValue1 = new Value();
		Value nameValue2 = new Value();
		Value surnameValue1 = new Value();
		Value surnameValue2 = new Value();

		nameField.setId(5264042104724962500l);
		nameField.setLabel("Name");
		nameField.setActive(true);
		nameField.setRequired(false);
		nameField.setType("Combobox");
		nameField.setOptions(Arrays.asList("Bruce", "Harvy", "Gim"));

		surnameField.setId(-5264042104724962500l);
		surnameField.setLabel("Surname");
		surnameField.setActive(true);
		surnameField.setRequired(true);
		surnameField.setType("Radio button");
		surnameField.setOptions(Arrays.asList("Wayne", "Dent", "Gordon"));

		nameValue1.setField(nameField);
		nameValue1.setValue("Bruce");
		nameValue2.setField(nameField);
		nameValue2.setValue("Wayne");
		surnameValue1.setField(surnameField);
		surnameValue1.setValue("Gim");
		surnameValue2.setField(surnameField);
		surnameValue2.setValue("Gordon");
		user1.setValues(Arrays.asList(nameValue1, surnameValue1));
		user2.setValues(Arrays.asList(nameValue2, surnameValue2));

		play.Logger.debug("about to save " + nameField);
		fieldDAO.create(nameField);
		play.Logger.debug("saved " + nameField);
		play.Logger.debug("about to save " + surnameField);
		fieldDAO.create(surnameField);
		play.Logger.debug("saved " + nameField);
		play.Logger.debug("about to save " + user1);
		userDataDAO.create(user1);
		play.Logger.debug("saved " + user1);
		play.Logger.debug("about to save " + user2);
		userDataDAO.create(user2);
		play.Logger.debug("saved " + user2);
	}

	public static void clearDB() throws Exception {
		Logger.debug("executing #deleteAll on users");
		DAOFactory.getInstance().getUserDataDAO().deleteAll();
		Logger.debug("executing #deleteAll on fields");
		DAOFactory.getInstance().getFieldDAO().deleteAll();
	}

	public static Field getFieldFromMap(Map<String, String> form) {
		if (form != null && form.get("id") != null && form.get("label") != null
				&& form.get("type") != null) {
			Field result = new Field();

			result.setId(Long.valueOf(form.get("id")));
			result.setLabel(form.get("label"));
			result.setType(form.get("type"));
			if (form.get("required") != null) {
				result.setRequired(true);
			} else {
				result.setRequired(false);
			}
			if (form.get("active") != null) {
				result.setActive(true);
			} else {
				result.setActive(false);
			}
			if (!result.getType().equals("Textarea")
					&& !result.getType().equals("Date")) {
				List<String> options = new ArrayList<String>();
				if (form.get("options[0]") == null) {
					// there must be at least 1 option!
					return null;
				}
				options.add(form.get("options[0]"));
				for (int i = 1; i < Integer.MAX_VALUE; i++) {
					String value = form.get("options[" + i + "]");
					if (value != null) {
						options.add(value);
					} else {
						result.setOptions(options);
						break;
					}
				}
			}

			return result;
		}

		return null;
	}
}
