package practica.tecnologias.web.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import practica.tecnologias.web.app.models.entity.Agenda;

/**
 * Interface IAgendaDao.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
public interface IAgendaDao extends JpaRepository<Agenda,Long>{

}
