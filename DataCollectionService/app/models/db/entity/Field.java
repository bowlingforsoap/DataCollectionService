package models.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Used to store metadata on a user-created field.
 * 
 * @author mightychili
 *
 */
@Entity(name = "FIELDS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Field implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	@Column(nullable = false, unique = true)
	private String label;
	@Column(nullable = false)
	private boolean active;
	@Column(nullable = false)
	private boolean required;
	@Column(nullable = false)
	private String type;
	@ElementCollection
	@Column(name = "option")
	@CollectionTable(name = "FIELD_OPTIONS", joinColumns = @JoinColumn(name = "field_id"))
	private List<String> options = new ArrayList<>();

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "Field [id=" + id + ", label=" + label + ", active=" + active
				+ ", required=" + required + ", type=" + type + ", options="
				+ options + "]";
	}
}
