package practica.tecnologias.web.app.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Clase entidad Mensaje del chat.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Entity
@Table(name = "mensajes")
public class Mensaje implements Serializable{

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mensaje_id", updatable = false, nullable = false)
	private Long id;

	/** The content. */
	@Column(name = "content")
	private String content;

	/** The evento final id. */
	@Column(name = "eventoFinal_id")
	private Long eventoFinal_id;

	/** The username. */
	@Column(name = "username")
	private String username;

	/** The create at. */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createAt = LocalDateTime.now();

	/**
	 * Gets the creates the at.
	 *
	 * @return the creates the at
	 */
	public LocalDateTime getCreateAt() {
		return createAt;
	}

	/**
	 * Sets the creates the at.
	 *
	 * @param createAt the new creates the at
	 */
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
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
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the evento final id.
	 *
	 * @return the evento final id
	 */
	public Long getEventoFinal_id() {
		return eventoFinal_id;
	}

	/**
	 * Sets the evento final id.
	 *
	 * @param eventoFinal_id the new evento final id
	 */
	public void setEventoFinal_id(Long eventoFinal_id) {
		this.eventoFinal_id = eventoFinal_id;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
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
