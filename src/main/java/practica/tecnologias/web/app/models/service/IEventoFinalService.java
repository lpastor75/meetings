package practica.tecnologias.web.app.models.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import practica.tecnologias.web.app.models.entity.Agenda;
import practica.tecnologias.web.app.models.entity.EventoFinal;
import practica.tecnologias.web.app.models.entity.Horario;
import practica.tecnologias.web.app.models.entity.Mensaje;
import practica.tecnologias.web.app.models.entity.Sala;
import practica.tecnologias.web.app.models.entity.Usuario;

/**
 * Interfaz de servicio IEventoFinalService.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Service
public interface IEventoFinalService {

	/**
	 * Find all evento finals.
	 *
	 * @return the list
	 */
	public List<EventoFinal> findAllEventoFinals();

	/**
	 * Find all evento finals by id.
	 *
	 * @param ids the ids
	 * @return the list
	 */
	public List<EventoFinal> findAllEventoFinalsById(Iterable<Long> ids);

	/**
	 * Find evento finals by usuario id.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<EventoFinal> findEventoFinalsByUsuarioId(Long id);

	/**
	 * Find evento finals by usuario id.
	 *
	 * @param idUsuario the id usuario
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<EventoFinal> findEventoFinalsByUsuarioId(Long idUsuario, Pageable pageable);

	/**
	 * Gets the one.
	 *
	 * @param id the id
	 * @return the one
	 */
	public EventoFinal getOne(Long id);

	/**
	 * Find evento final by id.
	 *
	 * @param id the id
	 * @return the evento final
	 */
	public EventoFinal findEventoFinalById(Long id);

	/**
	 * Find all evento finals.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<EventoFinal> findAllEventoFinals(Pageable pageable);

	/**
	 * Save evento final.
	 *
	 * @param eventoFinal the evento final
	 */
	public void saveEventoFinal(EventoFinal eventoFinal);

	/**
	 * Delete evento final by id.
	 *
	 * @param id the id
	 */
	public void deleteEventoFinalById(Long id);

	/**
	 * Delete evento final.
	 *
	 * @param entity the entity
	 */
	public void deleteEventoFinal(EventoFinal entity);

	/**
	 * Find evento finals by organizador.
	 *
	 * @param organizador the organizador
	 * @return the list
	 */
	public List<EventoFinal> findEventoFinalsByOrganizador(Usuario organizador);

	/**
	 * Find evento finals by organizador.
	 *
	 * @param organizador the organizador
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<EventoFinal> findEventoFinalsByOrganizador(Usuario organizador, Pageable pageable);

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
	 * Find horario by evento final.
	 *
	 * @param EventoFinal the evento final
	 * @return the horario
	 */
	public Horario findHorarioByEventoFinal(EventoFinal EventoFinal);

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
	 * Save sala.
	 *
	 * @param sala the sala
	 */
	public void saveSala(Sala sala);

	/**
	 * Delete sala.
	 *
	 * @param sala the sala
	 */
	public void deleteSala(Sala sala);

	/**
	 * Find sala by id.
	 *
	 * @param id the id
	 * @return the sala
	 */
	public Sala findSalaById(Long id);

	/**
	 * Find salas by reserva.
	 *
	 * @param horario the horario
	 * @return the list
	 */
	public List<Sala> findSalasByReserva(LocalDateTime horario);

	/**
	 * Save agenda.
	 *
	 * @param agenda the agenda
	 */
	public void saveAgenda(Agenda agenda);

	/**
	 * Find salas by list.
	 *
	 * @param salas the salas
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Sala> findSalasByList(List<Sala> salas, Pageable pageable);

	/**
	 * Find salas by horario aforo.
	 *
	 * @param aforo the aforo
	 * @param horarioInicio the horario inicio
	 * @param horarioFin the horario fin
	 * @return the list
	 */
	public List<Sala> findSalasByHorarioAforo(int aforo, Date horarioInicio, Date horarioFin);

	/**
	 * Save mensaje.
	 *
	 * @param mensaje the mensaje
	 */
	public void saveMensaje(Mensaje mensaje);

	/**
	 * Find all mensajes.
	 *
	 * @return the list
	 */
	public List<Mensaje> findAllMensajes();

	/**
	 * Find all mensajes by evento final.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<Mensaje> findAllMensajesByEventoFinal(Long id);

	/**
	 * Find eventos finales by invitado.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<EventoFinal> findEventosFinalesByInvitado(Long id);

	/**
	 * Delete mensaje.
	 *
	 * @param mensaje the mensaje
	 */
	public void deleteMensaje(Mensaje mensaje);

	/**
	 * Find mensaje by id.
	 *
	 * @param id the id
	 * @return the mensaje
	 */
	public Mensaje findMensajeById(Long id);

}
