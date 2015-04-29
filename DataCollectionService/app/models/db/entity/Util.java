package models.db.entity;

/**
 * Help methods for entities.
 * 
 * @author mightychili
 *
 */
public class Util {
	public static void updateField(Field newField, Field oldField) {
		oldField.setLabel(newField.getLabel());
		oldField.setActive(newField.isActive());
		oldField.setRequired(newField.isRequired());
		oldField.setType(newField.getType());
		oldField.setOptions(newField.getOptions());
	}
}
