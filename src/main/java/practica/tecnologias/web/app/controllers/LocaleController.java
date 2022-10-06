package practica.tecnologias.web.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Clase controladora LocaleController, encargada de la internacionalización y de la localización de los textos (I18N). Cuando el usuario
 * decide cambiar el idioma de los textos, se debe redireccionar a la URL desde donde se solicitó ese cambio.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Controller
public class LocaleController {

	/**
	 * Locale.
	 *
	 * @param request the request
	 * @return the string
	 */
	@GetMapping("/locale")
	public String locale(HttpServletRequest request) {
		String ultimaUrl = request.getHeader("referer");

		return "redirect:".concat(ultimaUrl);
	}
	
}
