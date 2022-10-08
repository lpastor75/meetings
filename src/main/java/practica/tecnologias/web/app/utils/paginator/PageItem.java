package practica.tecnologias.web.app.utils.paginator;

/**
 * Clase de utilidad para crear un paginador de los distintos tipos de elementos.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
public class PageItem {

	/** The numero. */
	private int numero;

	/** The actual. */
	private boolean actual;

	/**
	 * Instantiates a new page item.
	 *
	 * @param numero the numero
	 * @param actual the actual
	 */
	public PageItem(int numero, boolean actual) {

		this.numero = numero;
		this.actual = actual;
	}

	/**
	 * Gets the numero.
	 *
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Checks if is actual.
	 *
	 * @return true, if is actual
	 */
	public boolean isActual() {
		return actual;
	}

}
