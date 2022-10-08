package practica.tecnologias.web.app.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import practica.tecnologias.web.app.models.entity.Contacto;
import practica.tecnologias.web.app.models.entity.EventoFinal;
import practica.tecnologias.web.app.models.entity.FileInfo;
import practica.tecnologias.web.app.models.entity.FileModel;
import practica.tecnologias.web.app.models.entity.Mensaje;
import practica.tecnologias.web.app.models.entity.Usuario;
import practica.tecnologias.web.app.models.service.IEventoFinalService;
import practica.tecnologias.web.app.models.service.IUploadFileService;
import practica.tecnologias.web.app.models.service.IUsuarioService;

/** 
 * Clase encargada de controlar lo relativo al chat y a la reunión, tanto para usuarios registrados 
 * como para usuarios no registrados.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Controller
@SessionAttributes("eventoFinal")
public class ChatController {

	/** The usuario service. */
	@Autowired
	private IUsuarioService usuarioService;

	/** The evento final service. */
	@Autowired
	private IEventoFinalService eventoFinalService;

	/** The file service. */
	@Autowired
	private IUploadFileService fileService;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Crear chat.
	 *
	 * @param id             the id
	 * @param request        the request
	 * @param model          the model
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping("/eventos/reunion/{id}")
	public String crearChat(@PathVariable(value = "id") Long id, HttpServletRequest request, Model model,
		Authentication authentication, RedirectAttributes flash, Locale locale) {

		EventoFinal eventoFinalUser = eventoFinalService.findEventoFinalById(id);
		Usuario currentUser = null;
		String username = null;

		if (eventoFinalUser == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noEventoId", null, locale));
			return "redirect:/";

		} else {

			if (eventoFinalUser.isEventoFinalizado() == true) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.chat.fin", null, locale));

				return "redirect:/eventos/inicioEventos";
			}
			
			if (authentication != null) {
				
				currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
				username = currentUser.getUsername();
			}
			
			if (currentUser==null || !eventoFinalUser.getInvitados().contains(currentUser)) {
				
				return "redirect:/error_403";
			}
			
			LocalDateTime fecha1 = eventoFinalUser.getHorarioDefinitivo();
			LocalDateTime fechaAhora = LocalDateTime.now();

			Long minutosDiferencia = Duration.between(fechaAhora, fecha1).toMinutes();
			if (eventoFinalUser.getHorarioDefinitivoFin().isAfter(LocalDateTime.now()) && (minutosDiferencia<5)) {

				if (username == null || username.isEmpty()) {
					return "redirect:/login";
				}

				List<FileModel> archivos = fileService.findFilesByEventoFinalId(id);

				List<FileInfo> fileInfos = archivos.stream().map(fileModel -> {
					Long idFile = fileModel.getId();
					String filename = fileModel.getName();
					String url = MvcUriComponentsBuilder
					.fromMethodName(ChatController.class, "downloadFile", fileModel.getName()).build()
					.toString();
					return new FileInfo(filename, url, idFile);
				}).collect(Collectors.toList());

				String bandera = String.valueOf(eventoFinalUser.isEventoFinalizado());
				model.addAttribute("usuario", currentUser);
				model.addAttribute("eventoFinal_id", eventoFinalUser.getId());
				model.addAttribute("bandera", bandera);
				model.addAttribute("username", username);
				model.addAttribute("titulo", messageSource.getMessage("text.eventos.celebracion", null, locale));
				model.addAttribute("eventoFinalUser", eventoFinalUser);
				model.addAttribute("files", fileInfos);
				return "eventos/reunion";
				
			} else {
				
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.chat.minutos", null, locale));
				return "redirect:/eventos/inicioEventos";
			}
		}
	}

	/**
	 * Download file.
	 *
	 * @param filename the filename
	 * @return the response entity
	 */
	@GetMapping("/eventos/reunion/{id}/{filename}")
	public ResponseEntity<byte[] > downloadFile(@PathVariable String filename) {
		FileModel file = fileService.findFileModelByName(filename);
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
			.body(file.getPic());
	}

	/**
	 * Crear evento final.
	 *
	 * @param model           the model
	 * @param authentication  the authentication
	 * @param request         the request
	 * @param flash           the flash
	 * @param locale          the locale
	 * @return the string
	 */
	@RequestMapping(value = "/eventos/reunion-add", method = RequestMethod.POST)
	public String crearEventoFinal(Model model, Authentication authentication, HttpServletRequest request,
		RedirectAttributes flash, Locale locale) {

		Long id = Long.valueOf(request.getParameter("eventoFinalUser"));
		EventoFinal eventoFinalUser = eventoFinalService.findEventoFinalById(id);
		eventoFinalUser.setEventoFinalizado(true);
		eventoFinalUser.setHorarioDefinitivoFin(LocalDateTime.now());
		eventoFinalService.saveEventoFinal(eventoFinalUser);

		String mensajeFlash = messageSource.getMessage("text.alert.finReunion", null, locale);
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:/eventos/listadoEventosPendientes";
	}

	/**
	 * Upload file.
	 *
	 * @param file               the file
	 * @param model              the model
	 * @param authentication     the authentication
	 * @param redirectAttributes the redirect attributes
	 * @param request            the request
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/eventos/reunion-upload", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file, Model model, Authentication authentication,
		RedirectAttributes redirectAttributes, HttpServletRequest request, Locale locale) {

		Long id = Long.valueOf(request.getParameter("eventoFinalUser"));
		EventoFinal eventoFinalUser = eventoFinalService.findEventoFinalById(id);
		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		fileService.uploadFile(file, eventoFinalUser);

		redirectAttributes.addFlashAttribute("info", messageSource.getMessage("text.alert.subidaOk", null, locale) + file.getOriginalFilename());

		String bandera = String.valueOf(eventoFinalUser.isEventoFinalizado());
		model.addAttribute("usuario", currentUser);
		model.addAttribute("eventoFinal_id", eventoFinalUser.getId());
		model.addAttribute("bandera", bandera);
		model.addAttribute("username", currentUser.getUsername());
		model.addAttribute("titulo", messageSource.getMessage("text.eventos.celebracion", null, locale));
		model.addAttribute("eventoFinalUser", eventoFinalUser);

		return "redirect:reunion/" + eventoFinalUser.getId();

	}

	/**
	 * Do login.
	 *
	 * @param request     the request
	 * @param username    the username
	 * @param eventoFinal the evento final
	 * @return the string
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String username,
		@RequestParam EventoFinal eventoFinal) {

		username = username.trim();

		if (username.isEmpty()) {
			return "login";
		}
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("eventoFinal_id", eventoFinal.getId());

		return "redirect:/";
	}

	/**
	 * Ver reunion.
	 *
	 * @param id             the id
	 * @param request        the request
	 * @param model          the model
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping("/eventos/verReunion/{id}")
	public String verReunion(@PathVariable(value = "id") Long id, HttpServletRequest request, Model model,
		Authentication authentication, RedirectAttributes flash, Locale locale) {

		EventoFinal eventoFinalUser = eventoFinalService.findEventoFinalById(id);

		if (eventoFinalUser == null) {

			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noEventoId", null, locale));
			return "redirect:/";

		} else {

			if (eventoFinalUser.isEventoFinalizado() ||
				eventoFinalUser.getHorarioDefinitivoFin().isBefore(LocalDateTime.now())) {

				Usuario currentUser = null;
				String username = null;

				if (authentication != null) {

					currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
					username = currentUser.getUsername();
				}

				if (username == null || username.isEmpty()) {
					return "redirect:/login";
				}

				if (eventoFinalUser.getInvitados().contains(currentUser)) {

					List<FileModel> archivos = fileService.findFilesByEventoFinalId(id);

					List<FileInfo> fileInfos = archivos.stream().map(fileModel -> {
						Long idFile = fileModel.getId();
						String filename = fileModel.getName();
						String url = MvcUriComponentsBuilder
						.fromMethodName(ChatController.class, "downloadFile", fileModel.getName()).build()
						.toString();
						return new FileInfo(filename, url, idFile);
					}).collect(Collectors.toList());

					List<Mensaje> mensajesReunion = eventoFinalService
						.findAllMensajesByEventoFinal(eventoFinalUser.getId());

					model.addAttribute("files", fileInfos);
					model.addAttribute("mensajes", mensajesReunion);
					model.addAttribute("usuario", currentUser);
					model.addAttribute("eventoFinal_id", eventoFinalUser.getId());
					model.addAttribute("username", username);
					model.addAttribute("titulo", messageSource.getMessage("text.eventos.resumen", null, locale));
					model.addAttribute("eventoFinalUser", eventoFinalUser);

					return "eventos/verReunion";

				} else {

					return "redirect:/error_403";
				}

			} else {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.chat.fin", null, locale));
				return "redirect:/eventos/inicioEventos";
			}

		}
	}

	/**
	 * Eliminar mensaje.
	 *
	 * @param eventoFinalUser the evento final user
	 * @param result          the result
	 * @param id              the id
	 * @param model           the model
	 * @param flash           the flash
	 * @param request         the request
	 * @param authentication  the authentication
	 * @param locale the locale
	 * @return the model and view
	 */
	@RequestMapping(value = "/eventos/eliminarMensaje/{id}")
	public ModelAndView eliminarMensaje(@Valid EventoFinal eventoFinalUser, BindingResult result,
		@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash, HttpServletRequest request,
		Authentication authentication, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		Mensaje mensaje = null;
		Long idEventoFinal = null;
		EventoFinal ef = null;

		String mensajeFlash = messageSource.getMessage("text.alert.mensajeDelete", null, locale);

		if (result.hasErrors()) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.eventos.flash.validacion.error", null, locale));
			model.addAttribute("eventoFinalUser", eventoFinalUser);
			return new ModelAndView("redirect:/");
		}

		if (id > 0) {

			mensaje = eventoFinalService.findMensajeById(id);

			if (mensaje != null) {

				idEventoFinal = mensaje.getEventoFinal_id();
				ef = eventoFinalService.findEventoFinalById(idEventoFinal);

				if (ef.getOrganizador() == currentUser) {

					eventoFinalService.deleteMensaje(mensaje);
					flash.addFlashAttribute("success", mensajeFlash);

				} else {

					flash.addFlashAttribute("error", messageSource.getMessage("text.alert.mensajeUndelete", null, locale));
					return new ModelAndView("redirect:/");
				}

			} else {

				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.mensajeIncorrecto", null, locale));
				return new ModelAndView("redirect:/");
			}

		} else {

			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.mensajeIncorrecto", null, locale));
			return new ModelAndView("redirect:/");
		}
		model.addAttribute("usuario", currentUser);

		return new ModelAndView("redirect:/eventos/verReunion/" + idEventoFinal);
	}

	/**
	 * Eliminar archivo.
	 *
	 * @param eventoFinalUser the evento final user
	 * @param result          the result
	 * @param idFile          the id file
	 * @param model           the model
	 * @param flash           the flash
	 * @param request         the request
	 * @param authentication  the authentication
	 * @param locale the locale
	 * @return the model and view
	 */
	@RequestMapping(value = "/eventos/eliminarArchivo/{idFile}")
	public ModelAndView eliminarArchivo(@Valid EventoFinal eventoFinalUser, BindingResult result,
		@PathVariable(value = "idFile") Long idFile, Model model, RedirectAttributes flash,
		HttpServletRequest request, Authentication authentication, Locale locale) {

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		FileModel fileModel = null;
		Long idEventoFinal = null;
		EventoFinal ef = null;

		String mensajeFlash = messageSource.getMessage("text.alert.archivoDelete", null, locale);

		if (result.hasErrors()) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.eventos.flash.validacion.error", null, locale));
			model.addAttribute("eventoFinalUser", eventoFinalUser);
			return new ModelAndView("redirect:/");
		}

		if (idFile > 0) {

			fileModel = fileService.findFileModelById(idFile);

			if (fileModel != null) {

				idEventoFinal = fileModel.getIdEventoFinal();
				ef = eventoFinalService.findEventoFinalById(idEventoFinal);

				if (ef.getOrganizador() == currentUser) {

					fileService.deleteFileModel(fileModel);
					flash.addFlashAttribute("success", mensajeFlash);

				} else {

					flash.addFlashAttribute("error", messageSource.getMessage("text.alert.fileUndelete", null, locale));
					return new ModelAndView("redirect:/");
				}

			} else {

				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.archivoIncorrecto", null, locale));
				return new ModelAndView("redirect:/");
			}

		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.archivoIncorrecto", null, locale));
			return new ModelAndView("redirect:/");
		}
		model.addAttribute("usuario", currentUser);

		return new ModelAndView("redirect:/eventos/verReunion/" + idEventoFinal);
	}

	/**
	 * Repositorio archivos.
	 *
	 * @param id             the id
	 * @param request        the request
	 * @param model          the model
	 * @param authentication the authentication
	 * @param flash          the flash
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping("/eventos/repositorioArchivos/{id}")
	public String repositorioArchivos(@PathVariable(value = "id") Long id, HttpServletRequest request, Model model,
		Authentication authentication, RedirectAttributes flash, Locale locale) {
		
		Usuario currentUser = null;

		EventoFinal eventoFinalUser = eventoFinalService.findEventoFinalById(id);
		
		if (authentication != null) {
			currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
		}

		if (eventoFinalUser == null) {

			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noEventoId", null, locale));
			return "redirect:/";

		} else {

			if (eventoFinalUser.isEventoFinalizado() == true) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.chat.fin", null, locale));

				return "redirect:/";
			}

			LocalDateTime fecha1 = eventoFinalUser.getHorarioDefinitivo();
			LocalDateTime fechaAhora = LocalDateTime.now();

			Long minutosDiferencia = Duration.between(fechaAhora, fecha1).toMinutes();
			if (eventoFinalUser.getHorarioDefinitivoFin().isAfter(LocalDateTime.now()) && (minutosDiferencia<5)) {

				List<FileModel> archivos = fileService.findFilesByEventoFinalId(id);

				List<FileInfo> fileInfos = archivos.stream().map(fileModel -> {
					Long idFile = fileModel.getId();
					String filename = fileModel.getName();
					String url = MvcUriComponentsBuilder
					.fromMethodName(ChatController.class, "downloadFile", fileModel.getName()).build()
					.toString();
					return new FileInfo(filename, url, idFile);
				}).collect(Collectors.toList());
				model.addAttribute("files", fileInfos);
				model.addAttribute("eventoFinal_id", eventoFinalUser.getId());
				model.addAttribute("titulo", messageSource.getMessage("text.eventos.resumen", null, locale));
				model.addAttribute("eventoFinalUser", eventoFinalUser);
				model.addAttribute("usuario", currentUser);

				return "eventos/repositorioArchivos";

			} else {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.chat.fin", null, locale));
				return "redirect:/";
			}

		}
	}

	/**
	 * Ante sala.
	 *
	 * @param id      the id
	 * @param model   the model
	 * @param request the request
	 * @param flash   the flash
	 * @return the string
	 */
	@GetMapping("eventos/anteSalaReunion/{id}")
	public String anteSala(@PathVariable("id") Long id, Model model, HttpServletRequest request,
		RedirectAttributes flash, Locale locale) {
		
		EventoFinal eventoFinal = eventoFinalService.findEventoFinalById(id);
		
		if (eventoFinal == null) {
			
			flash.addFlashAttribute("error", messageSource.getMessage("text.eventos.flash.null.error", null, locale));
			return "redirect:/";
			
		} else {

			Contacto contacto = new Contacto();

			model.addAttribute("eventoFinalUser", eventoFinal);
			model.addAttribute("eventoFinal_id", eventoFinal.getId());
			model.addAttribute("contacto", contacto);
			return "eventos/anteSalaReunion";
		}
	}

	/**
	 * Comprobar asistente.
	 *
	 * @param request        the request
	 * @param email          the email
	 * @param flash          the flash
	 * @param locale the locale
	 * @return the string
	 */
	@PostMapping("/eventos/anteSalaReunion-comprobarAsistente")
	public String comprobarAsistente(HttpServletRequest request, @RequestParam("email") String email, RedirectAttributes flash, Locale locale) {

		Long id = Long.valueOf(request.getParameter("eventoFinalUser"));
		EventoFinal eventoFinalUser = eventoFinalService.findEventoFinalById(id);

		Set<Contacto> asistentesEvento = eventoFinalUser.getAsistentes();
		boolean asistente = false;
		for (Contacto contacto: asistentesEvento) {
			if (contacto.getEmail().equals(email)) {
				asistente = true;
			}
		}
		if (asistente) {
			flash.addFlashAttribute("success", messageSource.getMessage("text.eventos.bienvenida", null, locale));
			return "redirect:/eventos/reunionNoRegistrado/" + eventoFinalUser.getId() + "/" + email;
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noInvitado", null, locale));
			return "redirect:/eventos/anteSalaReunion/" + eventoFinalUser.getId();
		}

	}

	/**
	 * Ver reunion no registrado.
	 *
	 * @param id the id
	 * @param email the email
	 * @param request the request
	 * @param model the model
	 * @param flash the flash
	 * @param locale the locale
	 * @return the string
	 */
	@SuppressWarnings("null")
	@RequestMapping("/eventos/reunionNoRegistrado/{id}/{email}")
	public String verReunionNoRegistrado(@PathVariable(value = "id") Long id,
		@PathVariable(value = "email") String email, HttpServletRequest request, Model model,
		RedirectAttributes flash, Locale locale) {

		EventoFinal eventoFinalUser = eventoFinalService.findEventoFinalById(id);
		if (eventoFinalUser == null) {

			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noEventoId", null, locale));
			return "redirect:/eventos/anteSalaReunion/" + eventoFinalUser.getId();

		} else {
			boolean estoyinvitado = false;
			@SuppressWarnings("unused")
			Contacto asistente = new Contacto();
			for (Contacto invitado: eventoFinalUser.getAsistentes()) {
				if (invitado.getEmail().equals(email)) {
					estoyinvitado = true;
					asistente = invitado;
				}
			}

			if (estoyinvitado) {

				LocalDateTime fecha1 = eventoFinalUser.getHorarioDefinitivo();
				LocalDateTime fechaAhora = LocalDateTime.now();
				Long minutosDiferencia = Duration.between(fechaAhora, fecha1).toMinutes();
				if (eventoFinalUser.getHorarioDefinitivoFin().isAfter(LocalDateTime.now()) && (minutosDiferencia<5)) {
					String bandera = String.valueOf(eventoFinalUser.isEventoFinalizado());
					model.addAttribute("eventoFinal_id", eventoFinalUser.getId());
					model.addAttribute("bandera", bandera);
					model.addAttribute("titulo", eventoFinalUser.getTitulo());
					model.addAttribute("eventoFinalUser", eventoFinalUser);
					return "eventos/reunionNoRegistrado";

				} else {
					flash.addFlashAttribute("error", messageSource.getMessage("text.alert.chat.fin", null, locale));
					return "redirect:/eventos/anteSalaReunion/" + eventoFinalUser.getId();
				}

			} else {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noEntrada", null, locale));
				return "redirect:/eventos/anteSalaReunion/" + eventoFinalUser.getId();
			}
		}

	}

}