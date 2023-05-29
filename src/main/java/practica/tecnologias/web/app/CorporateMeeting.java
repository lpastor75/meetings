package practica.tecnologias.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase CorporateMeeting, contiene el método main de la aplicación para lanzar el programa.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@SpringBootApplication
public class CorporateMeeting {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CorporateMeeting.class, args);
	}

}
