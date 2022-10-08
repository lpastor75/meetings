package practica.tecnologias.web.app.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import practica.tecnologias.web.app.models.entity.Contacto;
import practica.tecnologias.web.app.models.entity.Usuario;

/**
 * Interface IContactoDao.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
public interface IContactoDao extends JpaRepository<Contacto, Long> {

	/**
	 * Find by usuario.
	 *
	 * @param usuario the usuario
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Contacto> findByUsuario(Usuario usuario, Pageable pageable);

}
