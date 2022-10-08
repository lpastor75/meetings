package practica.tecnologias.web.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Clase entidad de la Sala, que es el lugar físico donde una reunión es celebrada.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Entity
@Table(name = "salas")
public class Sala implements Serializable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The nombre. */
	@NotBlank
	@Column(length = 30, unique = true)
	private String nombre;

	/** The aforo. */
	@Max(45)
	@Min(5)
	private int aforo;

	/** The disponible. */
	private boolean disponible;

	/** The streaming. */
	private boolean streaming;

	/** The wifi. */
	private boolean wifi;

	/** The grabacion. */
	private boolean grabacion;

	/** The megafonia. */
	private boolean megafonia;

	/** The presentacion. */
	private boolean presentacion;

	/** The apertura. */
	@DateTimeFormat(pattern = "HH:mm")
	@Temporal(TemporalType.TIME)
	private Date apertura;

	/** The cierre. */
	@DateTimeFormat(pattern = "HH:mm")
	@Temporal(TemporalType.TIME)
	private Date cierre;

	/** The agendas. */
	@OneToMany(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		mappedBy = "sala"
	)
	private Set<Agenda> agendas = new HashSet<>();

	/** The eventos finales. */
	@OneToMany(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		mappedBy = "sala")
	private Set<EventoFinal> eventosFinales = new HashSet<>();

	/**
	 * Instantiates a new sala.
	 */
	public Sala() {

		disponible = true;
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
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the aforo.
	 *
	 * @return the aforo
	 */
	public int getAforo() {
		return aforo;
	}

	/**
	 * Sets the aforo.
	 *
	 * @param aforo the new aforo
	 */
	public void setAforo(int aforo) {
		this.aforo = aforo;
	}

	/**
	 * Checks if is disponible.
	 *
	 * @return true, if is disponible
	 */
	public boolean isDisponible() {
		return disponible;
	}

	/**
	 * Sets the disponible.
	 *
	 * @param disponible the new disponible
	 */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	/**
	 * Checks if is streaming.
	 *
	 * @return true, if is streaming
	 */
	public boolean isStreaming() {
		return streaming;
	}

	/**
	 * Sets the streaming.
	 *
	 * @param streaming the new streaming
	 */
	public void setStreaming(boolean streaming) {
		this.streaming = streaming;
	}

	/**
	 * Checks if is wifi.
	 *
	 * @return true, if is wifi
	 */
	public boolean isWifi() {
		return wifi;
	}

	/**
	 * Sets the wifi.
	 *
	 * @param wifi the new wifi
	 */
	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	/**
	 * Checks if is grabacion.
	 *
	 * @return true, if is grabacion
	 */
	public boolean isGrabacion() {
		return grabacion;
	}

	/**
	 * Sets the grabacion.
	 *
	 * @param grabacion the new grabacion
	 */
	public void setGrabacion(boolean grabacion) {
		this.grabacion = grabacion;
	}

	/**
	 * Checks if is megafonia.
	 *
	 * @return true, if is megafonia
	 */
	public boolean isMegafonia() {
		return megafonia;
	}

	/**
	 * Sets the megafonia.
	 *
	 * @param megafonia the new megafonia
	 */
	public void setMegafonia(boolean megafonia) {
		this.megafonia = megafonia;
	}

	/**
	 * Checks if is presentacion.
	 *
	 * @return true, if is presentacion
	 */
	public boolean isPresentacion() {
		return presentacion;
	}

	/**
	 * Sets the presentacion.
	 *
	 * @param presentacion the new presentacion
	 */
	public void setPresentacion(boolean presentacion) {
		this.presentacion = presentacion;
	}

	/**
	 * Gets the apertura.
	 *
	 * @return the apertura
	 */
	public Date getApertura() {
		return apertura;
	}

	/**
	 * Sets the apertura.
	 *
	 * @param apertura the new apertura
	 */
	public void setApertura(Date apertura) {
		this.apertura = apertura;
	}

	/**
	 * Gets the cierre.
	 *
	 * @return the cierre
	 */
	public Date getCierre() {
		return cierre;
	}

	/**
	 * Sets the cierre.
	 *
	 * @param cierre the new cierre
	 */
	public void setCierre(Date cierre) {
		this.cierre = cierre;
	}

	/**
	 * Gets the agendas.
	 *
	 * @return the agendas
	 */
	public Set<Agenda> getAgendas() {
		return agendas;
	}

	/**
	 * Sets the agendas.
	 *
	 * @param agendas the new agendas
	 */
	public void setAgendas(Set<Agenda> agendas) {
		this.agendas = agendas;
	}

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
