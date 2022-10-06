package practica.tecnologias.web.app.models.entity;

/**
 * Clase FileInfo donde se guarda la informacion de los archivos que pueden ser aportados a una reunión.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
public class FileInfo {

	/** The filename. */
	private String filename;

	/** The url. */
	private String url;

	/** The id file. */
	private Long idFile;

	/**
	 * Instantiates a new file info.
	 *
	 * @param filename the filename
	 * @param url the url
	 * @param idFile the id file
	 */
	public FileInfo(String filename, String url, Long idFile) {
		this.filename = filename;
		this.url = url;
		this.idFile = idFile;
	}

	/**
	 * Instantiates a new file info.
	 */
	public FileInfo() {

	}

	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets the filename.
	 *
	 * @param filename the new filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the id file.
	 *
	 * @return the id file
	 */
	public Long getIdFile() {
		return idFile;
	}

	/**
	 * Sets the id file.
	 *
	 * @param idFile the new id file
	 */
	public void setIdFile(Long idFile) {
		this.idFile = idFile;
	}
	
}
