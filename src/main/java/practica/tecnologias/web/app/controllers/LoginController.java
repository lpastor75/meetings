package practica.tecnologias.web.app.controllers;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Clase encargada de controlar la pagina inicial de logueo de la aplicación.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Controller
public class LoginController {

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Login.
	 *
	 * @param error the error
	 * @param logout the logout
	 * @param model the model
	 * @param principal the principal
	 * @param flash the flash
	 * @param locale the locale
	 * @return the string
	 */
	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout, Model model,
		Principal principal, RedirectAttributes flash, Locale locale) {

		if (principal != null) {

			flash.addFlashAttribute("info", messageSource.getMessage("text.login.already", null, locale));
			return "redirect:/";
		}

		if (error != null) {
			model.addAttribute("titulo", messageSource.getMessage("text.login.boton", null, locale));
			model.addAttribute("error", messageSource.getMessage("text.login.error", null, locale));
		}

		if (logout != null) {
			
			flash.addFlashAttribute("success", messageSource.getMessage("text.login.logout", null, locale));
			return "redirect:/inicio";
		}

		model.addAttribute("titulo", messageSource.getMessage("text.login.boton", null, locale));
		return "login";
	}

}
