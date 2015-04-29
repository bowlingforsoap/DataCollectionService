package models.db.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Used to store values on a user-created {@link Field}.
 * 
 * @author mightychili
 *
 */
@Entity(name = "VALUES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Value implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	@Column(nullable=false)
	private String value;
	@ManyToOne(fetch = FetchType.LAZY)
	private Field field;

	public long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	/**
	 * Sets the <b><code>this.value</code></b>, if field is not null and if <b>
	 * <code>field.options</code></b> contain the given <b>value</b> parameter.
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		if (field == null && !field.getOptions().contains(value))
			return;
		this.value = value;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	@Override
	public String toString() {
		return "Value [id=" + id + ", value=" + value + ", field=" + field
				+ "]";
	}
}
