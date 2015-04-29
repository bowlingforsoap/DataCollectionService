package controllers;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import controllers.utils.Util;
import models.db.dao.DAOFactory;
import models.db.dao.FieldDAO;
import models.db.dao.UserDataDAO;
import models.db.dao.ValueDAO;
import models.db.entity.Field;
import models.db.entity.UserData;
import models.db.entity.Value;
import play.Logger;
import play.mvc.*;
import views.html.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.*;
import play.libs.Json;
import scala.annotation.meta.field;

import java.util.UUID;

public class Application extends Controller {
	static {
		DAOFactory
				.setDaoFactoryFCN("models.db.dao.hibernate.HibernateDAOFactory");
	}

	public static Result index() throws Exception {
		return ok(index.render());
	}
	
	@Transactional
	public static Result serveIndexJson() throws Exception {
		List<Field> allFields = DAOFactory.getInstance().getFieldDAO().readAll();
		ObjectNode result = Json.newObject();
		
		result.put("allFields", Json.toJson(allFields));
		return ok(result);
	}

	public static Result responses() throws Exception {
		return ok(responses.render());
	}
	
	@Transactional(readOnly=true)
	public static Result serveResponsesJson() throws Exception {
		List<Field> allFields = DAOFactory.getInstance().getFieldDAO()
				.readAll();
		List<UserData> allUsers = DAOFactory.getInstance().getUserDataDAO()
				.readAll();
		ObjectNode result = Json.newObject();

		result.put("allFields", Json.toJson(allFields));
		result.put("allUsers", Json.toJson(allUsers));
		return ok(result);
	}

	public static Result fields() throws Exception {
		return ok(fields.render());
	}

	@Transactional(readOnly=true)
	public static Result serveFieldsJson() throws Exception {
		List<Field> allFields = DAOFactory.getInstance().getFieldDAO()
				.readAll();
		ObjectNode result = Json.newObject();

		result.put("allFields", Json.toJson(allFields));

		return ok(result);
	}

	/**
	 * Redirects to create_edit_field html page with the generated UUID.
	 * 
	 * @return
	 */
	public static Result addField() {
		return redirect("/fields/" + UUID.randomUUID().getMostSignificantBits());
	}

	@Transactional
	public static Result deleteField(Long id) throws Exception {
		Logger.debug("deleting " + id);
		DAOFactory.getInstance().getFieldDAO().delete(id);
		return redirect(routes.Application.fields());
	}

	public static Result createOrEditField(Long id) throws Exception {
		if (id == null) {
			id = UUID.randomUUID().getMostSignificantBits();
		}

		return ok(create_edit_field.render(id));
	}

	@Transactional
	public static Result serveCreateOrEditFieldJson(Long id) throws Exception {
		Field fieldToEdit = null;
		ObjectNode result = Json.newObject();

		if (id != null) {
			fieldToEdit = DAOFactory.getInstance().getFieldDAO().read(id);

			// in case nothing was found in DB
			if (fieldToEdit == null) {
				fieldToEdit = new Field();
				fieldToEdit.setId(id);
			}
		}

		result.put("fieldToEdit", Json.toJson(fieldToEdit));
		return ok(result);
	}

	@Transactional
	public static Result createOrEditFieldPost() throws Exception {
		DynamicForm dForm = Form.form().bindFromRequest();
		Field field = Util.getFieldFromMap(dForm.data());

		if (Boolean.valueOf(dForm.data().get("present-in-db"))) {
			DAOFactory.getInstance().getFieldDAO().update(field);
		} else {
			DAOFactory.getInstance().getFieldDAO().create(field);
		}
		return redirect(routes.Application.fields());
	}
}
