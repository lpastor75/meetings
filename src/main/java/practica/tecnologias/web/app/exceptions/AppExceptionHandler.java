package practica.tecnologias.web.app.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Clase controladora para gestionar las posibles excepciones de la aplicación.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@ControllerAdvice
public class AppExceptionHandler {

	/**
	 * Handle exception.
	 *
	 * @param exception the exception
	 * @param redirectAttributes the redirect attributes
	 * @return the model and view
	 */
	@ExceptionHandler(FileStorageException.class)
	public ModelAndView handleException(FileStorageException exception, RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("message", exception.getMsg());
		mav.setViewName("error");
		return mav;
	}
}
