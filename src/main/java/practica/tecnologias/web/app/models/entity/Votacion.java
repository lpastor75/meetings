package practica.tecnologias.web.app.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Clase Entidad Votacion que representa los votos de los usuarios sobre sus preferencias respecto a la fecha y 
 * el horario de los eventos.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Table(name = "votaciones", uniqueConstraints = {@UniqueConstraint(columnNames = {"evento_id", "invitado_id"})})
@Entity
public class Votacion {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The evento id. */
	@Column(name = "evento_id")
	private Long eventoId;

	/** The invitado id. */
	@Column(name = "invitado_id")
	private Long invitadoId;

	/** The horario seleccionado id. */
	@Column(name = "horario_seleccionado_id")
	private Long horarioSeleccionadoId;

	/**
	 * Instantiates a new votacion.
	 *
	 * @param eventoId the evento id
	 * @param invitadoId the invitado id
	 * @param horarioSeleccionadoId the horario seleccionado id
	 */
	public Votacion(Long eventoId, Long invitadoId, Long horarioSeleccionadoId) {
		super();
		this.eventoId = eventoId;
		this.invitadoId = invitadoId;
		this.horarioSeleccionadoId = horarioSeleccionadoId;
	}

	/**
	 * Instantiates a new votacion.
	 */
	public Votacion() {

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
	 * Gets the evento id.
	 *
	 * @return the evento id
	 */
	public Long getEventoId() {
		return eventoId;
	}

	/**
	 * Sets the evento id.
	 *
	 * @param eventoId the new evento id
	 */
	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}

	/**
	 * Gets the invitado id.
	 *
	 * @return the invitado id
	 */
	public Long getInvitadoId() {
		return invitadoId;
	}

	/**
	 * Sets the invitado id.
	 *
	 * @param invitadoId the new invitado id
	 */
	public void setInvitadoId(Long invitadoId) {
		this.invitadoId = invitadoId;
	}

	/**
	 * Gets the horario seleccionado id.
	 *
	 * @return the horario seleccionado id
	 */
	public Long getHorarioSeleccionadoId() {
		return horarioSeleccionadoId;
	}

	/**
	 * Sets the horario seleccionado id.
	 *
	 * @param horarioSeleccionadoId the new horario seleccionado id
	 */
	public void setHorarioSeleccionadoId(Long horarioSeleccionadoId) {
		this.horarioSeleccionadoId = horarioSeleccionadoId;
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
