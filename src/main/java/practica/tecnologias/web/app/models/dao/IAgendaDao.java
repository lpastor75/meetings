package practica.tecnologias.web.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import practica.tecnologias.web.app.models.entity.Agenda;

/**
 * Interface IAgendaDao.
 * 
 * @author Alumno 1, ALumno 2, ALumno 3
 * @version Junio 2020
 */
public interface IAgendaDao extends JpaRepository<Agenda,Long>{

}
