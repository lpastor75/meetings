package practica.tecnologias.web.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Clase entidad Role. Roles que pueden ser asociados con los usuarios registrados en la aplicación. 
 * Cada Role concede una serie de privilegios a los usuarios.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Entity
@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority"})})
public class Role implements Serializable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The authority. */
	private String authority;

	/**
	 * Instantiates a new role.
	 *
	 * @param authority the authority
	 */
	public Role(String authority) {
		super();
		this.authority = authority;
	}

	/**
	 * Instantiates a new role.
	 */
	public Role() {

	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the authority.
	 *
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * Sets the authority.
	 *
	 * @param authority the new authority
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
}
