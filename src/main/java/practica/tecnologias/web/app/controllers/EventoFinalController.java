package practica.tecnologias.web.app.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import practica.tecnologias.web.app.models.entity.Contacto;
import practica.tecnologias.web.app.models.entity.EventoFinal;
import practica.tecnologias.web.app.models.entity.Usuario;
import practica.tecnologias.web.app.models.service.IEventoFinalService;
import practica.tecnologias.web.app.models.service.IUsuarioService;

/**
 * Clase controladora del EventoFinal, engargada de gestionar aquellos eventos que ya están totalmente configurados 
 * para poder ser celebrados.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@SessionAttributes("eventoFinal")
@RequestMapping("/eventos")
@Controller
public class EventoFinalController {

	/** The evento final service. */
	@Autowired
	private IEventoFinalService eventoFinalService;

	/** The usuario service. */
	@Autowired
	private IUsuarioService usuarioService;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Listar reuniones.
	 *
	 * @param page           the page
	 * @param model          the model
	 * @param request        the request
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = {"/listadoReuniones"}, method = RequestMethod.GET)
	public String listarReuniones(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
		HttpServletRequest request, Authentication authentication, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		List<EventoFinal> misEventos = eventoFinalService.findEventosFinalesByInvitado(currentUser.getId());

		List<EventoFinal> misEventosFinales = new ArrayList<EventoFinal> ();

		for (EventoFinal eventoFinal: misEventos) {
			if (eventoFinal.getSala() != null && !eventoFinal.isEventoFinalizado()) {
				if (eventoFinal.getHorarioDefinitivoFin().isAfter(LocalDateTime.now())) {
					misEventosFinales.add(eventoFinal);
				} else {
					eventoFinal.setEventoFinalizado(true);
					eventoFinalService.saveEventoFinal(eventoFinal);
				}
			}
		}

		model.addAttribute("titulo", messageSource.getMessage("text.listadoEventos.asistencia", null, locale));
		model.addAttribute("usuario", currentUser);
		model.addAttribute("eventos", misEventosFinales);

		return "eventos/listadoReuniones";
	}

	/**
	 * Listar historico.
	 *
	 * @param page           the page
	 * @param model          the model
	 * @param request        the request
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = {"/listadoHistorico"}, method = RequestMethod.GET)
	public String listarHistorico(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
		HttpServletRequest request, Authentication authentication, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		List<EventoFinal> misEventos = eventoFinalService.findEventosFinalesByInvitado(currentUser.getId());

		List<EventoFinal> misEventosFinales = new ArrayList<EventoFinal> ();

		for (EventoFinal eventoFinal: misEventos) {

			if (eventoFinal.isEventoFinalizado() ||	eventoFinal.getHorarioDefinitivoFin().isBefore(LocalDateTime.now())) {

				misEventosFinales.add(eventoFinal);
			}

		}

		model.addAttribute("titulo", messageSource.getMessage("text.listadoEventos.historico", null, locale));
		model.addAttribute("usuario", currentUser);
		model.addAttribute("eventos", misEventosFinales);

		return "eventos/listadoHistorico";
	}

	/**
	 * Ver evento contactos.
	 *
	 * @param id             the id
	 * @param page           the page
	 * @param model          the model
	 * @param authentication the authentication
	 * @param request        the request
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = "/verEventoContactos/{id}", method = RequestMethod.GET)
	public String verEventoContactos(@PathVariable(value = "id") Long id,
		@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication,
		HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		EventoFinal eventoFinal = eventoFinalService.findEventoFinalById(id);
		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		if (eventoFinal == null) {
			if (request.isUserInRole("ROLE_ADMIN")) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.eventos.flash.null.error", null, locale));
				return "redirect:/";

			} else {

				return "redirect:/error_403";
			}

		} else if (eventoFinal.getOrganizador().getId() == currentUser.getId()) {

			Set<Contacto> asistentes = new HashSet<Contacto> ();

			Contacto contacto = new Contacto();

			for (Usuario usuario: eventoFinal.getInvitados()) {
				contacto.setAlias(usuario.getNombre());
				contacto.setEmail(usuario.getEmail());
				asistentes.add(contacto);
			}
			if (eventoFinal.getVacantes() != 0) {
				eventoFinal.setAsistentes(asistentes);

				int salaSize = eventoFinal.getSala().getAforo();
				int numInvitados = eventoFinal.getInvitados().size();
				int vacantesSala = salaSize - numInvitados;
				eventoFinal.setVacantes(vacantesSala);
				eventoFinalService.saveEventoFinal(eventoFinal);
				model.addAttribute("eventoFinal", eventoFinal);
				model.addAttribute("usuario", currentUser);
				model.addAttribute("listaParticipantes", eventoFinal.getInvitados());
				model.addAttribute("horarioDefinitivo", eventoFinal.getHorarioDefinitivo());
				model.addAttribute("titulo", messageSource.getMessage("text.eventos.eventoFinal.detalle", null, locale));
				model.addAttribute("vacantesSala", vacantesSala);
				model.addAttribute("contactos", eventoFinal.getOrganizador().getContactos());

				return "eventos/verEventoContactos";
			} else {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.vacantes", null, locale));
				return "redirect:/eventos/inicioEventos";
			}
		}

		flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noOrganizador", null, locale));

		return "redirect:/";
	}

	/**
	 * Adds the contactos.
	 *
	 * @param eventoFinal    the evento final
	 * @param result         the result
	 * @param vacantesSala   the vacantes sala
	 * @param model          the model
	 * @param authentication the authentication
	 * @param request        the request
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = "/verEventoContactos-add", method = RequestMethod.POST)
	public String addContactos(@Valid EventoFinal eventoFinal, BindingResult result,
		@RequestParam("vacantes") int vacantesSala, Model model, Authentication authentication,
		HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		if (result.hasErrors()) {

			flash.addFlashAttribute("error",
				messageSource.getMessage("text.eventos.flash.validacion.error", null, locale));
			model.addAttribute("usuario", currentUser);
			model.addAttribute("eventoFinal", eventoFinal);
			return "redirect:/";
		}

		Set<Contacto> contactos = eventoFinal.getOrganizador().getContactos();

		List<Contacto> contactos3 = contactos.stream().filter(contacto -> contacto != null)
			.collect(Collectors.toList());
		if (contactos3.size()<= vacantesSala) {

			for (Contacto contacto: contactos3) {

				eventoFinal.getAsistentes().add(contacto);
			}
			eventoFinal.setVacantes(vacantesSala);
			eventoFinalService.saveEventoFinal(eventoFinal);

			flash.addFlashAttribute("success", messageSource.getMessage("text.alert.evento.configurado", null, locale));

			return "redirect:/eventos/inicioEventos";
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.overInvitados", null, locale));
			return "redirect:/eventos/verEventoContactos/" + eventoFinal.getId();
		}
	}

}
