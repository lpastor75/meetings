package practica.tecnologias.web.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Clase ServletInitializer, encargada de ejectutar el archivo .war
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configure.
	 *
	 * @param application the application
	 * @return the spring application builder
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CorporateMeeting.class);
	}

}
