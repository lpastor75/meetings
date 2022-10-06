package practica.tecnologias.web.app.models.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import practica.tecnologias.web.app.models.entity.Sala;

/**
 * Interface ISalaDao.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
public interface ISalaDao extends JpaRepository<Sala, Long> {

	/**
	 * Método para extraer la lista de salas disponibles para celebrar el evento. Mediante una consulta query 
	 * se buscan aquellas salas que no se encuentran reservadas en la fecha y en la hora en las que se pretende celebrar la reunión. 
	 *
	 * @param horarioEvento the horario evento
	 * @return the list
	 */
	@Query(value = "select s from Sala s inner join Agenda ag on s.id=ag.sala where s.id in  " +
		"(select a.sala from Agenda a inner join Sala sl on a.sala = sl.id where ?1 between a.horarioInicio and a.horarioFin)")
	List<Sala> findSalaByReserva(LocalDateTime horarioEvento);

	/**
	 * Método que devuelve una lista de salas que pueden albergar el evento de acuerdo a su capacidad y a su horario de 
	 * funcionamiento. Mediante una consulta query se obtienen las salas con una capacidad mayor al número de participantes 
	 * de un determinado evento y que se encuentran abiertas durante el horario en el que se celebrará el evento.
	 *
	 * @param asistentes the asistentes
	 * @param horarioInicio the horario inicio
	 * @param horarioFin the horario fin
	 * @return the list
	 */
	@Query("select s from Sala s where s.aforo<?1 or ?2<s.apertura or ?2>s.cierre or ?3>s.cierre or ?3<s.apertura")
	List<Sala> findSalaByCapacidad(int asistentes, Date horarioInicio, Date horarioFin);

}
