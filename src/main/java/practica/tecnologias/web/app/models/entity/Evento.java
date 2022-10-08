package practica.tecnologias.web.app.models.entity;

import java.lang.String;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Clase entidad del Evento.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Entity
@Table(name = "eventos")
public class Evento extends EventoBase {

	/** The horarios disponibles. */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "evento")
	private List<@Valid Horario > horariosDisponibles;

	/** The registrados. */
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "evento")
	private List<@Valid Usuario > registrados;

	/** The fecha limite. */
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaLimite;

	/** The finalizar votacion. */
	@Column(name = "finalizar_votacion")
	private boolean finalizarVotacion;

	/** The invitaciones cerradas. */
	@Column(name = "invitaciones_cerradas")
	private boolean invitacionesCerradas;

	/** The horario final. */
	private LocalDateTime horarioFinal;

	/**
	 * Instantiates a new evento.
	 *
	 * @param titulo the titulo
	 * @param descripcion the descripcion
	 * @param organizador the organizador
	 * @param duracion the duracion
	 * @param invitados the invitados
	 * @param horariosDisponibles the horarios disponibles
	 * @param usuariosRegistrados the usuarios registrados
	 * @param fechaLimite the fecha limite
	 */
	public Evento(String titulo, String descripcion, Usuario organizador, int duracion, Set<Usuario> invitados,
		List<Horario> horariosDisponibles, List<Usuario> usuariosRegistrados, LocalDate fechaLimite) {

		super(titulo, descripcion, organizador, duracion, invitados);

		this.horariosDisponibles = horariosDisponibles;
		this.registrados = usuariosRegistrados;
		this.fechaLimite = fechaLimite;
	}

	/**
	 * Instantiates a new evento.
	 */
	public Evento() {

	}

	/**
	 * Sets the horario final.
	 */
	@PrePersist
	public void setHorarioFinal() {

		int size = horariosDisponibles.size();

		LocalDateTime horaFin;

		for (int i = 0; i<size; i++) {
			horaFin = horariosDisponibles.get(i).getHorarioFlow().plusHours(super.getDuracion());
			horariosDisponibles.get(i).setHoraFin(horaFin);
		}
	}

	/**
	 * Check fecha limite.
	 *
	 * @param localDate the local date
	 * @return true, if successful
	 */
	public boolean checkFechaLimite(LocalDate localDate) {

		this.finalizarVotacion = false;

		if (this.fechaLimite.isAfter(localDate)) {

			finalizarVotacion = true;
			return finalizarVotacion;

		} else {
			return finalizarVotacion;
		}
	}

	/**
	 * Jefe proyecto finalizar votacion.
	 *
	 * @return true, if successful
	 */
	public boolean jefeProyectoFinalizarVotacion() {

		return true;
	}

	/**
	 * Gets the horarios disponibles.
	 *
	 * @return the horarios disponibles
	 */
	public List<Horario> getHorariosDisponibles() {
		return horariosDisponibles;
	}

	/**
	 * Sets the horarios disponibles.
	 *
	 * @param horariosDisponibles the new horarios disponibles
	 */
	public void setHorariosDisponibles(List<Horario> horariosDisponibles) {
		this.horariosDisponibles = horariosDisponibles;
	}

	/**
	 * Gets the registrados.
	 *
	 * @return the registrados
	 */
	public List<Usuario> getRegistrados() {
		return registrados;
	}

	/**
	 * Sets the registrados.
	 *
	 * @param registrados the new registrados
	 */
	public void setRegistrados(List<Usuario> registrados) {
		this.registrados = registrados;
	}

	/**
	 * Gets the fecha limite.
	 *
	 * @return the fecha limite
	 */
	public LocalDate getFechaLimite() {
		return fechaLimite;
	}

	/**
	 * Sets the fecha limite.
	 *
	 * @param fechaLimite the new fecha limite
	 */
	public void setFechaLimite(LocalDate fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	/**
	 * Checks if is finalizar votacion.
	 *
	 * @return true, if is finalizar votacion
	 */
	public boolean isFinalizarVotacion() {
		return finalizarVotacion;
	}

	/**
	 * Sets the finalizar votacion.
	 *
	 * @param finalizarVotacion the new finalizar votacion
	 */
	public void setFinalizarVotacion(boolean finalizarVotacion) {
		this.finalizarVotacion = finalizarVotacion;
	}

	/**
	 * Gets the horario final.
	 *
	 * @return the horario final
	 */
	public LocalDateTime getHorarioFinal() {
		return horarioFinal;
	}

	/**
	 * Sets the horario final.
	 *
	 * @param horarioFinal the new horario final
	 */
	public void setHorarioFinal(LocalDateTime horarioFinal) {
		this.horarioFinal = horarioFinal;
	}

	/**
	 * Checks if is invitaciones cerradas.
	 *
	 * @return true, if is invitaciones cerradas
	 */
	public boolean isInvitacionesCerradas() {
		return invitacionesCerradas;
	}

	/**
	 * Sets the invitaciones cerradas.
	 *
	 * @param invitacionesCerradas the new invitaciones cerradas
	 */
	public void setInvitacionesCerradas(boolean invitacionesCerradas) {
		this.invitacionesCerradas = invitacionesCerradas;
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
