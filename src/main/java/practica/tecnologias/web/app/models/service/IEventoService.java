package practica.tecnologias.web.app.models.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import practica.tecnologias.web.app.models.entity.Evento;
import practica.tecnologias.web.app.models.entity.Horario;
import practica.tecnologias.web.app.models.entity.Usuario;
import practica.tecnologias.web.app.models.entity.Votacion;

/**
 * Interfaz de servicio IEventoService.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Service
public interface IEventoService {

	/**
	 * Find all eventos.
	 *
	 * @return the list
	 */
	public List<Evento> findAllEventos();

	/**
	 * Find all eventos by id.
	 *
	 * @param ids the ids
	 * @return the list
	 */
	public List<Evento> findAllEventosById(Iterable<Long> ids);

	/**
	 * Find eventos by usuario id.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<Evento> findEventosByUsuarioId(Long id);

	/**
	 * Find eventos by usuario id.
	 *
	 * @param idUsuario the id usuario
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Evento> findEventosByUsuarioId(Long idUsuario, Pageable pageable);

	/**
	 * Gets the one.
	 *
	 * @param id the id
	 * @return the one
	 */
	public Evento getOne(Long id);

	/**
	 * Find all eventos.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Evento> findAllEventos(Pageable pageable);

	/**
	 * Save evento.
	 *
	 * @param evento the evento
	 */
	public void saveEvento(Evento evento);

	/**
	 * Find evento by id.
	 *
	 * @param id the id
	 * @return the evento
	 */
	public Evento findEventoById(Long id);

	/**
	 * Delete evento by id.
	 *
	 * @param id the id
	 */
	public void deleteEventoById(Long id);

	/**
	 * Delete evento.
	 *
	 * @param entity the entity
	 */
	public void deleteEvento(Evento entity);

	/**
	 * Find eventos by organizador.
	 *
	 * @param organizador the organizador
	 * @return the list
	 */
	public List<Evento> findEventosByOrganizador(Usuario organizador);

	/**
	 * Find eventos by organizador.
	 *
	 * @param organizador the organizador
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Evento> findEventosByOrganizador(Usuario organizador, Pageable pageable);

	/**
	 * Save votacion.
	 *
	 * @param votacion the votacion
	 */
	public void saveVotacion(Votacion votacion);

	/**
	 * Find votacion by evento id.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<Votacion> findVotacionByEventoId(Long id);

	/**
	 * Find votacion by invitado id.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<Votacion> findVotacionByInvitadoId(Long id);

	/**
	 * Find all votaciones.
	 *
	 * @return the list
	 */
	public List<Votacion> findAllVotaciones();

	/**
	 * Find ids votaciones invitado evento.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<Long> findIdsVotacionesInvitadoEvento(Long id);

	/**
	 * Find all horarios.
	 *
	 * @return the list
	 */
	public List<Horario> findAllHorarios();

	/**
	 * Find horario by id.
	 *
	 * @param id the id
	 * @return the horario
	 */
	public Horario findHorarioById(Long id);

	/**
	 * Save horario.
	 *
	 * @param horario the horario
	 */
	public void saveHorario(Horario horario);

	/**
	 * Find horario by evento.
	 *
	 * @param evento the evento
	 * @return the horario
	 */
	public Horario findHorarioByEvento(Evento evento);

}
