package practica.tecnologias.web.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Clase de la entidad Contacto. Cada usuario tiene los suyos, puediendo ser invitados a participar en los eventos.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Entity
@Table(name = "contactos", uniqueConstraints = {@UniqueConstraint(columnNames = {"alias", "usuario_id"}),
												@UniqueConstraint(columnNames = {"email", "usuario_id"})})
public class Contacto implements Serializable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The alias. */
	@NotBlank
	@Column(length = 30)
	private String alias;

	/** The email. */
	@NotBlank
	@Email
	private String email;

	/** The create at. */
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date createAt;

	/** The usuario. */
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	/** The eventos finales. */
	@ManyToMany(mappedBy = "asistentes")
	private Set<EventoFinal> eventosFinales;

	/**
	 * Gets the eventos finales.
	 *
	 * @return the eventos finales
	 */
	public Set<EventoFinal> getEventosFinales() {
		return eventosFinales;
	}

	/**
	 * Sets the eventos finales.
	 *
	 * @param eventosFinales the new eventos finales
	 */
	public void setEventosFinales(Set<EventoFinal> eventosFinales) {
		this.eventosFinales = eventosFinales;
	}

	/**
	 * Instantiates a new contacto.
	 *
	 * @param alias the alias
	 * @param email the email
	 * @param usuario the usuario
	 */
	public Contacto(String alias, String email, Usuario usuario) {

		this.alias = alias;
		this.email = email;
		this.usuario = usuario;
	}

	/**
	 * Instantiates a new contacto.
	 */
	public Contacto() {

	}

	/**
	 * Pre persist.
	 */
	@PrePersist
	public void prePersist() {
		createAt = new Date();
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
	 * Gets the alias.
	 *
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the alias.
	 *
	 * @param alias the new alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the usuario.
	 *
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Sets the usuario.
	 *
	 * @param usuario the new usuario
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Gets the creates the at.
	 *
	 * @return the creates the at
	 */
	public Date getCreateAt() {
		return createAt;
	}

	/**
	 * Sets the creates the at.
	 *
	 * @param createAt the new creates the at
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
