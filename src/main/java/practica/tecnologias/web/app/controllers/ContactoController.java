package practica.tecnologias.web.app.controllers;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import practica.tecnologias.web.app.models.entity.Contacto;
import practica.tecnologias.web.app.models.entity.Usuario;
import practica.tecnologias.web.app.models.service.IUsuarioService;
import practica.tecnologias.web.app.utils.paginator.PageRender;

/**
 * Clase controladora encargada de gestionar todo lo relacionado con los contactos de los usuarios.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Controller
@SessionAttributes("usuario")
@RequestMapping("/usuarios")
public class ContactoController {

	/** The usuario service. */
	@Autowired
	private IUsuarioService usuarioService;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Crear contacto.
	 *
	 * @param model the model
	 * @param authentication the authentication
	 * @param flash the flash
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/registroContacto")
	public String crearContacto(Map<String, Object> model, Authentication authentication,
		RedirectAttributes flash, Locale locale) {

		Usuario usuario = usuarioService.findUsuarioByUsername(authentication.getName());

		if (usuario == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noUsuario", null, locale));
			return "redirect:/";
		}

		Contacto contacto = new Contacto();
		contacto.setUsuario(usuario);

		model.put("usuario", usuario);
		model.put("contacto", contacto);
		model.put("titulo", messageSource.getMessage("text.contactos.add", null, locale));

		return "usuarios/registroContacto";
	}

	/**
	 * Editar contacto.
	 *
	 * @param id the id
	 * @param model the model
	 * @param authentication the authentication
	 * @param request the request
	 * @param flash the flash
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/registroContacto/{id}")
	public String editarContacto(@PathVariable(value = "id") Long id, Map<String, Object> model,
		Authentication authentication, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Contacto contacto = null;
		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		Set<Contacto> contactos = currentUser.getContactos();

		if (id > 0) {
			contacto = usuarioService.findContactoById(id);
			if (contacto == null) {
				if (request.isUserInRole("ROLE_ADMIN")) {
					flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noContacto", null, locale));
					return "redirect:/";
				} else {
					return "redirect:/error_403";
				}

			} else {

				if (contactos.contains(contacto)) {
					model.put("contacto", contacto);
					model.put("titulo", messageSource.getMessage("text.contactos.editar", null, locale));

					return "usuarios/registroContacto";
				} else {

					return "redirect:/error_403";
				}
			}

		} else {

			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.contactoId", null, locale));
			return "redirect:/";
		}

	}

	/**
	 * Guardar contacto.
	 *
	 * @param contacto the contacto
	 * @param result the result
	 * @param model the model
	 * @param flash the flash
	 * @param authentication the authentication
	 * @param status the status
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/registroContacto", method = RequestMethod.POST)
	public String guardarContacto(@Valid Contacto contacto, BindingResult result, Model model, RedirectAttributes flash,
		Authentication authentication, SessionStatus status, Locale locale) {

		Usuario currentUser = null;

		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.contactos.add", null, locale));
			return "usuarios/registroContacto";
		}

		if (authentication != null) {
			currentUser = usuarioService.findUsuarioByUsername(authentication.getName());
		}

		if (currentUser == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noUsuario", null, locale));
			return "redirect:/";
		}

		String mensajeFlash = (contacto.getId() != null) ? messageSource.getMessage("text.alert.contactoEditado", null, locale) :
			messageSource.getMessage("text.alert.contactoCreado", null, locale);

		try {

			usuarioService.saveContacto(contacto);

		} catch (DataIntegrityViolationException e) {

			mensajeFlash = messageSource.getMessage("text.alert.contactoExiste", null, locale) + contacto.getAlias() +
				messageSource.getMessage("text.alert.mail", null, locale) + contacto.getEmail();

			flash.addFlashAttribute("error", mensajeFlash);

			if (contacto.getId() != null) {
				return "redirect:/usuarios/registroContacto/" + contacto.getId();
			} else {
				return "redirect:/usuarios/registroContacto";
			}
		}

		status.setComplete();
		if (currentUser != null) {
			model.addAttribute("usuario", currentUser);
		}
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/usuarios/listadoContactos/" + currentUser.getId();
	}

	/**
	 * Ver contacto.
	 *
	 * @param id the id
	 * @param model the model
	 * @param authentication the authentication
	 * @param request the request
	 * @param flash the flash
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/verContacto/{id}", method = RequestMethod.GET)
	public String verContacto(@PathVariable(value = "id") Long id, Model model, Authentication authentication,
		HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Usuario currentUser = null;

		Contacto contacto = usuarioService.findContactoById(id);
		currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		if (contacto == null) {

			if (request.isUserInRole("ROLE_ADMIN")) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noContacto", null, locale));
				return "redirect:/usuarios/listadoUsuarios";

			} else {

				return "redirect:/error_403";
			}

		} else {

			model.addAttribute("contacto", contacto);
			model.addAttribute("titulo", messageSource.getMessage("text.eventos.datosContacto", null, locale));

			if (currentUser == null) {

				return "redirect:/error_403";

			} else {

				if (contacto.getUsuario() == currentUser || request.isUserInRole("ROLE_ADMIN")) {

					return "usuarios/verContacto";

				} else {

					return "redirect:/error_403";
				}
			}
		}
	}

	/**
	 * Lista de contactos.
	 *
	 * @param id the id
	 * @param page the page
	 * @param model the model
	 * @param authentication the authentication
	 * @param session the session
	 * @param request the request
	 * @param flash the flash
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = {"/listadoContactos/{id}"}, method = RequestMethod.GET)
	public String listaDeContactos(@PathVariable(value = "id") Long id, @RequestParam(name = "page", defaultValue = "0") int page, Model model,
		Authentication authentication, HttpSession session, HttpServletRequest request, RedirectAttributes flash, Locale locale) {

		Usuario usuario = usuarioService.findUsuarioById(id);
		Usuario currentUser = usuarioService.findUsuarioByUsername(authentication.getName());

		if (usuario == null) {

			if (request.isUserInRole("ROLE_ADMIN")) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noUsuario", null, locale));
				return "redirect:/";

			} else {

				return "redirect:/error_403";
			}
		} else if (usuario == currentUser || request.isUserInRole("ROLE_ADMIN")) {

			Pageable pageRequest = PageRequest.of(page, 5);

			Page<Contacto> contactos = usuarioService.findAllContactosByUsuario(usuario, pageRequest);

			PageRender<Contacto> pageRender = new PageRender<Contacto> ("/usuarios/listadoContactos/" + id, contactos);

			model.addAttribute("titulo", messageSource.getMessage("text.contactos.misContactos", null, locale));
			model.addAttribute("usuario", usuario);
			model.addAttribute("currentUser", currentUser);
			model.addAttribute("contactos", contactos);
			model.addAttribute("page", pageRender);

			return "usuarios/listadoContactos";

		} else {
			return "redirect:/error_403";
		}
	}

	/**
	 * Eliminar contacto.
	 *
	 * @param id the id
	 * @param flash the flash
	 * @param request the request
	 * @param authentication the authentication
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/eliminarContacto/{id}")
	public String eliminarContacto(@PathVariable(value = "id") Long id, RedirectAttributes flash,
		HttpServletRequest request, Authentication authentication, Locale locale) {

		Usuario usuario = usuarioService.findUsuarioByUsername(authentication.getName());

		if (usuario == null) {

			return "redirect:/error_403";

		} else {
			
			String mensajeFlash = messageSource.getMessage("text.alert.contactoEliminado", null, locale);

			if (id > 0) {

				Contacto contacto = usuarioService.findContactoById(id);
				if (contacto == null) {

					if (request.isUserInRole("ROLE_ADMIN")) {

						flash.addFlashAttribute("error", messageSource.getMessage("text.alert.noContacto", null, locale));
						return "redirect:/usuarios/listadoContactos/" + usuario.getId();

					} else {
						return "redirect:/error_403";
					}

				} else {

					if (contacto.getUsuario().getId() == usuario.getId()) {
						usuarioService.deleteContacto(id);
						flash.addFlashAttribute("success", mensajeFlash);
						return "redirect:/usuarios/listadoContactos/" + usuario.getId();
					} else {
						return "redirect:/error_403";
					}
				}

			} else {
				flash.addFlashAttribute("error", messageSource.getMessage("text.alert.contactoId", null, locale));
				return "redirect:/usuarios/listadoContactos/" + usuario.getId();
			}
		}
	}
	
}
