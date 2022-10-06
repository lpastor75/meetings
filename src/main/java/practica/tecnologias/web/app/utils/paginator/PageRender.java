package practica.tecnologias.web.app.utils.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Clase de utilidad para lograr una mejor presentación de una página que contiene muchos items y 
 * estos no pueden ser mostrados al mismo tiempo.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 * @param<T> Tipo genérico
 */
public class PageRender<T> {

	/** The url. */
	private String url;

	/** The page. */
	private Page<T> page;

	/** The total paginas. */
	private int totalPaginas;

	/** The num elementos por pagina. */
	private int numElementosPorPagina;

	/** The pagina actual. */
	private int paginaActual;

	/** The paginas. */
	private List<PageItem> paginas;

	/**
	 * Instantiates a new page render.
	 *
	 * @param url the url
	 * @param page the page
	 */
	public PageRender(String url, Page<T> page) {
		super();
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem> ();

		numElementosPorPagina = page.getSize();
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() + 1;

		int desde, hasta;
		if (totalPaginas<= numElementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
		} else {
			if (paginaActual<= numElementosPorPagina / 2) {
				desde = 1;
				hasta = numElementosPorPagina;
			} else if (paginaActual >= totalPaginas - numElementosPorPagina / 2) {
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			} else {

				desde = paginaActual - numElementosPorPagina / 2;
				hasta = numElementosPorPagina;
			}
		}

		for (int i = 0; i<hasta; i++) {

			paginas.add(new PageItem(desde + i, paginaActual == desde + i));
		}

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
	 * Gets the total paginas.
	 *
	 * @return the total paginas
	 */
	public int getTotalPaginas() {
		return totalPaginas;
	}

	/**
	 * Gets the pagina actual.
	 *
	 * @return the pagina actual
	 */
	public int getPaginaActual() {
		return paginaActual;
	}

	/**
	 * Gets the paginas.
	 *
	 * @return the paginas
	 */
	public List<PageItem> getPaginas() {
		return paginas;
	}

	/**
	 * Checks if is first.
	 *
	 * @return true, if is first
	 */
	public boolean isFirst() {

		return page.isFirst();
	}

	/**
	 * Checks if is last.
	 *
	 * @return true, if is last
	 */
	public boolean isLast() {

		return page.isLast();
	}

	/**
	 * Checks if is checks for next.
	 *
	 * @return true, if is checks for next
	 */
	public boolean isHasNext() {

		return page.hasNext();
	}

	/**
	 * Checks if is checks for previous.
	 *
	 * @return true, if is checks for previous
	 */
	public boolean isHasPrevious() {

		return page.hasPrevious();
	}

}