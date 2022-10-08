package practica.tecnologias.web.app.utils;

import java.util.List;

import practica.tecnologias.web.app.models.entity.Votacion;

/**
 * Clase de utilidad encargada de calcular la moda de los horarios (horario más votado). Se utiliza para conocer el horario definitivo
 * en el que se celebrará el evento, teniendo en cuenta las preferencias de los invitados a dicho evento.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
public class Moda {

	/** The votaciones. */
	List<Votacion> votaciones;

	/**
	 * Instantiates a new moda.
	 *
	 * @param votaciones the votaciones
	 */
	public Moda(List<Votacion> votaciones) {
		this.votaciones = votaciones;
	}

	/**
	 * Resultado.
	 *
	 * @return the int
	 */
	// funcion para entrar en los metodos de calcular el numero de ocurrencias;
	public int resultado() {
		Votacion[] array = new Votacion[votaciones.size()];
		array = votaciones.toArray(array);
		@SuppressWarnings("unused")
		int resultado;
		int[] arrayInt = new int[array.length];
		for (int i = 0; i<array.length; i++) {
			arrayInt[i] = Integer.parseInt(array[i].getHorarioSeleccionadoId().toString());
		}
		return resultado = mayorOcurrencia(arrayInt);
	}

	/**
	 * Mayor ocurrencia.
	 *
	 * @param a the a
	 * @return the int
	 */
	// metodo para calcular la moda
	private int mayorOcurrencia(int[] a) {

		int mayor = a[0];

		for (int i = 1; i<a.length; i++) {

			if (ocurrencias(mayor, a)<ocurrencias(a[i], a))

				mayor = a[i];
		}
		return mayor;
	}

	/**
	 * Ocurrencias.
	 *
	 * @param n the n
	 * @param arr the arr
	 * @return the int
	 */
	// metodo auxiliar para calcular moda
	private int ocurrencias(int n, int[] arr) {

		int cuantos = 0; // contador

		for (int i = 0; i<arr.length; i++) {

			if (n == arr[i]) // si n es igual al elemento i

				cuantos++; // el contador incrementa en 1.

		}

		return cuantos; // devuelve al contador.
	}

}
