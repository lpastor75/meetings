package practica.tecnologias.web.app.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Clase entidad Horario.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Entity
@Table(name = "horarios")
public class Horario implements Serializable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The horario flow. */
	@NotNull
	@Future
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "horario_flow")
	private LocalDateTime horarioFlow;

	/** The hora fin. */
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "horario_fin")
	private LocalDateTime horaFin;

	/**
	 * Instantiates a new horario.
	 */
	public Horario() {

	}

	/**
	 * Instantiates a new horario.
	 *
	 * @param horarioFlow the horario flow
	 */
	public Horario(LocalDateTime horarioFlow) {
		this.horarioFlow = horarioFlow;
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
	 * Gets the horario flow.
	 *
	 * @return the horario flow
	 */
	public LocalDateTime getHorarioFlow() {
		return horarioFlow;
	}

	/**
	 * Sets the horario flow.
	 *
	 * @param horarioFlow the new horario flow
	 */
	public void setHorarioFlow(LocalDateTime horarioFlow) {
		this.horarioFlow = horarioFlow;
	}

	/**
	 * Gets the hora fin.
	 *
	 * @return the hora fin
	 */
	public LocalDateTime getHoraFin() {
		return horaFin;
	}

	/**
	 * Sets the hora fin.
	 *
	 * @param horaFin the new hora fin
	 */
	public void setHoraFin(LocalDateTime horaFin) {
		this.horaFin = horaFin;
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
