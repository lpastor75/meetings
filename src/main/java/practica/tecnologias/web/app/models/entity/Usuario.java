package practica.tecnologias.web.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


/**
 * Clase entidad del Usuario. Interactuan con la aplicación.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The username. */
	@NotBlank
	@Column(length = 30, unique = true)
	private String username;

	/** The password. */
	@NotBlank
	@Column(length = 60)
	private String password;

	/** The nombre. */
	@NotBlank
	private String nombre;

	/** The apellido. */
	@NotBlank
	private String apellido;

	/** The email. */
	@NotEmpty
	@Email
	private String email;

	/** The poblacion. */
	@NotBlank
	private String poblacion;

	/** The pais. */
	@NotBlank
	private String pais;

	/** The telefono. */
	@NotBlank
	private String telefono;

	/** The enabled. */
	private Boolean enabled;

	/** The is jefe. */
	private Boolean isJefe;
	
	/** The roles. */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<Role> roles; 

	/** The contactos. */
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<@Valid Contacto> contactos;
	
	/** The invitaciones eventos. */
	@ManyToMany(mappedBy = "invitados")
	private Set<EventoBase> invitacionesEventos;
	
	/**
	 * Instantiates a new usuario.
	 *
	 * @param username the username
	 * @param password the password
	 * @param nombre the nombre
	 * @param apellido the apellido
	 * @param email the email
	 * @param poblacion the poblacion
	 * @param pais the pais
	 * @param telefono the telefono
	 * @param enabled the enabled
	 * @param isJefe the is jefe
	 * @param roles the roles
	 * @param contactos the contactos
	 * @param invitacionesEventos the invitaciones eventos
	 */
	public Usuario (String username, String password, String nombre, String apellido, 
			String email, String poblacion, String pais, String telefono, 
			Boolean enabled, Boolean isJefe, List<Role> roles, Set<Contacto> contactos,
			Set<EventoBase>invitacionesEventos) {
		
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.poblacion = poblacion;
		this.pais = pais;
		this.telefono = telefono;
		this.enabled = enabled;
		this.isJefe = isJefe;
		this.roles = roles;
		this.contactos = contactos;
		this.invitacionesEventos = invitacionesEventos;	
	}

	/**
	 * Instantiates a new usuario.
	 */
	public Usuario() {

		enabled = true;
		roles = new ArrayList<Role>();
		contactos = new HashSet<Contacto>();
		isJefe = false;
		roles.add(new Role("ROLE_USER"));
	}
	
	/**
	 * Adds the role.
	 *
	 * @param role the role
	 */
	public void addRole(Role role) {

		roles.add(role);
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
	 * Gets the apellido.
	 *
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * Sets the apellido.
	 *
	 * @param apellido the new apellido
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
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
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * Gets the poblacion.
	 *
	 * @return the poblacion
	 */
	public String getPoblacion() {
		return poblacion;
	}

	/**
	 * Sets the poblacion.
	 *
	 * @param poblacion the new poblacion
	 */
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	/**
	 * Gets the pais.
	 *
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * Sets the pais.
	 *
	 * @param pais the new pais
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Gets the telefono.
	 *
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Sets the telefono.
	 *
	 * @param telefono the new telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the checks if is jefe.
	 *
	 * @return the checks if is jefe
	 */
	public Boolean getIsJefe() {
		return isJefe;
	}

	/**
	 * Sets the checks if is jefe.
	 *
	 * @param isJefe the new checks if is jefe
	 */
	public void setIsJefe(Boolean isJefe) {
		this.isJefe = isJefe;
	}
	
	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles the new roles
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Gets the contactos.
	 *
	 * @return the contactos
	 */
	public Set<Contacto> getContactos() {
		return contactos;
	}

	/**
	 * Sets the contactos.
	 *
	 * @param contactos the new contactos
	 */
	public void setContactos(Set<Contacto> contactos) {
		this.contactos = contactos;
	}
	
	/**
	 * Gets the invitaciones eventos.
	 *
	 * @return the invitaciones eventos
	 */
	public Set<EventoBase> getInvitacionesEventos() {
		return invitacionesEventos;
	}

	/**
	 * Sets the invitaciones eventos.
	 *
	 * @param invitacionesEventos the new invitaciones eventos
	 */
	public void setInvitacionesEventos(Set<EventoBase> invitacionesEventos) {
		this.invitacionesEventos = invitacionesEventos;
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
