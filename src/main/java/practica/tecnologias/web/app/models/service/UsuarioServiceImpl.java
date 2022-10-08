package practica.tecnologias.web.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import practica.tecnologias.web.app.models.dao.IContactoDao;
import practica.tecnologias.web.app.models.dao.ISalaDao;
import practica.tecnologias.web.app.models.dao.IUsuarioDao;
import practica.tecnologias.web.app.models.entity.Contacto;
import practica.tecnologias.web.app.models.entity.Sala;
import practica.tecnologias.web.app.models.entity.Usuario;

/**
 * Clase de servicio para el manejo de las funcionalidades de Usuarios que implenta
 * la interfaz IUsuarioService.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Service
public class UsuarioServiceImpl implements IUsuarioService {

	/** The usuario dao. */
	@Autowired
	private IUsuarioDao usuarioDao;

	/** The sala dao. */
	@Autowired
	private ISalaDao salaDao;

	/** The contacto dao. */
	@Autowired
	private IContactoDao contactoDao;

	/**
	 * Save usuario.
	 *
	 * @param usuario the usuario
	 */
	@Override
	@Transactional
	public void saveUsuario(Usuario usuario) {
		usuarioDao.save(usuario);
	}

	/**
	 * Delete usuario.
	 *
	 * @param id the id
	 */
	@Override
	@Transactional
	public void deleteUsuario(Long id) {
		usuarioDao.deleteById(id);
	}

	/**
	 * Find all usuarios.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAllUsuarios() {
		return usuarioDao.findAll();
	}

	/**
	 * Find all usuarios.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAllUsuarios(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}

	/**
	 * Find usuario by id.
	 *
	 * @param id the id
	 * @return the usuario
	 */
	@Override
	@Transactional(readOnly = true)
	public Usuario findUsuarioById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	/**
	 * Find usuario by username.
	 *
	 * @param username the username
	 * @return the usuario
	 */
	@Override
	@Transactional(readOnly = true)
	public Usuario findUsuarioByUsername(String username) {

		return usuarioDao.findByUsername(username);
	}

	/**
	 * Save sala.
	 *
	 * @param sala the sala
	 */
	@Override
	@Transactional
	public void saveSala(Sala sala) {
		salaDao.save(sala);
	}

	/**
	 * Delete sala.
	 *
	 * @param id the id
	 */
	@Override
	@Transactional
	public void deleteSala(Long id) {
		salaDao.deleteById(id);
	}

	/**
	 * Find all salas.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Sala> findAllSalas() {
		return salaDao.findAll();
	}

	/**
	 * Find all salas.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Sala> findAllSalas(Pageable pageable) {
		return salaDao.findAll(pageable);
	}

	/**
	 * Find sala by id.
	 *
	 * @param id the id
	 * @return the sala
	 */
	@Override
	@Transactional(readOnly = true)
	public Sala findSalaById(Long id) {
		return salaDao.findById(id).orElse(null);
	}

	/**
	 * Save contacto.
	 *
	 * @param contacto the contacto
	 */
	@Override
	@Transactional
	public void saveContacto(Contacto contacto) {
		contactoDao.save(contacto);

	}

	/**
	 * Delete contacto.
	 *
	 * @param id the id
	 */
	@Override
	@Transactional
	public void deleteContacto(Long id) {
		contactoDao.deleteById(id);
	}

	/**
	 * Find all contactos.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Contacto> findAllContactos() {
		return contactoDao.findAll();
	}

	/**
	 * Find all contactos.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Contacto> findAllContactos(Pageable pageable) {
		return contactoDao.findAll(pageable);
	}

	/**
	 * Find contacto by id.
	 *
	 * @param id the id
	 * @return the contacto
	 */
	@Override
	@Transactional(readOnly = true)
	public Contacto findContactoById(Long id) {
		return contactoDao.findById(id).orElse(null);
	}

	/**
	 * Find all contactos by usuario.
	 *
	 * @param usuario the usuario
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Contacto> findAllContactosByUsuario(Usuario usuario, Pageable pageable) {

		return contactoDao.findByUsuario(usuario, pageable);
	}

}
