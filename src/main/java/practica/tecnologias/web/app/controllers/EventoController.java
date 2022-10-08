package practica.tecnologias.web.app.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import practica.tecnologias.web.app.utils.Moda;
import practica.tecnologias.web.app.models.entity.Evento;
import practica.tecnologias.web.app.models.entity.EventoFinal;
import practica.tecnologias.web.app.models.entity.Horario;
import practica.tecnologias.web.app.models.entity.Usuario;
import practica.tecnologias.web.app.models.entity.Votacion;
import practica.tecnologias.web.app.models.service.EmailService;
import practica.tecnologias.web.app.models.service.IEventoFinalService;
import practica.tecnologias.web.app.models.service.IEventoService;
import practica.tecnologias.web.app.models.service.IUsuarioService;
import practica.tecnologias.web.app.utils.paginator.PageRender;

/**
 * Clase que se encarga de controlar todo lo relacionado con los eventos en la fase inicial de creación.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@SessionAttributes("evento") 
@RequestMapping("/eventos") 
@Controller
public class EventoController {

	/** The email service. */
	@Autowired
	private EmailService emailService;

	/** The evento service. */
	@Autowired
	private IEventoService eventoService;

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
	 * Iniciar.
	 *
	 * @param model          the model
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @return the string
	 */
	@GetMapping("/inicioEventos")
	public String iniciar(Model model, Authentication authentication, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = null;

		if (authentication != null) {
			currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
		}

		List<Evento> misEventosPendientes = eventoService.findEventosByUsuarioId(currentUser.getId());

		List<Evento> misEventosMostrar = new ArrayList<Evento> ();

		Long idU = (long) currentUser.getId();

		List<Long> listaIds = eventoService.findIdsVotacionesInvitadoEvento(idU);

		for (Evento evento: misEventosPendientes) {
			if (evento.checkFechaLimite(LocalDate.now()) && evento.isFinalizarVotacion()) {
				misEventosMostrar.add(evento);
			}
		}

		misEventosMostrar.removeAll(eventoService.findAllEventosById(listaIds));

		List<Evento> eventosTotal = eventoService.findEventosByOrganizador(currentUser);

		List<Evento> misEventosInvitar = new ArrayList<Evento> ();

		for (Evento evento: eventosTotal) {

			if (evento.isInvitacionesCerradas() == false) {

				misEventosInvitar.add(evento);
			}
		}

		List<EventoFinal> misEventosFinal = eventoFinalService.findEventosFinalesByInvitado(currentUser.getId());

		List<EventoFinal> misEventosFinales = new ArrayList<EventoFinal> ();

		for (EventoFinal eventoFinal: misEventosFinal) {

			if (eventoFinal.getSala() != null && !eventoFinal.isEventoFinalizado()) {
				if (eventoFinal.getHorarioDefinitivoFin().isAfter(LocalDateTime.now())) {
					misEventosFinales.add(eventoFinal);
				}
			}
		}
		List<EventoFinal> misEventosHistoricos = new ArrayList<EventoFinal> ();

		for (EventoFinal eventoFinal: misEventosFinal) {

			if (eventoFinal.isEventoFinalizado() || eventoFinal.getHorarioDefinitivoFin().isBefore(LocalDateTime.now())) {

				misEventosHistoricos.add(eventoFinal);
			}
		}

		if (currentUser != null) {
			model.addAttribute("usuario", currentUser);
			model.addAttribute("titulo", messageSource.getMessage("text.menu.misEventos", null, locale));
			model.addAttribute("listaMisEventos", misEventosMostrar);
			model.addAttribute("misEventosInvitar", misEventosInvitar);
			model.addAttribute("misEventosFinales", misEventosFinales);
			model.addAttribute("misEventosHistoricos", misEventosHistoricos);
		}

		return "/eventos/inicioEventos";
	}

	/**
	 * Listar eventos.
	 *
	 * @param page           the page
	 * @param model          the model
	 * @param request        the request
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = {"/listadoEventos"}, method = RequestMethod.GET)
	public String listarEventos(@RequestParam(name = "page", defaultValue = "0") int page, Model model, HttpServletRequest request, Authentication authentication, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		List<Evento> eventosTotal = eventoService.findEventosByOrganizador(currentUser);

		List<Evento> eventos = new ArrayList<Evento> ();

		for (Evento evento: eventosTotal) {

			if (evento.isInvitacionesCerradas() == false) {

				eventos.add(evento);
			}
		}

		model.addAttribute("titulo", messageSource.getMessage("text.eventos.inicioEventos.invitaciones", null, locale));
		model.addAttribute("usuario", currentUser);
		model.addAttribute("eventos", eventos);

		return "eventos/listadoEventos";
	}

	/**
	 * Listar eventos creados.
	 *
	 * @param page           the page
	 * @param model          the model
	 * @param request        the request
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = {"/listadoEventosCreados"}, method = RequestMethod.GET)
	public String listarEventosCreados(@RequestParam(name = "page", defaultValue = "0") int page, Model model, HttpServletRequest request, Authentication authentication, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Evento> eventos = eventoService.findEventosByOrganizador(currentUser, pageRequest);

		PageRender<Evento> pageRender = new PageRender<Evento> ("/eventos/listadoEventosCreados", eventos);

		model.addAttribute("titulo", messageSource.getMessage("text.listadoEventos.creados", null, locale));
		model.addAttribute("usuario", currentUser);
		model.addAttribute("eventos", eventos);
		model.addAttribute("page", pageRender);

		return "eventos/listadoEventosCreados";
	}

	/**
	 * Listar eventos pendientes.
	 *
	 * @param page           the page
	 * @param model          the model
	 * @param request        the request
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = {"/listadoEventosPendientes"}, method = RequestMethod.GET)
	public String listarEventosPendientes(@RequestParam(name = "page", defaultValue = "0") int page, Model model, HttpServletRequest request, Authentication authentication, RedirectAttributes flash, Locale locale) {

		Usuario usuario = usuarioService.findUsuarioByUsername(authentication.getName());
		List<Evento> misEventos = eventoService.findEventosByUsuarioId(usuario.getId());

		List<Evento> misEventosMostrar = new ArrayList<Evento> ();

		Long idU = (long) usuario.getId();

		List<Long> listaIds = eventoService.findIdsVotacionesInvitadoEvento(idU);

		for (Evento evento: misEventos) {
			if (evento.checkFechaLimite(LocalDate.now()) && evento.isFinalizarVotacion()) {
				misEventosMostrar.add(evento);
			}
		}

		misEventosMostrar.removeAll(eventoService.findAllEventosById(listaIds));
		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Evento> eventos = eventoService.findEventosByUsuarioId(usuario.getId(), pageRequest);

		PageRender<Evento> pageRender = new PageRender<Evento> ("/eventos/listadoEventosPendientes", eventos);

		model.addAttribute("titulo", messageSource.getMessage("text.eventos.pendientes.titulo", null, locale));
		model.addAttribute("usuario", usuario);
		model.addAttribute("eventos", misEventosMostrar);
		model.addAttribute("page", pageRender);

		return "eventos/listadoEventosPendientes";
	}

	/**
	 * Crear evento.
	 *
	 * @param model          the model
	 * @param authentication the authentication
	 * @param request        the request
	 * @param flash          the flash
	 * @param session        the session
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = "/registroEvento")
	public String crearEvento(Map<String, Object> model, Authentication authentication, HttpServletRequest request, RedirectAttributes flash, HttpSession session, Locale locale) {

		Usuario usuario = usuarioService.findUsuarioByUsername(authentication.getName());

		if (usuario == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noUsuario", null, locale));
			return "redirect:/";
		}

		Evento evento = new Evento();

		List<Horario> horarios = new ArrayList<>();

		Horario horario1 = new Horario(LocalDateTime.now());
		horarios.add(horario1);

		Horario horario2 = new Horario(LocalDateTime.now());
		horarios.add(horario2);

		evento.setHorariosDisponibles(horarios);

		evento.setOrganizador(usuario);

		int duracion = 0;

		model.put("duracion", duracion);
		model.put("horarios", evento.getHorariosDisponibles());
		model.put("fechaLimite", evento.getFechaLimite());
		model.put("usuario", usuario);
		model.put("evento", evento);
		model.put("titulo", messageSource.getMessage("text.eventos.nuevoEvento", null, locale));
		evento.setHorarioFinal();

		return "eventos/registroEvento";
	}

	/**
	 * Guardar evento.
	 *
	 * @param evento         the evento
	 * @param result         the result
	 * @param model          the model
	 * @param flash          the flash
	 * @param authentication the authentication
	 * @param status         the status
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = "/registroEvento", method = RequestMethod.POST)
	public String guardarEvento(@Valid Evento evento, BindingResult result, Model model, RedirectAttributes flash, Authentication authentication, SessionStatus status, Locale locale) {

		Usuario usuario = usuarioService.findUsuarioByUsername(authentication.getName());

		if (result.hasErrors()) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.error.form", null, locale));
			model.addAttribute("titulo", messageSource.getMessage("text.eventos.nuevo.crear", null, locale));
			model.addAttribute("usuario", usuario);
			model.addAttribute("evento", evento);
			return "eventos/registroEvento";
		}

		if (usuario == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noUsuario", null, locale));
			return "redirect:/";
		}

		String mensajeFlash = (evento.getId() != null) ? messageSource.getMessage("text.alert.eventoEditado", null, locale) : messageSource.getMessage("text.alert.eventoCreado", null, locale);

		try {

			evento.setHorarioFinal();
			eventoService.saveEvento(evento);

		} catch (DataIntegrityViolationException e) {

			mensajeFlash = messageSource.getMessage("text.alert.eventoExiste", null, locale) + evento.getTitulo() + messageSource.getMessage("text.alert.eventoFechaTope", null, locale) + evento.getFechaLimite();

			flash.addFlashAttribute("error", mensajeFlash);

			if (evento.getId() != null) {
				return "redirect:/eventos/registroEvento/" + evento.getId();
			} else {
				return "redirect:/eventos/registroEvento";
			}

		}

		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/eventos/listadoEventos";
	}

	/**
	 * Eliminar evento.
	 *
	 * @param id             the id
	 * @param flash          the flash
	 * @param request        the request
	 * @param authentication the authentication
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = "/eliminarEvento/{id}")
	public String eliminarEvento(@PathVariable(value = "id") Long id, RedirectAttributes flash, HttpServletRequest request, Authentication authentication, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
		String mensajeFlash = messageSource.getMessage("text.alert.eventoEliminado", null, locale);

		if (id > 0) {

			Evento evento = eventoService.findEventoById(id);

			if (evento != null) {
				
				if(evento.getOrganizador()==currentUser && !evento.isFinalizarVotacion()) {

					eventoService.deleteEvento(evento);
					flash.addFlashAttribute("success", mensajeFlash);
					
				} else {
					
					return "redirect:/error_403";
				}

			} else {
				
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noEventoId", null, locale));
				return "redirect:/";
			}
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.eventoId", null, locale));
			return "redirect:/";

		}

		return "redirect:/";
	}

	/**
	 * Rechazar invitacion.
	 *
	 * @param id             the id
	 * @param flash          the flash
	 * @param request        the request
	 * @param authentication the authentication
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = "/rechazarInvitacion/{id}")
	public String rechazarInvitacion(@PathVariable(value = "id") Long id, RedirectAttributes flash, HttpServletRequest request, Authentication authentication, Locale locale) {

		boolean encontrado = false;

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
		List<Evento> misEventos = eventoService.findEventosByUsuarioId(currentUser.getId());

		for (int i = 0; i<misEventos.size(); i++) {

			if (misEventos.get(i).getId() == id) {

				encontrado = true;

				misEventos.get(i).getInvitados().remove(currentUser);
				eventoService.saveEvento(misEventos.get(i));

				break;
			}
		}

		if (encontrado) {

			flash.addFlashAttribute("success", messageSource.getMessage("text.eventos.flash.rechazar", null, locale));
			return "redirect:/eventos/listadoEventosPendientes";

		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.eventos.flash.rechazar.error", null, locale));
			return "redirect:/";
		}

	}

	/**
	 * Ver evento creado.
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
	@RequestMapping(value = "/verEventoCreado/{id}", method = RequestMethod.GET)
	public String verEventoCreado(@PathVariable(value = "id") Long id, @RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Evento evento = eventoService.findEventoById(id);
		Usuario usuario = usuarioService.findUsuarioByUsername(authentication.getName());

		if (evento == null) {

			if (request.isUserInRole("ROLE_ADMIN")) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.eventos.flash.null.error", null, locale));
				return "redirect:/";

			} else {

				return "redirect:/error_403";
			}

		} else {

			if (evento.getOrganizador() == usuario) {

				Long idV = evento.getId();
				List<Votacion> votaciones = eventoService.findVotacionByEventoId(idV);

				Moda moda = new Moda(votaciones);
				Horario horarioPreferido = new Horario();
				String mensaje = null;

				if (!votaciones.isEmpty()) {
					// se calcula la moda de los horarios seleccionados
					Long modaEvento = (long) moda.resultado();
					horarioPreferido = eventoService.findHorarioById(modaEvento);
					evento.setHorarioFinal(horarioPreferido.getHorarioFlow());
					mensaje = messageSource.getMessage("text.eventos.preferidoAhora", null, locale);

				} else {

					horarioPreferido = evento.getHorariosDisponibles().get(0);

					evento.setHorarioFinal(evento.getHorariosDisponibles().get(0).getHorarioFlow());
					mensaje = messageSource.getMessage("text.eventos.noVotosHorario", null, locale);
				}

				if (evento.isFinalizarVotacion()) {
					String mensajeFlash = messageSource.getMessage("text.alert.finalVotacion", null, locale);
					flash.addFlashAttribute("warning", mensajeFlash);

					return "redirect:/eventos/listadoEventosFinales";
				}

				model.addAttribute("fechaActual", LocalDate.now());
				model.addAttribute("evento", evento);
				model.addAttribute("usuario", usuario);
				model.addAttribute("listaInvitados", evento.getInvitados());
				model.addAttribute("horarioPreferido", evento.getHorarioFinal());
				model.addAttribute("titulo", messageSource.getMessage("text.eventos.detalle", null, locale));
				model.addAttribute("mensaje", mensaje);

				return "eventos/verEventoCreado";
			} else {

				return "redirect:/error_403";

			}
		}

	}

	/**
	 * Crear evento final.
	 *
	 * @param evento         the evento
	 * @param result         the result
	 * @param model          the model
	 * @param authentication the authentication
	 * @param request        the request
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = "/eventoCreado-add", method = RequestMethod.POST)
	public String crearEventoFinal(@Valid Evento evento, BindingResult result, Model model, Authentication authentication, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		if (result.hasErrors()) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.errorEventoVal", null, locale));
			model.addAttribute("usuario", currentUser);
			model.addAttribute("evento", evento);
			return "redirect:/";
		}

		// Se actualiza la votación
		Long idV = evento.getId();
		List<Votacion> votaciones = eventoService.findVotacionByEventoId(idV);

		Moda moda = new Moda(votaciones);
		Horario horarioPreferido = new Horario();

		if (!votaciones.isEmpty()) {
			// Se calcula la moda de los horarios seleccionados
			Long modaEvento = (long) moda.resultado();
			horarioPreferido = eventoService.findHorarioById(modaEvento);
			evento.setHorarioFinal(horarioPreferido.getHorarioFlow());
		} else {

			horarioPreferido = evento.getHorariosDisponibles().get(0);

			evento.setHorarioFinal(evento.getHorariosDisponibles().get(0).getHorarioFlow());

		}
		evento.setFechaLimite(LocalDate.now());

		evento.setFinalizarVotacion(true);

		eventoService.saveEvento(evento);

		EventoFinal eventoFinal = new EventoFinal(evento.getTitulo(), evento.getDescripcion(), evento.getOrganizador(), evento.getDuracion(), evento.getInvitados(), horarioPreferido.getHorarioFlow());
		// Se guarda el id del evento padre
		eventoFinal.setEventoPadre(evento.getId());
		eventoFinalService.saveEventoFinal(eventoFinal);

		String mensajeFlash = messageSource.getMessage("text.alert.finalVotacion", null, locale);
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:/eventos/listadoEventosFinales";
	}

	/**
	 * Ver evento.
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
	@RequestMapping(value = "/verEvento/{id}", method = RequestMethod.GET)
	public String verEvento(@PathVariable(value = "id") Long id, @RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Evento evento = eventoService.findEventoById(id);
		Usuario usuario = usuarioService.findUsuarioByUsername(authentication.getName());

		List<Usuario> listaRegistrados = usuarioService.findAllUsuarios();

		if (evento == null) {

			if (request.isUserInRole("ROLE_ADMIN")) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.eventos.flash.null.error", null, locale));
				return "redirect:/";

			} else {

				return "redirect:/error_403";
			}

		} else {

			evento.setRegistrados(listaRegistrados);
			model.addAttribute("evento", evento);
			model.addAttribute("usuario", usuario);
			model.addAttribute("listaRegistrados", evento.getRegistrados());
			model.addAttribute("titulo", messageSource.getMessage("text.eventos.detalle", null, locale));

			if (usuario == null || usuario != evento.getOrganizador()) {

				return "redirect:/error_403";

			} else if (!evento.isInvitacionesCerradas()) {

				return "eventos/verEvento";

			} else {

				flash.addFlashAttribute("warning", messageSource.getMessage("text.eventos.flash.info.invitacionesEnviadas", null, locale));
				return "redirect:/eventos/inicioEventos";
			}
		}
	}

	/**
	 * Adds the invitados.
	 *
	 * @param evento         the evento
	 * @param result         the result
	 * @param model          the model
	 * @param authentication the authentication
	 * @param request        the request
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = "/verEvento-add", method = RequestMethod.POST)
	public String addInvitados(@Valid Evento evento, BindingResult result, Model model, Authentication authentication, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
		String seleccionaInvitado = messageSource.getMessage("text.alert.eventInv", null, locale);

		if (result.hasErrors()) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.errorEventoVal", null, locale));
			model.addAttribute("usuario", currentUser);
			model.addAttribute("evento", evento);
			return "redirect:/";
		}

		List<Usuario> invitadosRegistrados = new ArrayList<Usuario> ();
		invitadosRegistrados = evento.getRegistrados();

		List<Usuario> invitados = invitadosRegistrados.stream().filter(usuario -> usuario != null).collect(Collectors.toList());

		evento.getInvitados().add(currentUser);

		if (invitados.size() != 0) {
			for (int i = 0; i<invitados.size(); i++) {

				evento.getInvitados().add(invitados.get(i));
			}

			String mensajeFlash = messageSource.getMessage("text.alert.evento.invitaciones", null, locale);

			evento.setInvitacionesCerradas(true);
			eventoService.saveEvento(evento);

			flash.addFlashAttribute("success", mensajeFlash);
			return "redirect:/eventos/inicioEventos";
		} else {
			flash.addFlashAttribute("warning", seleccionaInvitado);
			return "redirect:/eventos/verEvento/" + evento.getId();
		}
	}

	/**
	 * Ver evento horario.
	 *
	 * @param id             the id
	 * @param model          the model
	 * @param authentication the authentication
	 * @param request        the request
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = "/eventoHorario/{id}", method = RequestMethod.GET)
	public String verEventoHorario(@PathVariable(value = "id") Long id, Model model, Authentication authentication, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Evento evento = eventoService.findEventoById(id);
		Usuario usuario = usuarioService.findUsuarioByUsername(authentication.getName());

		if (evento == null) {

			if (request.isUserInRole("ROLE_ADMIN")) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.eventos.flash.null.error", null, locale));
				return "redirect:/usuarios/listadoUsuarios";

			} else {

				return "redirect:/error_403";
			}

		} else {

			model.addAttribute("usuario", usuario);
			model.addAttribute("evento", evento);
			model.addAttribute("usuariosInvitados", evento.getInvitados());
			model.addAttribute("horariosDisponibles", evento.getHorariosDisponibles());
			model.addAttribute("titulo", messageSource.getMessage("text.eventos.detalle", null, locale));
			model.addAttribute("fechaLimite", evento.getFechaLimite());

			evento.getInvitados().forEach(invitaciones -> System.out.println(invitaciones.getNombre()));

			if (usuario == null) {

				return "redirect:/error_403";

			} else {

				return "eventos/eventoHorario";
			}
		}
	}

	/**
	 * Adds the id horario.
	 *
	 * @param evento         the evento
	 * @param model          the model
	 * @param result         the result
	 * @param authentication the authentication
	 * @param request        the request
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = "/eventoHorario-add", method = RequestMethod.POST)
	public String addIdHorario(@Valid Evento evento, Model model, BindingResult result, Authentication authentication, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		if (result.hasErrors()) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.errorEventoVal", null, locale));
			model.addAttribute("usuario", currentUser);
			model.addAttribute("evento", evento);
			return "redirect:/";
		}

		List<Horario> horarios = evento.getHorariosDisponibles();
		List<Horario> horarios2 = horarios.stream().filter(hora -> hora != null).collect(Collectors.toList());
		if (horarios2.size() != 0) {
			Long id = horarios2.get(0).getId();
			Votacion votacion = new Votacion(evento.getId(), currentUser.getId(), id);
			try {
				eventoService.saveVotacion(votacion);

			} catch (DataIntegrityViolationException e) {

				String mensajeFlash = messageSource.getMessage("text.alert.votoUnico", null, locale);

				flash.addFlashAttribute("error", mensajeFlash);

				return "redirect:/";
			}
			emailService.EnviarMailVotado(evento, currentUser, horarios.get(0));
			flash.addFlashAttribute("success", messageSource.getMessage("text.alert.graciasVoto", null, locale));
			return "redirect:/eventos/inicioEventos";
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.selectHorario", null, locale));
			return "redirect:/eventos/eventoHorario/" + evento.getId();
		}
	}

	/**
	 * Listar eventos finales.
	 *
	 * @param page           the page
	 * @param model          the model
	 * @param request        the request
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @param locale         the locale
	 * @return the string
	 */
	@RequestMapping(value = {"/listadoEventosFinales"}, method = RequestMethod.GET)
	public String listarEventosFinales(@RequestParam(name = "page", defaultValue = "0") int page, Model model, HttpServletRequest request, Authentication authentication, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		List<EventoFinal> misEventos = eventoFinalService.findEventoFinalsByOrganizador(currentUser);

		List<EventoFinal> misEventosFinales = new ArrayList<EventoFinal> ();

		String mensajeFlash = messageSource.getMessage("text.alert.eventoSinSala", null, locale);

		for (EventoFinal eventoFinal: misEventos) {

			if (eventoFinal.getSala() == null) {

				misEventosFinales.add(eventoFinal);
			}
		}

		if (misEventosFinales.size() == 0) {

			flash.addFlashAttribute("warning", mensajeFlash);

			return "redirect:/eventos/inicioEventos";
		}

		model.addAttribute("titulo", messageSource.getMessage("text.eventos.finales.titulo", null, locale));
		model.addAttribute("usuario", currentUser);
		model.addAttribute("eventos", misEventosFinales);

		return "eventos/listadoEventosFinales";
	}

	/**
	 * Ver evento final.
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
	@RequestMapping(value = "/verEventoFinal/{id}", method = RequestMethod.GET)
	public String verEventoFinal(@PathVariable(value = "id") Long id, @RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		EventoFinal eventoFinal = eventoFinalService.findEventoFinalById(id);
		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		if (eventoFinal == null) {
			if (request.isUserInRole("ROLE_ADMIN")) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.eventos.flash.null.error", null, locale));
				return "redirect:/";

			} else {

				return "redirect:/error_403";
			}

		} else {

			if (currentUser == null || currentUser != eventoFinal.getOrganizador()) {

				return "redirect:/error_403";
			}

			if (eventoFinal.getSala() != null) {

				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.eventoConSala", null, locale));
				return "redirect:/eventos/listadoEventosCreados";

			} else {

				model.addAttribute("evento", eventoFinal);
				model.addAttribute("usuario", currentUser);
				model.addAttribute("listaParticipantes", eventoFinal.getInvitados());
				model.addAttribute("horarioDefinitivo", eventoFinal.getHorarioDefinitivo());
				model.addAttribute("titulo", messageSource.getMessage("text.eventos.eventoFinal.detalle", null, locale));

				return "eventos/verEventoFinal";
			}

		}

	}

}
