package practica.tecnologias.web.app.models.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Clase abstracta de la entidad EventoBase (utilizada para solucionar ManytoMany en Hibernate), 
 * que es la clase abstracta de donde heredan la clase evento y eventoFinal .
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Entity
@Table(name = "eventos_base")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class EventoBase implements Serializable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The titulo. */
	@NotBlank
	@Column(length = 60)
	private String titulo;

	/** The descripcion. */
	private String descripcion;

	/** The organizador. */
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario organizador;

	/** The duracion. */
	@NotNull
	private int duracion;

	/** The invitados. */
	@ManyToMany(fetch = FetchType.EAGER,
		cascade = {
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.REFRESH
		})
	@JoinTable(name = "invitados",
		inverseJoinColumns = @JoinColumn(name = "usuario_id"),
		joinColumns = @JoinColumn(name = "evento_base_id"))
	private Set<@Valid Usuario > invitados;

	/**
	 * Instantiates a new evento base.
	 */
	public EventoBase() {

	}

	/**
	 * Instantiates a new evento base.
	 *
	 * @param titulo the titulo
	 * @param descripcion the descripcion
	 * @param organizador the organizador
	 * @param duracion the duracion
	 * @param invitados the invitados
	 */
	public EventoBase(String titulo, String descripcion, Usuario organizador, int duracion, Set<Usuario> invitados) {

		this.titulo = titulo;
		this.descripcion = descripcion;
		this.organizador = organizador;
		this.duracion = duracion;
		this.invitados = invitados;

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
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the titulo.
	 *
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Sets the titulo.
	 *
	 * @param titulo the new titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion the new descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Gets the organizador.
	 *
	 * @return the organizador
	 */
	public Usuario getOrganizador() {
		return organizador;
	}

	/**
	 * Sets the organizador.
	 *
	 * @param organizador the new organizador
	 */
	public void setOrganizador(Usuario organizador) {
		this.organizador = organizador;
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
	 * Gets the invitados.
	 *
	 * @return the invitados
	 */
	public Set<Usuario> getInvitados() {
		return invitados;
	}

	/**
	 * Sets the invitados.
	 *
	 * @param invitados the new invitados
	 */
	public void setInvitados(Set<Usuario> invitados) {
		this.invitados = invitados;
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
