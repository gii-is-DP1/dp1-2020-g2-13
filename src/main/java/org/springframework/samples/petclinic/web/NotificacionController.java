package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.model.MensajePrivado;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.samples.petclinic.service.MensajePrivadoService;
import org.springframework.samples.petclinic.service.NotificacionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notificaciones")
public class NotificacionController {
	//public static final String NOTIFICACIONES_FORM = "mensajesPrivados/createOrUpdateMensajePrivadoForm";
	public static final String NOTIFICACIONES_LISTING = "notificaciones/NotificacionesListing";
	public static final String MEJORAR_CUENTA = "usuarios/mejorarCuenta";
	public static final String LOGIN = "login";
	public static final String ERROR = "";

	@Autowired
	NotificacionService notificacionService;

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UserService userService;

	@GetMapping
	public String listNotificaciones(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Collection<Usuario> usuarios = usuarioService.findAll();
		Usuario usuario = null;
		for (Usuario u:usuarios) {
			if (u.getUser().getUsername().equals(username)) {
				usuario = u;
			}
		}
		model.addAttribute("notificaciones", notificacionService.findByUserId(usuario.getId()));
		model.addAttribute("usuario", usuario);
		return NOTIFICACIONES_LISTING;
	}
}
