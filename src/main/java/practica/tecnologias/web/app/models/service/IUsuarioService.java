package practica.tecnologias.web.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import practica.tecnologias.web.app.models.entity.Contacto;
import practica.tecnologias.web.app.models.entity.Sala;
import practica.tecnologias.web.app.models.entity.Usuario;

/**
 * Interfaz de servicio IUsuarioService.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Service
public interface IUsuarioService {

	/**
	 * Find all usuarios.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Usuario> findAllUsuarios(Pageable pageable);

	/**
	 * Find all usuarios.
	 *
	 * @return the list
	 */
	public List<Usuario> findAllUsuarios();

	/**
	 * Delete usuario.
	 *
	 * @param id the id
	 */
	public void deleteUsuario(Long id);

	/**
	 * Save usuario.
	 *
	 * @param usuario the usuario
	 */
	public void saveUsuario(Usuario usuario);

	/**
	 * Find usuario by id.
	 *
	 * @param id the id
	 * @return the usuario
	 */
	public Usuario findUsuarioById(Long id);

	/**
	 * Find usuario by username.
	 *
	 * @param username the username
	 * @return the usuario
	 */
	public Usuario findUsuarioByUsername(String username);

	/**
	 * Save sala.
	 *
	 * @param sala the sala
	 */
	public void saveSala(Sala sala);

	/**
	 * Delete sala.
	 *
	 * @param id the id
	 */
	public void deleteSala(Long id);

	/**
	 * Find all salas.
	 *
	 * @return the list
	 */
	public List<Sala> findAllSalas();

	/**
	 * Find all salas.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Sala> findAllSalas(Pageable pageable);

	/**
	 * Find sala by id.
	 *
	 * @param id the id
	 * @return the sala
	 */
	public Sala findSalaById(Long id);

	/**
	 * Save contacto.
	 *
	 * @param contacto the contacto
	 */
	public void saveContacto(Contacto contacto);

	/**
	 * Delete contacto.
	 *
	 * @param id the id
	 */
	public void deleteContacto(Long id);

	/**
	 * Find all contactos.
	 *
	 * @return the list
	 */
	public List<Contacto> findAllContactos();

	/**
	 * Find all contactos.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Contacto> findAllContactos(Pageable pageable);

	/**
	 * Find all contactos by usuario.
	 *
	 * @param usuario the usuario
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Contacto> findAllContactosByUsuario(Usuario usuario, Pageable pageable);

	/**
	 * Find contacto by id.
	 *
	 * @param id the id
	 * @return the contacto
	 */
	public Contacto findContactoById(Long id);

}
