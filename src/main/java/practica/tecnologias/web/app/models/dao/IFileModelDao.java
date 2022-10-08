package practica.tecnologias.web.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import practica.tecnologias.web.app.models.entity.FileModel;

/**
 * Interface IFileModelDao.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
public interface IFileModelDao extends JpaRepository<FileModel, Long> {

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the file model
	 */
	public FileModel findByName(String name);

	/**
	 * Mediante una query se busca la lista de archivos que han sido compartidos por los usuarios 
	 * durante la celebración de un evento determinado.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Query("select f from FileModel f where id_evento_final =?1")
	public List<FileModel> findFilesByEventoFinalId(Long id);

}