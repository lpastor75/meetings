package practica.tecnologias.web.app.controllers;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import practica.tecnologias.web.app.models.entity.Agenda;
import practica.tecnologias.web.app.models.entity.Evento;
import practica.tecnologias.web.app.models.entity.EventoFinal;
import practica.tecnologias.web.app.models.entity.Sala;
import practica.tecnologias.web.app.models.entity.Usuario;
import practica.tecnologias.web.app.models.service.IEventoFinalService;
import practica.tecnologias.web.app.models.service.IEventoService;
import practica.tecnologias.web.app.models.service.IUsuarioService;
import practica.tecnologias.web.app.utils.paginator.PageRender;

/**
 * Clase controladora de todo lo relacionado con las salas, que es el lugar donde tienen lugar las
 * reuniones.
 *  
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Controller
@SessionAttributes("eventoFinal")
@RequestMapping("/salas")
public class SalaController {

	/** The logger. */
	protected final Log logger = LogFactory.getLog(this.getClass());

	/** The usuario service. */
	@Autowired
	private IUsuarioService usuarioService;

	/** The evento final service. */
	@Autowired
	private IEventoFinalService eventoFinalService;

	/** The evento service. */
	@Autowired
	private IEventoService eventoService;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Crear sala.
	 *
	 * @param model 			the model
	 * @param locale 			the locale
	 * @param authentication 	the authentication
	 * @return the string
	 */
	@RequestMapping(value = "/registroSala")
	public String crearSala(Map<String, Object> model, Locale locale, Authentication authentication) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
		Sala sala = new Sala();

		model.put("sala", sala);
		model.put("usuario", currentUser);
		model.put("titulo", messageSource.getMessage("text.salas.registrar", null, locale));

		return "salas/registroSala";
	}

	/**
	 * Editar sala.
	 *
	 * @param id    				the id
	 * @param model 				the model
	 * @param flash 				the flash
	 * @param authentication 		the authentication
	 * @param locale 				the locale
	 * @return the string
	 */
	@RequestMapping(value = "/registroSala/{id}")
	public String editarSala(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash,
		Authentication authentication, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
		Sala sala = null;

		if (id > 0) {
			sala = usuarioService.findSalaById(id);
			if (sala == null) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noSalaId", null, locale));
				return "redirect:/salas/listadoSalas";
			}
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.salaId", null, locale));
			return "redirect:/salas/listadoSalas";
		}
		model.put("sala", sala);
		model.put("usuario", currentUser);
		model.put("titulo", messageSource.getMessage("text.salas.editarSala", null, locale));
		return "salas/registroSala";
	}

	/**
	 * Guardar sala.
	 *
	 * @param sala   the sala
	 * @param result the result
	 * @param model  the model
	 * @param flash  the flash
	 * @param status the status
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/registroSala", method = RequestMethod.POST)
	public String guardarSala(@Valid Sala sala, BindingResult result, Model model, RedirectAttributes flash,
		SessionStatus status, Locale locale) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.salas.registrar", null, locale));
			return "salas/registroSala";
		}

		//model.addAttribute("id", sala.getId());
		String mensajeFlash = (sala.getId() != null) ? messageSource.getMessage("text.alert.salaEditada", null, locale) :
			messageSource.getMessage("text.alert.salaCreada", null, locale);

		try {
			usuarioService.saveSala(sala);

		} catch (DataIntegrityViolationException e) {

			mensajeFlash = (sala.getId() != null) ?
				messageSource.getMessage("text.alert.salaExiste", null, locale) + sala.getNombre() +
				messageSource.getMessage("text.alert.salaOtroEditar", null, locale) :
				messageSource.getMessage("text.alert.sala", null, locale) + sala.getNombre() +
				messageSource.getMessage("text.alert.salaDuplicada", null, locale);
			flash.addFlashAttribute("error", mensajeFlash);

			if (sala.getId() != null) {
				return "redirect:/salas/registroSala/" + sala.getId();
			} else {
				return "redirect:/salas/registroSala";
			}
		}

		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/";
	}

	/**
	 * Ver sala.
	 *
	 * @param id             the id
	 * @param model          the model
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @param locale 		 the locale
	 * @return the string
	 */
	@RequestMapping(value = "/verSala/{id}", method = RequestMethod.GET)
	public String verSala(@PathVariable(value = "id") Long id, Model model, Authentication authentication,
		RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		Sala sala = usuarioService.findSalaById(id);

		if (sala == null) {

			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noSala", null, locale));
			return "redirect:/salas/listadoSalas";
		}

		model.addAttribute("usuario", currentUser);
		model.addAttribute("sala", sala);
		model.addAttribute("titulo", messageSource.getMessage("text.salas.caracteristicas", null, locale));

		return "salas/verSala";
	}

	/**
	 * Listar salas.
	 *
	 * @param page           the page
	 * @param model          the model
	 * @param authentication the authentication
	 * @param locale 		 the locale
	 * @return the string
	 */
	@RequestMapping(value = {
		"/listadoSalas"
	}, method = RequestMethod.GET)
	public String listarSalas(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
		Authentication authentication, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Sala> salasPage = eventoFinalService.findAllSalas(pageRequest);

		PageRender<Sala> pageRender = new PageRender<Sala> ("/salas/listadoSalas", salasPage);

		model.addAttribute("titulo", messageSource.getMessage("text.salas.listadoSalas.titulo", null, locale));
		model.addAttribute("salas", salasPage);
		model.addAttribute("page", pageRender);
		model.addAttribute("usuario", currentUser);

		return "/salas/listadoSalas";
	}

	/**
	 * Listado salas disponibles.
	 *
	 * @param id             the id
	 * @param model          the model
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = {
		"/listadoSalasDisponibles/{id}"
	}, method = RequestMethod.GET)
	public String listadoSalasDisponibles(@PathVariable(value = "id") Long id, Model model,
		Authentication authentication, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
		EventoFinal eventoFinal = eventoFinalService.findEventoFinalById(id);

		String mensajeFlash = messageSource.getMessage("text.alert.noOrganizador", null, locale);
		String mensajeFlashNoSalas = messageSource.getMessage("text.alert.noSalas", null, locale);

		if (currentUser.getId() == eventoFinal.getOrganizador().getId() && eventoFinal != null) {

			Date fecha = Date.from(eventoFinal.getHorarioDefinitivo().atZone(ZoneId.systemDefault()).toInstant());
			Date fecha2 = Date.from(eventoFinal.getHorarioDefinitivoFin().atZone(ZoneId.systemDefault()).toInstant());

			List<Sala> salasDuracion = eventoFinalService.findSalasByHorarioAforo(eventoFinal.getInvitados().size(),
				fecha, fecha2);
			List<Sala> salas = eventoFinalService.findSalasByReserva(eventoFinal.getHorarioDefinitivo());
			List<Sala> salas2 = eventoFinalService
				.findSalasByReserva(eventoFinal.getHorarioDefinitivo().plusHours(eventoFinal.getDuracion()));

			List<Sala> salasNuevas = eventoFinalService.findAllSalas();
			salasNuevas.removeAll(salas);
			salasNuevas.removeAll(salas2);
			salasNuevas.removeAll(salasDuracion);

			eventoFinal.setSalasDisponibles(salasNuevas);
			eventoFinalService.saveEventoFinal(eventoFinal);

			if (salasNuevas.size() != 0) {
				model.addAttribute("titulo", messageSource.getMessage("text.salas.lista.disponibles", null, locale));
				model.addAttribute("salasDisponibles", eventoFinal.getSalasDisponibles());
				model.addAttribute("eventoFinal", eventoFinal);
				model.addAttribute("usuario", currentUser);

				return "/salas/listadoSalasDisponibles";
			} else {
				Evento evento = eventoService.findEventoById(eventoFinal.getEventoPadre());
				eventoFinalService.deleteEventoFinal(eventoFinal);
				eventoService.deleteEvento(evento);

				flash.addFlashAttribute("warning", mensajeFlashNoSalas);
				return "redirect:/";
			}

		} else {
			flash.addFlashAttribute("error", mensajeFlash);
			return "redirect:/";
		}
	}

	/**
	 * Adds the sala.
	 *
	 * @param eventoFinal    the evento final
	 * @param model          the model
	 * @param result         the result
	 * @param authentication the authentication
	 * @param request        the request
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = "/listadoSalasDisponibles-add", method = RequestMethod.POST)
	public String addSala(@Valid EventoFinal eventoFinal, Model model, BindingResult result,
		Authentication authentication, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
		String seleccionaSala = messageSource.getMessage("text.alert.selectSala", null, locale);
		if (result.hasErrors()) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.error.eventoFinal", null, locale));
			model.addAttribute("titulo", messageSource.getMessage("text.salas.lista.disponibles", null, locale));
			model.addAttribute("usuario", currentUser);
			model.addAttribute("eventoFinal", eventoFinal);
			return "redirect:/";
		}

		List<Sala> salasDisponibles = eventoFinal.getSalasDisponibles();
		List<Sala> salas = salasDisponibles.stream().filter(sala -> sala != null).collect(Collectors.toList());
		if (salas.size() != 0) {
			Sala sala = salas.get(0);

			eventoFinalService.saveSala(sala);

			Agenda agenda = new Agenda(eventoFinal.getHorarioDefinitivo(), eventoFinal.getHorarioDefinitivoFin(),
				eventoFinal.getDuracion(), sala);

			eventoFinal.setSala(sala);
			eventoFinalService.saveAgenda(agenda);
			eventoFinalService.saveEventoFinal(eventoFinal);
			sala.getAgendas().add(agenda);
			eventoFinalService.saveSala(sala);

			flash.addFlashAttribute("success", messageSource.getMessage("text.alert.salaReservada", null, locale));

			return "redirect:/eventos/verEventoContactos/" + eventoFinal.getId();
		} else {
			flash.addFlashAttribute("warning", seleccionaSala);
			return "redirect:/salas/listadoSalasDisponibles/" + eventoFinal.getId();
		}

	}

}
