package practica.tecnologias.web.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import practica.tecnologias.web.app.models.entity.Usuario;

/**
 * Interface IUsuarioDao.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
public interface IUsuarioDao extends JpaRepository<Usuario, Long> {

	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the usuario
	 */
	public Usuario findByUsername(String username);

}
