package practica.tecnologias.web.app.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import practica.tecnologias.web.app.models.entity.Evento;
import practica.tecnologias.web.app.models.entity.Usuario;
import practica.tecnologias.web.app.models.entity.Votacion;

/**
 * Interface IEventoDao.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Repository
public interface IEventoDao extends JpaRepository<Evento, Long> {

	/**
	 * Find by organizador.
	 *
	 * @param organizador the organizador
	 * @return the list
	 */
	public List<Evento> findByOrganizador(Usuario organizador);

	/**
	 * Find by organizador.
	 *
	 * @param organizador the organizador
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Evento> findByOrganizador(Usuario organizador, Pageable pageable);

	/**
	 * Find eventos by usuario.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Query("select e from Evento e join e.invitados i where i.id=?1")
	public List<Evento> findEventosByUsuario(Long id);

	/**
	 * Mediante una query se busca la lista de eventos en los que ha participado un usuario.
	 *
	 * @param id the id
	 * @param pageable the pageable
	 * @return the page
	 */
	@Query(value = "select e from Evento e join e.invitados i where i.id=?1",
		countQuery = "select count(e) from Evento e join e.invitados i where i.id=?1")
	public Page<Evento> findEventosByUsuario(Long id, Pageable pageable);

	/**
	 * Consulta que devuelve la lista de votaciones de preferencias de horario de un determinado evento.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Query("select v from Votacion v where v.eventoId=?1")
	public List<Votacion> findVotacionesById(Long id);

}
