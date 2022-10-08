package practica.tecnologias.web.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import practica.tecnologias.web.app.models.entity.EventoFinal;

import practica.tecnologias.web.app.models.service.IEventoFinalService;
import practica.tecnologias.web.app.models.service.IUploadFileService;

/**
 * Clase Controladora encargada de las peticiones Ajax que hay en el Frontend.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@RequestMapping("/eventos/reunion")
@Controller
public class AjaxController {

	/** The upload file service. */
	@SuppressWarnings("unused")
	@Autowired
	private IUploadFileService uploadFileService;

	/** The evento final service. */
	@Autowired
	private IEventoFinalService eventoFinalService;

	/**
	 * Comprobar fin evento.
	 *
	 * @param id the id
	 * @return the string
	 */
	@PostMapping("comprobarBandera")
	public @ResponseBody String comprobarFinEvento(@RequestParam("eventoFinal_id") long id) {
		
		EventoFinal eventoFinal = eventoFinalService.findEventoFinalById(id);
		String valor = String.valueOf(eventoFinal.isEventoFinalizado());
		return valor;
	}
	
}
