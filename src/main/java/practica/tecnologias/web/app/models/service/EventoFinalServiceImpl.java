package practica.tecnologias.web.app.models.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import practica.tecnologias.web.app.models.dao.IAgendaDao;
import practica.tecnologias.web.app.models.dao.IEventoFinalDao;
import practica.tecnologias.web.app.models.dao.IHorarioDao;
import practica.tecnologias.web.app.models.dao.IMensajeDao;
import practica.tecnologias.web.app.models.dao.ISalaDao;
import practica.tecnologias.web.app.models.entity.Agenda;
import practica.tecnologias.web.app.models.entity.EventoFinal;
import practica.tecnologias.web.app.models.entity.Horario;
import practica.tecnologias.web.app.models.entity.Mensaje;
import practica.tecnologias.web.app.models.entity.Sala;
import practica.tecnologias.web.app.models.entity.Usuario;

/**
 * Clase de Servicio EventoFinal, que implementa la interface IEventoFinalService.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Service
public class EventoFinalServiceImpl implements IEventoFinalService {

	/** The evento final dao. */
	@Autowired
	IEventoFinalDao eventoFinalDao;

	/** The horario dao. */
	@Autowired
	IHorarioDao horarioDao;

	/** The sala dao. */
	@Autowired
	ISalaDao salaDao;

	/** The agenda dao. */
	@Autowired
	IAgendaDao agendaDao;

	/** The mensaje dao. */
	@Autowired
	IMensajeDao mensajeDao;

	/**
	 * Find all evento finals.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<EventoFinal> findAllEventoFinals() {

		return eventoFinalDao.findAll();
	}

	/**
	 * Find all evento finals by id.
	 *
	 * @param ids the ids
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<EventoFinal> findAllEventoFinalsById(Iterable<Long> ids) {

		return eventoFinalDao.findAllById(ids);
	}

	/**
	 * Find evento finals by usuario id.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Override
	public List<EventoFinal> findEventoFinalsByUsuarioId(Long id) {
		
		return null;
	}

	/**
	 * Find evento finals by usuario id.
	 *
	 * @param idUsuario the id usuario
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	public Page<EventoFinal> findEventoFinalsByUsuarioId(Long idUsuario, Pageable pageable) {
		
		return null;
	}

	/**
	 * Gets the one.
	 *
	 * @param id the id
	 * @return the one
	 */
	@Override
	@Transactional(readOnly = true)
	public EventoFinal getOne(Long id) {

		return eventoFinalDao.getOne(id);
	}

	/**
	 * Find evento final by id.
	 *
	 * @param id the id
	 * @return the evento final
	 */
	@Override
	@Transactional(readOnly = true)
	public EventoFinal findEventoFinalById(Long id) {

		return eventoFinalDao.findById(id).orElse(null);
	}

	/**
	 * Find all evento finals.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<EventoFinal> findAllEventoFinals(Pageable pageable) {

		return eventoFinalDao.findAll(pageable);
	}

	/**
	 * Save evento final.
	 *
	 * @param eventoFinal the evento final
	 */
	@Override
	@Transactional
	public void saveEventoFinal(EventoFinal eventoFinal) {

		eventoFinalDao.save(eventoFinal);
	}

	/**
	 * Delete evento final by id.
	 *
	 * @param id the id
	 */
	@Override
	@Transactional
	public void deleteEventoFinalById(Long id) {

		eventoFinalDao.deleteById(id);
	}

	/**
	 * Delete evento final.
	 *
	 * @param eventoFinal the evento final
	 */
	@Override
	@Transactional
	public void deleteEventoFinal(EventoFinal eventoFinal) {

		eventoFinalDao.delete(eventoFinal);
	}

	/**
	 * Find evento finals by organizador.
	 *
	 * @param organizador the organizador
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<EventoFinal> findEventoFinalsByOrganizador(Usuario organizador) {

		return eventoFinalDao.findByOrganizador(organizador);
	}

	/**
	 * Find evento finals by organizador.
	 *
	 * @param organizador the organizador
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<EventoFinal> findEventoFinalsByOrganizador(Usuario organizador, Pageable pageable) {

		return null;
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
	 * Find horario by evento final.
	 *
	 * @param eventoFinal the evento final
	 * @return the horario
	 */
	@Override
	@Transactional(readOnly = true)
	public Horario findHorarioByEventoFinal(EventoFinal eventoFinal) {

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
	@Transactional
	public Sala findSalaById(Long id) {

		return salaDao.findById(id).orElse(null);
	}

	/**
	 * Delete sala.
	 *
	 * @param sala the sala
	 */
	@Override
	@Transactional
	public void deleteSala(Sala sala) {

		salaDao.delete(sala);
	}

	/**
	 * Find salas by reserva.
	 *
	 * @param horario the horario
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Sala> findSalasByReserva(LocalDateTime horario) {

		return salaDao.findSalaByReserva(horario);
	}

	/**
	 * Save agenda.
	 *
	 * @param agenda the agenda
	 */
	@Override
	@Transactional
	public void saveAgenda(Agenda agenda) {

		agendaDao.save(agenda);
	}

	/**
	 * Find salas by list.
	 *
	 * @param salas the salas
	 * @param pageable the pageable
	 * @return the page
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Sala> findSalasByList(List<Sala> salas, Pageable pageable) {

		return salaDao.findAll((Example<Sala> ) salas, pageable);
	}

	/**
	 * Find salas by horario aforo.
	 *
	 * @param aforo the aforo
	 * @param horarioInicio the horario inicio
	 * @param horarioFin the horario fin
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Sala> findSalasByHorarioAforo(int aforo, Date horarioInicio, Date horarioFin) {

		return salaDao.findSalaByCapacidad(aforo, horarioInicio, horarioFin);
	}

	/**
	 * Save mensaje.
	 *
	 * @param mensaje the mensaje
	 */
	@Override
	@Transactional
	public void saveMensaje(Mensaje mensaje) {

		mensajeDao.save(mensaje);
	}

	/**
	 * Find all mensajes.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Mensaje> findAllMensajes() {

		return mensajeDao.findAll();
	}

	/**
	 * Find all mensajes by evento final.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Mensaje> findAllMensajesByEventoFinal(Long id) {

		return mensajeDao.findAllMensajesByEventoFinal(id);
	}

	/**
	 * Find eventos finales by invitado.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<EventoFinal> findEventosFinalesByInvitado(Long id) {

		return eventoFinalDao.findEventosByInvitado(id);
	}

	/**
	 * Delete mensaje.
	 *
	 * @param mensaje the mensaje
	 */
	@Override
	@Transactional
	public void deleteMensaje(Mensaje mensaje) {

		mensajeDao.delete(mensaje);
	}

	/**
	 * Find mensaje by id.
	 *
	 * @param id the id
	 * @return the mensaje
	 */
	@Override
	@Transactional(readOnly = true)
	public Mensaje findMensajeById(Long id) {

		return mensajeDao.findById(id).orElse(null);
	}

}
