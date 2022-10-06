package practica.tecnologias.web.app.models.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import practica.tecnologias.web.app.models.entity.EventoFinal;
import practica.tecnologias.web.app.models.entity.FileModel;

/**
 * Interfaz de servicio IUploadFileService para subir archivos.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Service
public interface IUploadFileService {

	/** The Constant uploadDir. */
	public static final String uploadDir = "";

	/**
	 * Find file model by name.
	 *
	 * @param name the name
	 * @return the file model
	 */
	public FileModel findFileModelByName(String name);

	/**
	 * Find file model by id.
	 *
	 * @param id the id
	 * @return the file model
	 */
	public FileModel findFileModelById(Long id);

	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @param eventoFinal the evento final
	 */
	public void uploadFile(MultipartFile file, EventoFinal eventoFinal);

	/**
	 * Find files by evento final id.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<FileModel> findFilesByEventoFinalId(Long id);

	/**
	 * Delete file model.
	 *
	 * @param fileModel the file model
	 */
	public void deleteFileModel(FileModel fileModel);

}
