package practica.tecnologias.web.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import practica.tecnologias.web.app.models.entity.EventoFinal;
import practica.tecnologias.web.app.models.entity.Usuario;

/**
 * Interface IEventoFinalDao.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
public interface IEventoFinalDao extends JpaRepository<EventoFinal, Long> {

	/**
	 * Find by organizador.
	 *
	 * @param organizador the organizador
	 * @return the list
	 */
	public List<EventoFinal> findByOrganizador(Usuario organizador);

	/**
	 * Consulta que devuelve la lista de eventos finales en los que ha participado como invitado un usuario determinado.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Query("select e from EventoFinal e join e.invitados i where i.id=?1")
	public List<EventoFinal> findEventosByInvitado(Long id);

}