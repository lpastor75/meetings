package practica.tecnologias.web.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import practica.tecnologias.web.app.models.entity.Votacion;

/**
 * Interface IVotacionDao.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
public interface IVotacionDao extends JpaRepository<Votacion, Long> {

	/**
	 * Find votaciones by invitado id.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<Votacion> findVotacionesByInvitadoId(Long id);

	/**
	 * Mediante una query se obtienen los identificadores de eventos en los que un determinado usuario ha votado
	 * mostrando sus preferencias de posibles horarios.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Query("select v.eventoId from Votacion v where v.invitadoId=?1")
	public List<Long> findIdsVotacionesUsuarioEvento(Long id);

}
