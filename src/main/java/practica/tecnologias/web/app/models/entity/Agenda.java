package practica.tecnologias.web.app.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Clase de la entidad Agenda, relacionada con las salas y su disponibilidad para celebrar un evento.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Entity
@Table(name = "agendas")
public class Agenda implements Serializable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/** The horario inicio. */
	private LocalDateTime horarioInicio;

	/** The horario fin. */
	private LocalDateTime horarioFin;

	/** The duracion. */
	private int duracion;

	/** The sala. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sala")
	private Sala sala;

	/**
	 * Instantiates a new agenda.
	 */
	public Agenda() {}

	/**
	 * Instantiates a new agenda.
	 *
	 * @param horarioInicio the horario inicio
	 * @param horarioFin the horario fin
	 * @param duracion the duracion
	 * @param sala the sala
	 */
	public Agenda(LocalDateTime horarioInicio, LocalDateTime horarioFin, int duracion, Sala sala) {
		this.horarioInicio = horarioInicio;
		this.horarioFin = horarioFin;
		this.duracion = duracion;
		this.sala = sala;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the horario inicio.
	 *
	 * @return the horario inicio
	 */
	public LocalDateTime getHorarioInicio() {
		return horarioInicio;
	}

	/**
	 * Sets the horario inicio.
	 *
	 * @param horarioInicio the new horario inicio
	 */
	public void setHorarioInicio(LocalDateTime horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	/**
	 * Gets the horario fin.
	 *
	 * @return the horario fin
	 */
	public LocalDateTime getHorarioFin() {
		return horarioFin;
	}

	/**
	 * Sets the horario fin.
	 *
	 * @param horarioFin the new horario fin
	 */
	public void setHorarioFin(LocalDateTime horarioFin) {
		this.horarioFin = horarioFin;
	}

	/**
	 * Gets the duracion.
	 *
	 * @return the duracion
	 */
	public int getDuracion() {
		return duracion;
	}

	/**
	 * Sets the duracion.
	 *
	 * @param duracion the new duracion
	 */
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	/**
	 * Gets the sala.
	 *
	 * @return the sala
	 */
	public Sala getSala() {
		return sala;
	}

	/**
	 * Sets the sala.
	 *
	 * @param sala the new sala
	 */
	public void setSala(Sala sala) {
		this.sala = sala;
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}