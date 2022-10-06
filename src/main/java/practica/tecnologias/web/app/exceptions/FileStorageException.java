package practica.tecnologias.web.app.exceptions;

/**
 * Clase encargada de las posibles excepciones en tiempo de ejecución relacionadas con la subida de archivos durante la celebración de los eventos.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
public class FileStorageException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The msg. */
	private String msg;

	/**
	 * Instantiates a new file storage exception.
	 *
	 * @param msg the msg
	 */
	public FileStorageException(String msg) {
		this.msg = msg;
	}

	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

}
