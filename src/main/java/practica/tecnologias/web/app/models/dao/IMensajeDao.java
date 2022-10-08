package practica.tecnologias.web.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import practica.tecnologias.web.app.models.entity.Mensaje;

/**
 * Interface IMensajeDao.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
public interface IMensajeDao extends JpaRepository<Mensaje, Long> {

	/**
	 * Mediante una query se localizan todos los mensajes enviados al chat durante la celebración de un evento.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Query(value = "select m from Mensaje m where evento_final_id =?1")
	public List<Mensaje> findAllMensajesByEventoFinal(Long id);
	
}