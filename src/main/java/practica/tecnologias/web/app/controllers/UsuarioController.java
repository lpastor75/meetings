package practica.tecnologias.web.app.controllers;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import practica.tecnologias.web.app.models.entity.Role;
import practica.tecnologias.web.app.models.entity.Usuario;
import practica.tecnologias.web.app.models.service.IUsuarioService;
import practica.tecnologias.web.app.utils.paginator.PageRender;

/**
 * Clase controladora de los usuarios del sistema, encargada de gestionar cualquier interacción con ellos en la aplicación.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Controller
@SessionAttributes("usuario")
public class UsuarioController {

	/** The logger. */
	protected final Log logger = LogFactory.getLog(this.getClass());

	/** The usuario service. */
	@Autowired
	private IUsuarioService usuarioService;

	/** The password encoder. */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Iniciar.
	 *
	 * @param model the model
	 * @param authentication the authentication
	 * @param locale the locale
	 * @return the string
	 */
	@GetMapping({"/inicio", "/"})
	public String iniciar(Model model, Authentication authentication, Locale locale) {

		Usuario usuario = null;

		if (authentication != null) {
			usuario = usuarioService.findUsuarioByUsername(authentication.getName());
		}

		model.addAttribute("titulo", messageSource.getMessage("text.inicio.titulo", null, locale));

		if (usuario != null) {
			model.addAttribute("usuario", usuario);
		}

		return "inicio";
	}

	@GetMapping("/faqs")
	public String faqs(Model model, Authentication authentication, Locale locale) {

		Usuario usuario = null;

		if (authentication != null) {
			usuario = usuarioService.findUsuarioByUsername(authentication.getName());
		}

		model.addAttribute("titulo", messageSource.getMessage("text.faqs.titulo", null, locale));

		if (usuario != null) {
			model.addAttribute("usuario", usuario);
		}

		return "faqs";
	}

	/**
	 * Crear usuario.
	 *
	 * @param model the model
	 * @param authentication the authentication
	 * @param request the request
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/usuarios/registroUsuario")
	public String crearUsuario(Map<String, Object> model, Authentication authentication, HttpServletRequest request, Locale locale) {

		if (authentication != null && !request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/error_403";

		} else {

			Usuario usuario = new Usuario();

			model.put("usuario", usuario);

			if (!request.isUserInRole("ROLE_ADMIN")) {
				model.put("titulo", messageSource.getMessage("text.registroUsuario.usuario.titulo", null, locale));
			} else {
				model.put("titulo", messageSource.getMessage("text.menu.nuevoJefe", null, locale));
			}
			return "usuarios/registroUsuario";
		}
	}

	/**
	 * Editar usuario.
	 *
	 * @param id the id
	 * @param model the model
	 * @param authentication the authentication
	 * @param request the request
	 * @param flash the flash
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/usuarios/registroUsuario/{id}")
	public String editarUsuario(@PathVariable(value = "id") Long id, Map<String, Object> model,
		Authentication authentication, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Usuario usuario = null;

		if (id > 0) {

			usuario = usuarioService.findUsuarioById(id);
			if (usuario == null) {

				if (request.isUserInRole("ROLE_ADMIN")) {
					flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noUsuarioId", null, locale));
					return "redirect:/usuarios/listadoUsuarios";
				} else {
					return "redirect:/error_403";
				}
			}

		} else {

			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.usuarioId", null, locale));
			return "redirect:/usuarios/listadoUsuarios";
		}

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		if (!request.isUserInRole("ROLE_ADMIN") && !currentUser.equals(usuario)) {

			return "redirect:/error_403";

		} else {

			model.put("usuario", usuario);
			model.put("titulo", messageSource.getMessage("text.usuario.editarUsuario", null, locale));
			return "usuarios/registroUsuario";
		}
	}

	/**
	 * Guardar usuario.
	 *
	 * @param usuario the usuario
	 * @param result the result
	 * @param model the model
	 * @param request the request
	 * @param flash the flash
	 * @param status the status
	 * @param authentication the autehntication
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/usuarios/registroUsuario", method = RequestMethod.POST)
	public String guardarUsuario(@Valid Usuario usuario, BindingResult result, Model model, HttpServletRequest request,
		RedirectAttributes flash, SessionStatus status, Authentication authentication, Locale locale) {

		Usuario currentUser = null;

		if (result.hasErrors()) {
			if (usuario.getId() != null) {
				model.addAttribute("titulo", messageSource.getMessage("text.usuario.editarUsuario", null, locale));
			} else {
				if (!request.isUserInRole("ROLE_ADMIN"))
					model.addAttribute("titulo", messageSource.getMessage("text.registroUsuario.usuario.titulo", null, locale));
				model.addAttribute("titulo", messageSource.getMessage("text.registroUsuario.jefe.titulo", null, locale));
			}
			return "usuarios/registroUsuario";
		}

		String mensajeFlash = (usuario.getId() != null) ? messageSource.getMessage("text.alert.usuarioEditado", null, locale) :
			messageSource.getMessage("text.alert.usuarioCreado", null, locale);

		try {

			if (request.isUserInRole("ROLE_ADMIN") && !usuario.getIsJefe()) {

				usuario.addRole(new Role("ROLE_JEFE"));
				usuario.setIsJefe(true);
			}
			String bcryptPassword = passwordEncoder.encode(usuario.getPassword());
			usuario.setPassword(bcryptPassword);

			usuarioService.saveUsuario(usuario);

		} catch (DataIntegrityViolationException e) {
			mensajeFlash = messageSource.getMessage("text.alert.usuarioExiste", null, locale) + usuario.getUsername() +
				messageSource.getMessage("text.alert.usuarioUsername", null, locale);
			flash.addFlashAttribute("error", mensajeFlash);

			if (usuario.getId() != null) {
				return "redirect:/usuarios/registroUsuario/" + usuario.getId();
			} else {
				return "redirect:/usuarios/registroUsuario";
			}
		}

		status.setComplete();
		if (authentication != null) {
			currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
		}
		if (currentUser != null) {
			model.addAttribute("usuario", currentUser);
		}
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/";
	}

	/**
	 * Ver usuario.
	 *
	 * @param id the id
	 * @param model the model
	 * @param authentication the authentication
	 * @param session the session
	 * @param request the request
	 * @param flash the flash
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/usuarios/verUsuario/{id}", method = RequestMethod.GET)
	public String verUsuario(@PathVariable(value = "id") Long id, Map<String, Object> model,
		Authentication authentication, HttpSession session, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Usuario usuario = usuarioService.findUsuarioById(id);

		if (usuario == null) {

			if (request.isUserInRole("ROLE_ADMIN")) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noUsuarioId", null, locale));
				return "redirect:/";

			} else {

				return "redirect:/error_403";
			}
		}

		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		if (!request.isUserInRole("ROLE_ADMIN") && !currentUser.equals(usuario)) {
			return "redirect:/error_403";

		} else {
			model.put("usuario", usuario);
			model.put("titulo", messageSource.getMessage("text.usuario.verUsuario.titulo", null, locale));
			return "usuarios/verUsuario";
		}
	}

	/**
	 * Listar usuarios.
	 *
	 * @param page the page
	 * @param model the model
	 * @param request the request
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/usuarios/listadoUsuarios", method = RequestMethod.GET)
	public String listarUsuarios(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
		HttpServletRequest request, Locale locale) {

		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Usuario> usuarios = usuarioService.findAllUsuarios(pageRequest);

		PageRender<Usuario> pageRender = new PageRender<Usuario> ("/usuarios/listadoUsuarios", usuarios);

		model.addAttribute("titulo", messageSource.getMessage("text.usuarios.listadoUsuarios.titulo", null, locale));
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);

		return "usuarios/listadoUsuarios";
	}

	/**
	 * Adds the role jefe.
	 *
	 * @param id the id
	 * @param model the model
	 * @param flash the flash
	 * @param locale the locale
	 * @return the string
	 */
	@Secured(value = {"ROLE_ADMIN"})
	@RequestMapping(value = "/usuarios/addRoleJefe/{id}")
	public String addRoleJefe(@PathVariable(value = "id") Long id, Map<String, Object> model,
		RedirectAttributes flash, Locale locale) {

		if (id > 0) {

			Usuario usuario = usuarioService.findUsuarioById(id);

			if (usuario != null) {

				String mensajeFlash = messageSource.getMessage("text.alert.roleJefe", null, locale) + " " + usuario.getNombre() + " " +
					usuario.getApellido() + " " + messageSource.getMessage("text.alert.roleCorrecto", null, locale);
				flash.addFlashAttribute("success", mensajeFlash);
				usuario.getRoles().add(new Role("ROLE_JEFE"));
				usuario.setIsJefe(true);
				usuarioService.saveUsuario(usuario);
				model.put("usuario", usuario);

			} else {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noUsuarioId", null, locale));
				return "redirect:/";
			}
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.usuarioId", null, locale));
			return "redirect:/";

		}

		return "redirect:/usuarios/listadoUsuarios";
	}

	/**
	 * Error.
	 *
	 * @param model the model
	 * @param authentication the authentication
	 * @return the string
	 */
	@RequestMapping(value = {"/error_403"}, method = RequestMethod.GET)
	public String error(Model model, Authentication authentication) {

		Usuario usuario = null;

		if (authentication != null) {
			usuario = usuarioService.findUsuarioByUsername(authentication.getName());
		}

		if (usuario != null) {
			model.addAttribute("usuario", usuario);
		}

		return "error_403";
	}

}
