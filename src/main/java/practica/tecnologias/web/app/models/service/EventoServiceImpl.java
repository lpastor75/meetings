package practica.tecnologias.web.app.models.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import practica.tecnologias.web.app.models.dao.IEventoDao;
import practica.tecnologias.web.app.models.dao.IHorarioDao;
import practica.tecnologias.web.app.models.dao.IVotacionDao;
import practica.tecnologias.web.app.models.entity.Evento;
import practica.tecnologias.web.app.models.entity.Horario;
import practica.tecnologias.web.app.models.entity.Usuario;
import practica.tecnologias.web.app.models.entity.Votacion;

/**
 * Implementación de la interfaz IEventoService.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Service
public class EventoServiceImpl implements IEventoService {

	/** The evento dao. */
	@Autowired
	private IEventoDao eventoDao;

	/** The votacion dao. */
	@Autowired
	private IVotacionDao votacionDao;

	/** The horario dao. */
	@Autowired
	private IHorarioDao horarioDao;

	/**
	 * Find all eventos.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Evento> findAllEventos() {

		return eventoDao.findAll();
	}

	/**
	 * Find all eventos by id.
	 *
	 * @param ids the ids
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Evento> findAllEventosById(Iterable<Long> ids) {

		return eventoDao.findAllById(ids);
	}

	/**
	 * Gets the one.
	 *
	 * @param id the id
	 * @return the one
	 */
	@Override
	@Transactional(readOnly = true)
	public Evento getOne(Long id) {

		return eventoDao.getOne(id);
	}

	/**
	 * Find all eventos.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Evento> findAllEventos(Pageable pageable) {

		return eventoDao.findAll(pageable);
	}

	/**
	 * Save evento.
	 *
	 * @param evento the evento
	 */
	@Override
	@Transactional
	public void saveEvento(Evento evento) {

		eventoDao.save(evento);
	}

	/**
	 * Find evento by id.
	 *
	 * @param id the id
	 * @return the evento
	 */
	@Override
	@Transactional(readOnly = true)
	public Evento findEventoById(Long id) {

		return eventoDao.findById(id).orElse(null);
	}

	/**
	 * Delete evento by id.
	 *
	 * @param id the id
	 */
	@Override
	public void deleteEventoById(Long id) {

		eventoDao.deleteById(id);
	}

	/**
	 * Delete evento.
	 *
	 * @param evento the evento
	 */
	@Override
	public void deleteEvento(Evento evento) {

		eventoDao.delete(evento);
	}

	/**
	 * Find eventos by organizador.
	 *
	 * @param organizador the organizador
	 * @return the list
	 */
	@Override
	public List<Evento> findEventosByOrganizador(Usuario organizador) {

		return eventoDao.findByOrganizador(organizador);
	}

	/**
	 * Find eventos by organizador.
	 *
	 * @param organizador the organizador
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	public Page<Evento> findEventosByOrganizador(Usuario organizador, Pageable pageable) {

		return eventoDao.findByOrganizador(organizador, pageable);
	}

	/**
	 * Save votacion.
	 *
	 * @param votacion the votacion
	 */
	@Override
	@Transactional
	public void saveVotacion(Votacion votacion) {

		votacionDao.save(votacion);
	}

	/**
	 * Find eventos by usuario id.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Evento> findEventosByUsuarioId(Long id) {

		return eventoDao.findEventosByUsuario(id);
	}

	/**
	 * Find eventos by usuario id.
	 *
	 * @param idUsuario the id usuario
	 * @param pageable the pageable
	 * @return the page
	 */
	@Transactional(readOnly = true)
	@Override
	public Page<Evento> findEventosByUsuarioId(Long idUsuario, Pageable pageable) {

		return eventoDao.findEventosByUsuario(idUsuario, pageable);
	}

	/**
	 * Find votacion by evento id.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Votacion> findVotacionByEventoId(Long id) {

		return eventoDao.findVotacionesById(id);
	}

	/**
	 * Find votacion by invitado id.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Votacion> findVotacionByInvitadoId(Long id) {

		return votacionDao.findVotacionesByInvitadoId(id);
	}

	/**
	 * Find all votaciones.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Votacion> findAllVotaciones() {

		return votacionDao.findAll();
	}

	/**
	 * Find ids votaciones invitado evento.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Long> findIdsVotacionesInvitadoEvento(Long id) {

		return votacionDao.findIdsVotacionesUsuarioEvento(id);
	}

	/**
	 * Find all horarios.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Horario> findAllHorarios() {

		return horarioDao.findAll();
	}

	/**
	 * Find horario by id.
	 *
	 * @param id the id
	 * @return the horario
	 */
	@Override
	@Transactional(readOnly = true)
	public Horario findHorarioById(Long id) {

		return horarioDao.findById(id).orElse(null);
	}

	/**
	 * Find horario by evento.
	 *
	 * @param evento the evento
	 * @return the horario
	 */
	@Override
	@Transactional(readOnly = true)
	public Horario findHorarioByEvento(Evento evento) {

		return null;
	}

	/**
	 * Save horario.
	 *
	 * @param horario the horario
	 */
	@Override
	@Transactional
	public void saveHorario(Horario horario) {

		horarioDao.save(horario);
	}

}
