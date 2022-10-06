package practica.tecnologias.web.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import practica.tecnologias.web.app.models.entity.Horario;

/**
 * Interface IHorarioDao.
 * 
 * @author Alumno 1, Alumno 2, ALumno 3
 * @version Junio 2020
 */
public interface IHorarioDao extends JpaRepository<Horario,Long>{
	
}
