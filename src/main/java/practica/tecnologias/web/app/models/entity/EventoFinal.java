package practica.tecnologias.web.app.models.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 * Clase entidad del EventoFinal, que representa a los eventos cuando ya están totálmente configurados
 * para poder ser celebrados.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Entity
@Table(name = "eventos_finales")
public class EventoFinal extends EventoBase {

	/** The sala. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sala")
	private Sala sala;

	/** The horario definitivo. */
	private LocalDateTime horarioDefinitivo;

	/** The horario definitivo fin. */
	private LocalDateTime horarioDefinitivoFin;

	/** The evento finalizado. */
	private boolean eventoFinalizado;

	/** The Evento padre. */
	private Long EventoPadre;

	/** The vacantes. */
	private int vacantes = -1;

	/** The salas disponibles. */
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "evento_final")
	private List<@Valid Sala > salasDisponibles;

	/** The asistentes. */
	@ManyToMany(fetch = FetchType.EAGER,
		cascade = {
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.REFRESH
		})
	@JoinTable(name = "asistentes",
		inverseJoinColumns = @JoinColumn(name = "contacto_id"),
		joinColumns = @JoinColumn(name = "evento_final_id"))
	private Set<@Valid Contacto > asistentes;

	/**
	 * Instantiates a new evento final.
	 *
	 * @param titulo the titulo
	 * @param descripcion the descripcion
	 * @param organizador the organizador
	 * @param duracion the duracion
	 * @param invitados the invitados
	 * @param horarioDefinitivo the horario definitivo
	 */
	public EventoFinal(String titulo, String descripcion, Usuario organizador, int duracion, Set<Usuario> invitados,
		LocalDateTime horarioDefinitivo) {

		super(titulo, descripcion, organizador, duracion, invitados);

		this.horarioDefinitivo = horarioDefinitivo;

		this.horarioDefinitivoFin = setHorarioDefinitivoFinal(horarioDefinitivo);

	}

	/**
	 * Instantiates a new evento final.
	 */
	public EventoFinal() {

	}

	/**
	 * Adds the asistente.
	 *
	 * @param contacto the contacto
	 */
	public void addAsistente(@Valid Contacto contacto) {

		asistentes.add(contacto);
	}

	/**
	 * Sets the horario definitivo final.
	 *
	 * @param horario the horario
	 * @return the local date time
	 */
	public LocalDateTime setHorarioDefinitivoFinal(LocalDateTime horario) {

		return this.horarioDefinitivoFin = horario.plusHours(super.getDuracion());

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

	/**
	 * Gets the horario definitivo.
	 *
	 * @return the horario definitivo
	 */
	public LocalDateTime getHorarioDefinitivo() {
		return horarioDefinitivo;
	}

	/**
	 * Sets the horario definitivo.
	 *
	 * @param horarioDefinitivo the new horario definitivo
	 */
	public void setHorarioDefinitivo(LocalDateTime horarioDefinitivo) {
		this.horarioDefinitivo = horarioDefinitivo;
	}

	/**
	 * Gets the salas disponibles.
	 *
	 * @return the salas disponibles
	 */
	public List<Sala> getSalasDisponibles() {
		return salasDisponibles;
	}

	/**
	 * Sets the salas disponibles.
	 *
	 * @param salasDisponibles the new salas disponibles
	 */
	public void setSalasDisponibles(List<Sala> salasDisponibles) {
		this.salasDisponibles = salasDisponibles;
	}

	/**
	 * Gets the horario definitivo fin.
	 *
	 * @return the horario definitivo fin
	 */
	public LocalDateTime getHorarioDefinitivoFin() {
		return horarioDefinitivoFin;
	}

	/**
	 * Sets the horario definitivo fin.
	 *
	 * @param horarioDefinitivoFin the new horario definitivo fin
	 */
	public void setHorarioDefinitivoFin(LocalDateTime horarioDefinitivoFin) {
		this.horarioDefinitivoFin = horarioDefinitivoFin;
	}

	/**
	 * Checks if is evento finalizado.
	 *
	 * @return true, if is evento finalizado
	 */
	public boolean isEventoFinalizado() {
		return eventoFinalizado;
	}

	/**
	 * Sets the evento finalizado.
	 *
	 * @param eventoFinalizado the new evento finalizado
	 */
	public void setEventoFinalizado(boolean eventoFinalizado) {
		this.eventoFinalizado = eventoFinalizado;
	}

	/**
	 * Gets the asistentes.
	 *
	 * @return the asistentes
	 */
	public Set<Contacto> getAsistentes() {
		return asistentes;
	}

	/**
	 * Sets the asistentes.
	 *
	 * @param asistentes the new asistentes
	 */
	public void setAsistentes(Set<Contacto> asistentes) {
		this.asistentes = asistentes;
	}

	/**
	 * Gets the evento padre.
	 *
	 * @return the evento padre
	 */
	public Long getEventoPadre() {
		return EventoPadre;
	}

	/**
	 * Sets the evento padre.
	 *
	 * @param eventoPadre the new evento padre
	 */
	public void setEventoPadre(Long eventoPadre) {
		EventoPadre = eventoPadre;
	}

	/**
	 * Gets the vacantes.
	 *
	 * @return the vacantes
	 */
	public int getVacantes() {
		return vacantes;
	}

	/**
	 * Sets the vacantes.
	 *
	 * @param vacantes the new vacantes
	 */
	public void setVacantes(int vacantes) {
		this.vacantes = vacantes;
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
