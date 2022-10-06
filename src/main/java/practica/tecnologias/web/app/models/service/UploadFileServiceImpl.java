package practica.tecnologias.web.app.models.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import practica.tecnologias.web.app.exceptions.FileStorageException;
import practica.tecnologias.web.app.models.dao.IFileModelDao;
import practica.tecnologias.web.app.models.entity.EventoFinal;
import practica.tecnologias.web.app.models.entity.FileModel;

/**
 * Clase de servicio para el manejo de las funcionalidades de subir archivos que implementa la interface IUploadFileService.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Service
public class UploadFileServiceImpl implements IUploadFileService {

	/** The file model dao. */
	@Autowired
	IFileModelDao fileModelDao;

	/** The upload dir. */
	@Value("${app.upload.dir:${user.home}}")
	public String uploadDir;

	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @param eventoFinal the evento final
	 */
	public void uploadFile(MultipartFile file, EventoFinal eventoFinal) {

		String fileName = file.getOriginalFilename();

		try {
			Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
			Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

			FileModel fileModel = new FileModel(file.getOriginalFilename(), file.getContentType(), file.getBytes(), eventoFinal.getId());

			fileModelDao.save(fileModel);

		} catch (Exception e) {
			e.printStackTrace();
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
		}
	}

	/**
	 * Find files by evento final id.
	 *
	 * @param id the id
	 * @return the list
	 */
	@Override
	public List<FileModel> findFilesByEventoFinalId(Long id) {

		return fileModelDao.findFilesByEventoFinalId(id);
	}

	/**
	 * Find file model by name.
	 *
	 * @param name the name
	 * @return the file model
	 */
	@Override
	public FileModel findFileModelByName(String name) {

		return fileModelDao.findByName(name);
	}

	/**
	 * Delete file model.
	 *
	 * @param fileModel the file model
	 */
	@Override
	@Transactional
	public void deleteFileModel(FileModel fileModel) {

		fileModelDao.delete(fileModel);
	}

	/**
	 * Find file model by id.
	 *
	 * @param id the id
	 * @return the file model
	 */
	@Override
	public FileModel findFileModelById(Long id) {

		return fileModelDao.findById(id).orElse(null);
	}

}
